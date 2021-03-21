package laijh.carrental.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;

/**
 * @author laijh25319
 * @date 2021/3/20 14:32
 */
@Data
@SuperBuilder
public class BaseRequest {

    /**
     * token
     */
    @ApiModelProperty(value = "token", required = true)
    protected String token;

    @Tolerate
    public BaseRequest() {
    }
}
