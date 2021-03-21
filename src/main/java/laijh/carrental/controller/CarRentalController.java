package laijh.carrental.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import laijh.carrental.aop.AuthCheck;
import laijh.carrental.common.*;
import laijh.carrental.dao.CarInfoMapper;
import laijh.carrental.dao.CarRentalMapper;
import laijh.carrental.dao.UserInfoMapper;
import laijh.carrental.dto.CarInfo;
import laijh.carrental.dto.CarRentalInfo;
import laijh.carrental.dto.UserInfo;
import laijh.carrental.form.CarRentalForm;
import laijh.carrental.form.UserCancelRentalForm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author laijh25319
 * @date 2021/3/20 14:30
 */
@RestController
@RequestMapping("/rental")
@Slf4j
@Validated
@CrossOrigin
public class CarRentalController {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CarInfoMapper carInfoMapper;

    @Autowired
    private CarRentalMapper carRentalMapper;

    /**
     * 所有车辆型号set集合
     */
    private volatile Set<String> carModelSet;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @PostConstruct
    public void initCarModelSet() {
        synchronized (this) {
            if (carModelSet == null) {
                QueryWrapper<CarInfo> queryWrapper = new QueryWrapper<>();
                queryWrapper.select("distinct model");
                List<CarInfo> carInfoList = carInfoMapper.selectList(queryWrapper);
                carModelSet = carInfoList.stream()
                        .map(CarInfo::getModel)
                        .collect(Collectors.toSet());
            }
        }

    }


    @PostMapping("/order")
    @AuthCheck
    @ApiOperation("预订车辆")
    public ApiResponse<?> order(@Validated CarRentalForm form) throws ParamInvalidException, NoLoginException {

        UserInfo userInfo = token2UserInfo(form.getToken());
        String carModel = form.getCarModel();
        Date startTime = form.getStartTime(), endTime = form.getEndTime();
        if (startTime.compareTo(endTime) >= 0) {
            throw new ParamInvalidException("预订开始时间需小于结束时间");
        }
        if (!carModelSet.contains(carModel)) {
            return ApiResponseUtil.genError(ResponseCode.CAR_MODEL_NOT_EXIST);
        }


        // 对车型号加锁
        final String carModelLockName = RedisKeyConst.CAR_RENTAL_LOCK_PREFIX + carModel;
        RLock carModelLock = redissonClient.getLock(carModelLockName);

        // 对用户加锁
        final String userLockName = RedisKeyConst.USER_CANCEL_RENTAL_LOCK + userInfo.getId();
        RLock userLock = redissonClient.getLock(userLockName);

        userLock.lock();
        try {

            // (startTime, endTime) 区间内用户的车辆预订信息
            List<CarRentalInfo> userCarRentalList = listTimeIntervalCarRental(userInfo.getId(),
                    null, startTime, endTime);

            // 该用户区间内已经有预订车辆信息，返回冲突提示
            if (!userCarRentalList.isEmpty()) {
                return ApiResponseUtil.genError(ResponseCode.RENTAL_CONFLICT);
            }

            carModelLock.lock();
            try {
                // (startTime, endTime) 区间内的车辆预订信息
                List<CarRentalInfo> carRentalList = listTimeIntervalCarRental(null, carModel, startTime, endTime);

                // (startTime, endTime) 区间内该型号车辆下已被预订的车辆id
                Set<Long> alreadyUseCarIdSet = carRentalList.stream()
                        .map(CarRentalInfo::getCarId)
                        .collect(Collectors.toSet());

                // 获取该型号车辆的所有车辆id
                List<CarInfo> oneModelCarInfoList = listCarInfo(carModel);

                // 获取(startTime, endTime)区间内还可使用的车辆id
                CarInfo availCar = oneModelCarInfoList.stream()
                        .filter(car -> !alreadyUseCarIdSet.contains(car.getId()))
                        .findFirst()
                        .orElse(null);

                // 还有车辆可以预订，开始预订车辆
                if (availCar != null) {

                    CarRentalInfo carRentalInfo = CarRentalInfo.builder()
                            .carId(availCar.getId())
                            .carModel(carModel)
                            .userId(userInfo.getId())
                            .startTime(startTime)
                            .endTime(endTime)
                            .status(CarRentalConst.CarRentalStatus.NORMAL.getVal())
                            .createTime(new Date())
                            .build();
                    carRentalMapper.insert(carRentalInfo);

                    return ApiResponseUtil.genSuccess(carRentalInfo);
                }

            } finally {
                carModelLock.unlock();
            }

        } finally {
            userLock.unlock();
        }

        return ApiResponseUtil.genError(ResponseCode.NO_AVAIL_CAR);
    }

