package laijh.carrental.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * @author laijh25319
 * @date 2021/3/20 15:14
 */
@Data
@NoArgsConstructor
public class ApiResponse<T> {

    public ApiResponse(ResponseCode responseCode) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }

    public ApiResponse(ResponseCode responseCode, T data) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        this.data = data;
    }

    public ApiResponse(ResponseCode responseCode, Collection<T> dataList) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        this.dataList = dataList;
    }

    private String code;

    private String message;

    private T data;

    private Collection<T> dataList;
}
