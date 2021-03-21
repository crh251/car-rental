package laijh.carrental.controller;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import laijh.carrental.common.ApiResponse;
import laijh.carrental.common.ApiResponseUtil;
import laijh.carrental.common.RedisKeyConst;
import laijh.carrental.common.ResponseCode;
import laijh.carrental.dao.UserInfoMapper;
import laijh.carrental.dto.UserInfo;
import laijh.carrental.form.LoginBack;
import laijh.carrental.form.LoginForm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author laijh25319
 * @date 2021/3/20 14:29
 */
@RestController
@RequestMapping("/user")
@Slf4j
@Validated
@CrossOrigin
@Api(value = "/user")
public class UserController {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${token.expire-time}")
    private final long tokenExpireTime = RedisKeyConst.DEFAULT_TOKEN_EXPIRE_TIME;

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public ApiResponse<?> login(@Validated LoginForm form) {

        String username = form.getUsername(), password = form.getPassword();

        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        UserInfo user = userInfoMapper.selectOne(queryWrapper);

        // 用户名不存在
        if (user == null) {
            return ApiResponseUtil.genError(ResponseCode.LOGIN_FAIL);
        }

        Long userId = user.getId();

        String pwdMd5 = SecureUtil.md5(password + user.getSalt());
        // 计算出的密码错误
        if (!pwdMd5.equals(user.getPassword())) {
            return ApiResponseUtil.genError(ResponseCode.LOGIN_FAIL);
        }

        // 生成的新token
        String token = UUID.randomUUID().toString();

        // userId -> token
        final String userId2TokenKey = RedisKeyConst.USER_ID2_TOKEN_PREFIX + userId;

        // 获取上一次登陆成功的token，如果存在则删除，将其他token挤下线
        String lastLoginToken = stringRedisTemplate.opsForValue().get(userId2TokenKey);
        if (StringUtils.isNotBlank(lastLoginToken)) {
            stringRedisTemplate.delete(RedisKeyConst.TOKEN2_USER_ID_PREFIX + lastLoginToken);
            log.info("user[{}]已被挤下线！", userId);
        }

        // token -> userId
        stringRedisTemplate.opsForValue().set(RedisKeyConst.TOKEN2_USER_ID_PREFIX + token,
                String.valueOf(userId), tokenExpireTime, TimeUnit.SECONDS);

        stringRedisTemplate.opsForValue().set(userId2TokenKey, token, tokenExpireTime, TimeUnit.SECONDS);

        // 更新最后登陆时间
        user.setLastLoginTime(new Date());
        userInfoMapper.updateById(user);

        return ApiResponseUtil.genSuccess(LoginBack.builder()
                .id(userId)
                .username(username)
                .token(token)
                .build());
    }


}
