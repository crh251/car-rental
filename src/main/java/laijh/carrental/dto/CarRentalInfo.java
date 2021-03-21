package laijh.carrental.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.Date;

/**
 * @author laijh25319
 * @date 2021/3/20 14:28
 */
@Data
@Builder
@TableName("car_rental_info")
public class CarRentalInfo {

    /**
     * 车辆预订id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("预订记录id")
    private Long id;

    /**
     * 用户id
     */
    @TableField("user_id")
    @ApiModelProperty("用户id")
    private Long userId;

    /**
     * 车辆id
     */
    @TableField("car_id")
    @ApiModelProperty("车辆id")
    private Long carId;

    /**
     * 车辆型号
     */
    @TableField("car_model")
    @ApiModelProperty("车辆型号")
    private String carModel;

    /**
     * 预订车辆开始使用时间
     */
    @TableField("start_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("预订开始使用时间")
    private Date startTime;

    /**
     * 预订车辆结束使用时间
     */
    @TableField("end_time")
    @ApiModelProperty("预订结束使用时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    /**
     * 预订状态
     */
    @TableField
    @ApiModelProperty("预订状态。1-已预订。2-已取消。")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("记录创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("记录更新时间")
    private Date updateTime;

    @Tolerate
    public CarRentalInfo() {
    }
}

