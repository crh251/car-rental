package laijh.carrental.common;

import lombok.experimental.UtilityClass;

import java.util.Collection;

/**
 * @author laijh25319
 * @date 2021/3/20 15:50
 */
@UtilityClass
public class ApiResponseUtil {

    public ApiResponse<?> genParamInvalidError(String msg) {
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setCode(ResponseCode.PARAM_INVALID.getCode());
        apiResponse.setMessage(msg);
        return apiResponse;
    }

    public ApiResponse<?> genCommonError(String msg) {
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setCode(ResponseCode.COMMON_ERROR.getCode());
        apiResponse.setMessage(msg);
        return apiResponse;
    }

    public ApiResponse<?> genError(ResponseCode responseCode) {

        return new ApiResponse<>(responseCode);
    }

    public <T> ApiResponse<T> genSuccess(T result) {

        return new ApiResponse<>(ResponseCode.SUCCESS, result);
    }

    public <T> ApiResponse<T> genSuccess(Collection<T> resultList) {

        return new ApiResponse<>(ResponseCode.SUCCESS, resultList);
    }

}
