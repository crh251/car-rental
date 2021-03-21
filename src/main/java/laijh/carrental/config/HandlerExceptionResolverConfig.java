package laijh.carrental.config;

import laijh.carrental.common.ApiResponse;
import laijh.carrental.common.ApiResponseUtil;
import laijh.carrental.common.CommonUtil;
import laijh.carrental.common.ParamInvalidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author laijh25319
 * @date 2021/3/21 1:28
 */
@Configuration
@Slf4j
public class HandlerExceptionResolverConfig implements WebMvcConfigurer {

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {

        log.info("开始注册全局异常处理器！");

        resolvers.add(0, (request, response, handler, ex) -> {

            ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());

            if (ex instanceof BindException) {
                BindException e = (BindException) ex;
                StringBuilder errMsg = new StringBuilder();
                e.getFieldErrors().forEach(fieldError ->
                        errMsg.append('[').append(fieldError.getField()).append(" 参数错误]"));
                ApiResponse<?> apiResponse = ApiResponseUtil.genParamInvalidError(errMsg.toString());
                modelAndView.addAllObjects(CommonUtil.bean2map(apiResponse, true));
                return modelAndView;
            }

            if (ex instanceof ConstraintViolationException || ex instanceof ParamInvalidException) {
                ApiResponse<?> apiResponse = ApiResponseUtil.genParamInvalidError(ex.getMessage());
                modelAndView.addAllObjects(CommonUtil.bean2map(apiResponse, true));
                return modelAndView;
            }

            log.error("occur error!", ex);
            ApiResponse<?> apiResponse = ApiResponseUtil.genCommonError("执行异常");
            modelAndView.addAllObjects(CommonUtil.bean2map(apiResponse, true));
            return modelAndView;
        });

    }
}
