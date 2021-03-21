package laijh.carrental.common;

/**
 * @author laijh25319
 * @date 2021/3/21 0:49
 * <p>
 * 请求参数错误异常
 */
public class ParamInvalidException extends Exception {

    public ParamInvalidException(String message) {
        super(message);
    }
}
