package laijh.carrental.aop;

import java.lang.annotation.*;

/**
 * @author laijh25319
 * @date 2021/3/20 19:56
 * <p>
 * 标明方法需要token校验
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthCheck {

}