package laijh.carrental.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.Data;

/**
 * @author laijh25319
 * @date 2021/3/20 14:28
 */
@Data
@TableName("car_info")
public class CarInfo {

    /**
     * 车辆id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 车辆型号
     */
    @TableField
    private String model;
}