    @PostMapping("/cancel")
    @AuthCheck
    @ApiOperation("取消车辆预订")
    public ApiResponse<?> cancel(@Validated UserCancelRentalForm form) throws NoLoginException {

        UserInfo userInfo = token2UserInfo(form.getToken());
        CarRentalInfo carRental;

        // 用户锁
        final String userLockName = RedisKeyConst.USER_CANCEL_RENTAL_LOCK + userInfo.getId();
        RLock lock = redissonClient.getLock(userLockName);

        lock.lock();
        try {

            QueryWrapper<CarRentalInfo> carRentalQry = new QueryWrapper<>();
            carRentalQry.eq("id", form.getCarRentalId());
            carRental = carRentalMapper.selectOne(carRentalQry);

            if (carRental == null) {
                return ApiResponseUtil.genError(ResponseCode.CANCEL_RENTAL_FAIL);
            }

            // 只能取消自己的预订
            if (!Objects.equals(carRental.getUserId(), userInfo.getId())) {
                return ApiResponseUtil.genError(ResponseCode.CANCEL_RENTAL_FAIL);
            }

            // 只能取消已预订的
            if (!Objects.equals(carRental.getStatus(), CarRentalConst.CarRentalStatus.NORMAL.getVal())) {
                return ApiResponseUtil.genError(ResponseCode.CANCEL_RENTAL_FAIL);
            }

            carRental.setStatus(CarRentalConst.CarRentalStatus.CANCEL.getVal());
            carRental.setUpdateTime(new Date());
            carRentalMapper.updateById(carRental);

        } finally {
            lock.unlock();
        }

        return ApiResponseUtil.genSuccess(carRental);
    }

    /**
     * 获取某型号下的所有车辆信息
     *
     * @param carModel 车辆型号
     * @return 该型号下的所有车辆
     */
    public List<CarInfo> listCarInfo(String carModel) {

        QueryWrapper<CarInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("model", carModel);

        return carInfoMapper.selectList(queryWrapper);
    }

    /**
     * 获取 (startTime, endTime) 区间内车辆的有效预订信息
     *
     * @param userId    用户id，可选
     * @param carModel  车辆型号，可选
     * @param startTime 预订开始使用时间
     * @param endTime   预订结束使用时间
     * @return 区间内的此型号车辆的有效预订信息
     */
    public List<CarRentalInfo> listTimeIntervalCarRental(Long userId, String carModel, Date startTime, Date endTime) {

        QueryWrapper<CarRentalInfo> queryWrapper = new QueryWrapper<>();
        if (userId != null) {
            queryWrapper.eq("user_id", userId);
        }
        if (StringUtils.isNotBlank(carModel)) {
            queryWrapper.eq("car_model", carModel);
        }
        // <
        queryWrapper.lt("start_time", endTime);
        // >
        queryWrapper.gt("end_time", startTime);
        queryWrapper.eq("status", CarRentalConst.CarRentalStatus.NORMAL.getVal());

        return carRentalMapper.selectList(queryWrapper);
    }

    @GetMapping("/list_car_model")
    @ApiOperation("查询所有车辆型号")
    public ApiResponse<String> listCarModel() {

        return ApiResponseUtil.genSuccess(carModelSet);
    }

    @PostMapping("/my_car_rental")
    @AuthCheck
    @ApiOperation("查询我的车辆预订信息列表")
    public ApiResponse<CarRentalInfo> listMyCarRental(BaseRequest baseRequest) throws NoLoginException {

        UserInfo user = token2UserInfo(baseRequest.getToken());

        QueryWrapper<CarRentalInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());
        queryWrapper.orderByDesc("id");
        List<CarRentalInfo> carRentalList = carRentalMapper.selectList(queryWrapper);

        return ApiResponseUtil.genSuccess(carRentalList);
    }

    /**
     * token->用户信息
     *
     * @param token 用户token
     * @return 用户信息
     * @throws NoLoginException token失效
     */
    public UserInfo token2UserInfo(String token) throws NoLoginException {

        // redis中token对应的用户信息
        final String token2userId = RedisKeyConst.TOKEN2_USER_ID_PREFIX + token;

        String userId = stringRedisTemplate.opsForValue().get(token2userId);
        if (StringUtils.isBlank(userId)) {
            throw new NoLoginException();
        }

        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        return userInfoMapper.selectOne(queryWrapper);
    }


}
