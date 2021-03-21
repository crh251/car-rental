package laijh.carrental.common;

/**
 * @author laijh25319
 * @date 2021/3/20 16:07
 */
public interface RedisKeyConst {

    String USER_ID2_TOKEN_PREFIX = "userid2.token.";

    String TOKEN2_USER_ID_PREFIX = "token2.userid.";

    String CAR_RENTAL_LOCK_PREFIX = "car.rental.lock.";

    String USER_CANCEL_RENTAL_LOCK = "user.cancel.car.rental.lock.";

    String TOKEN_NAME = "token";

    /**
     * 默认token超时时间（秒）。
     */
    long DEFAULT_TOKEN_EXPIRE_TIME = 2 * 60 * 60;
}
