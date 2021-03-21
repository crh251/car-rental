package laijh.carrental.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author laijh25319
 * @date 2021/3/20 15:16
 */
@AllArgsConstructor
@Getter
public enum ResponseCode {

    /**
     * 0-执行成功
     */
    SUCCESS("0", "success"),

    COMMON_ERROR("", ""),

    PARAM_INVALID("1", ""),

    LOGIN_FAIL("1", "用户名或密码错误"),
    /**
     * 1-未登录
     */
    NO_LOGIN("1", "未登录或已超时"),

    CAR_MODEL_NOT_EXIST("2", "车辆型号不存在"),
    /**
     * 2-暂无可用车辆
     */
    NO_AVAIL_CAR("2", "暂无可用车辆"),

    RENTAL_CONFLICT("3", "与已有预订冲突"),

    /**
     * 3-取消预订失败
     */
    CANCEL_RENTAL_FAIL("3", "取消失败");

    private final String code;

    private final String message;
}
