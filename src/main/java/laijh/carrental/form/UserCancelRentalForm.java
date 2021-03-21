package laijh.carrental.form;

import io.swagger.annotations.ApiModelProperty;
import laijh.carrental.common.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author laijh25319
 * @date 2021/3/21 12:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserCancelRentalForm extends BaseRequest {

    @NotNull
    @Min(1)
    @ApiModelProperty(value = "车辆预订id", required = true)
    private Long carRentalId;
}
