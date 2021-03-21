package laijh.carrental.form;

import laijh.carrental.common.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
public class CarRentalForm extends BaseRequest {

    /**
     * 预订车辆型号
     */
    @NotBlank
    private String carModel;

    /**
     * 预订车辆开始使用时间
     */
    @NotNull
    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startTime;

    /**
     * 预订车辆结束使用时间
     */
    @NotNull
    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;
}
