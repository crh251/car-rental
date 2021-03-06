package laijh.carrental.form;

import io.swagger.annotations.ApiModelProperty;
import laijh.carrental.common.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author laijh25319
 * @date 2021/3/20 16:31
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class CarRentalForm extends BaseRequest {

    /**
     * 预订车辆型号
     */
    @NotBlank
    @ApiModelProperty(value = "车辆型号", required = true)
    private String carModel;

    /**
     * 预订车辆开始使用时间
     */
    @NotNull
    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @ApiModelProperty(value = "开始使用时间[yyyy-MM-dd HH:mm]，需要大于当前时间", required = true, example = "2020-04-01 17:00")
    private Date startTime;

    /**
     * 预订车辆结束使用时间
     */
    @NotNull
    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @ApiModelProperty(value = "结束使用时间[yyyy-MM-dd HH:mm]，需要大于当前时间", required = true, example = "2020-04-01 08:00")
    private Date endTime;

    @Tolerate
    public CarRentalForm() {
    }
}
