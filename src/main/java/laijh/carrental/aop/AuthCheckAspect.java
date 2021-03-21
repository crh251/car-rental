package laijh.carrental.aop;

import laijh.carrental.common.ApiResponseUtil;
import laijh.carrental.common.RedisKeyConst;
import laijh.carrental.common.ResponseCode;
import laijh.carrental.dao.UserInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author laijh25319
 * @date 2021/3/20 16:30
 */
@Component
@Aspect
@Slf4j
public class AuthCheckAspect {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Value("${token.expire-time}")
    private final long tokenExpireTime = RedisKeyConst.DEFAULT_TOKEN_EXPIRE_TIME;

    @Around("@annotation(laijh.carrental.aop.AuthCheck)")
    @ResponseBody
    public Object authCheck(ProceedingJoinPoint pjp) throws Throwable {

        String token = request.getParameter(RedisKeyConst.TOKEN_NAME);

        if (StringUtils.isBlank(token)) {
            return ApiResponseUtil.genError(ResponseCode.NO_LOGIN);
        }

        // redis中token对应的用户信息
        final String token2userId = RedisKeyConst.TOKEN2_USER_ID_PREFIX + token;

        String userId = stringRedisTemplate.opsForValue().get(token2userId);
        if (StringUtils.isBlank(userId)) {
            return ApiResponseUtil.genError(ResponseCode.NO_LOGIN);
        }

        // 校验是否被挤下
        String userId2token = RedisKeyConst.USER_ID2_TOKEN_PREFIX + userId;
        String lastLoginToken = stringRedisTemplate.opsForValue().get(userId2token);
        if (!Objects.equals(token, lastLoginToken)) {
            return ApiResponseUtil.genError(ResponseCode.NO_LOGIN);
        }

        // 重新设置超时时间
        stringRedisTemplate.expire(token2userId, tokenExpireTime, TimeUnit.SECONDS);
        stringRedisTemplate.expire(userId2token, tokenExpireTime, TimeUnit.SECONDS);

        return pjp.proceed();
    }

}
