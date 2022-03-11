package laijh.carrental;

import laijh.carrental.common.ApiResponse;
import laijh.carrental.common.BaseRequest;
import laijh.carrental.common.CarRentalConst;
import laijh.carrental.common.NoLoginException;
import laijh.carrental.common.ParamInvalidException;
import laijh.carrental.common.ResponseCode;
import laijh.carrental.controller.CarRentalController;
import laijh.carrental.controller.UserController;
import laijh.carrental.dto.CarRentalInfo;
import laijh.carrental.form.CarRentalForm;
import laijh.carrental.form.LoginBack;
import laijh.carrental.form.LoginForm;
import laijh.carrental.form.UserCancelRentalForm;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

//@SpringBootTest
@Slf4j
class CarRentalApplicationTests {

    @Autowired
    private UserController userController;

    @Autowired
    private CarRentalController carRentalController;

    /**
     * 模拟并发预订某个型号车辆 单元测试
     */
//    @Test
    void carRentalOrder() throws NoLoginException, InterruptedException {

        // 登陆
        ApiResponse<?> response = userController.login(LoginForm.builder().username("t1").password("123").build());
        log.info("login back info:{}.", response);
        Assertions.assertEquals("0", response.getCode());

        Object result = Optional.of(response).map(ApiResponse::getData).orElse(null);
        Assertions.assertTrue(result instanceof LoginBack);

        String token = ((LoginBack) result).getToken();
        Assertions.assertNotNull(token);

        // 查询我的订阅
        ApiResponse<CarRentalInfo> myCarRental = carRentalController.listMyCarRental(BaseRequest.builder().token(token).build());
        log.info("my car rental info:{}", myCarRental);
        Assertions.assertNotNull(myCarRental);
        Assertions.assertEquals("0", myCarRental.getCode());

        // 取消我的所有订阅
        for (CarRentalInfo carRental : myCarRental.getDataList()) {
            if (Objects.equals(carRental.getStatus(), CarRentalConst.CarRentalStatus.CANCEL.getVal())) {
                log.info("already cancel!");
                continue;
            }
            ApiResponse<?> cancelResponse = carRentalController.cancel(UserCancelRentalForm.builder()
                    .carRentalId(carRental.getId()).token(token).build());
            Assertions.assertNotNull(cancelResponse);
            Assertions.assertEquals("0", cancelResponse.getCode());
        }

        final int size = 2;
        ExecutorService executor = Executors.newFixedThreadPool(size);

        // 预订成功次数
        AtomicInteger successCnt = new AtomicInteger(0);
        // 冲突次数
        AtomicInteger conflictCnt = new AtomicInteger(0);

        // 并发预订，只能有一个预订成功
        for (int i = 0; i < size; i++) {
            executor.submit(() -> {
                CarRentalForm rentalForm = CarRentalForm.builder()
                        .token(token)
                        .carModel("BMW 650")
                        // 当前时间+1分钟+最多一小时随机值
                        .startTime(new Date(System.currentTimeMillis() + 60 * 1000 + new Random().nextInt(60 * 60 * 1000)))
                        // 当前时间+1小时+最多一小时随机值
                        .endTime(new Date(System.currentTimeMillis() + 60 * 60 * 1000L + new Random().nextInt(60 * 60 * 1000)))
                        .build();
                ApiResponse<?> carRentalResponse;
                log.info("start order car rental!");
                try {
                    carRentalResponse = carRentalController.order(rentalForm);
                    log.info("order car rental end...");
                } catch (ParamInvalidException | NoLoginException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
                Assertions.assertNotNull(carRentalResponse);
                log.info("thread:{}, code:{}, message:{}.", Thread.currentThread().getName(),
                        carRentalResponse.getCode(), carRentalResponse.getMessage());
                String code = carRentalResponse.getCode();
                if ("0".equals(code)) {
                    successCnt.incrementAndGet();
                } else if (ResponseCode.RENTAL_CONFLICT.getCode().equals(code)) {
                    conflictCnt.incrementAndGet();
                } else {
                    throw new RuntimeException("未知错误" + carRentalResponse);
                }
            });
        }

        while (successCnt.get() + conflictCnt.get() != size) {
            log.info("wait...");
            Thread.sleep(1000);
        }

        executor.shutdown();

        Assertions.assertEquals(1, successCnt.get());
        Assertions.assertEquals(size - 1, conflictCnt.get());

    }


    /**
     * 模拟并发取消某个车辆预订记录 单元测试
     */
//    @Test
    void carRentalCancel() throws NoLoginException, InterruptedException {

        // 登陆
        ApiResponse<?> response = userController.login(LoginForm.builder().username("t1").password("123").build());
        log.info("login back info:{}.", response);
        Assertions.assertEquals("0", response.getCode());

        Object result = Optional.of(response).map(ApiResponse::getData).orElse(null);
        Assertions.assertTrue(result instanceof LoginBack);

        String token = ((LoginBack) result).getToken();
        Assertions.assertNotNull(token);

        // 查询我的订阅
        ApiResponse<CarRentalInfo> myCarRental = carRentalController.listMyCarRental(BaseRequest.builder().token(token).build());
        log.info("my car rental info:{}", myCarRental);
        Assertions.assertNotNull(myCarRental);
        Assertions.assertEquals("0", myCarRental.getCode());

        CarRentalInfo existCarRental = myCarRental.getDataList().stream()
                .filter(item -> CarRentalConst.CarRentalStatus.NORMAL.getVal() == item.getStatus())
                .findFirst().orElse(null);

        final int size = 2;
        ExecutorService executor = Executors.newFixedThreadPool(size);

        // 取消成功次数
        AtomicInteger successCnt = new AtomicInteger(0);
        // 取消失败次数
        AtomicInteger failCnt = new AtomicInteger(0);

        // 并发预订，只能有一个预订成功
        for (int i = 0; i < size; i++) {
            executor.submit(() -> {
                UserCancelRentalForm form = UserCancelRentalForm.builder().build();
                ApiResponse<?> cancelResponse;
                try {
                    cancelResponse = carRentalController.cancel(form);
                } catch (NoLoginException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }

                Assertions.assertNotNull(cancelResponse);
                log.info("thread:{}, code:{}, message:{}.", Thread.currentThread().getName(),
                        cancelResponse.getCode(), cancelResponse.getMessage());
                String code = cancelResponse.getCode();
                if ("0".equals(code)) {
                    successCnt.incrementAndGet();
                } else {
                    failCnt.incrementAndGet();
                }
            });
        }

        while (successCnt.get() + failCnt.get() != size) {
            log.info("wait...");
            Thread.sleep(1000);
        }

        executor.shutdown();

        Assertions.assertEquals(1, successCnt.get());
        Assertions.assertEquals(size - 1, failCnt.get());
    }

}
