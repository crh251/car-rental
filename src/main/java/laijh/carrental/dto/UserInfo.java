package laijh.carrental.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Tolerate;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;

/**
 * @author laijh25319
 * @date 2021/3/20 14:27
 */
@Data
@TableName("user_info")
@ApiIgnore
public class UserInfo {

    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    protected Long id;

    /**
     * 用户名
     */
    @TableField
    protected String username;

    /**
     * 密码
     */
    @TableField
    protected String password;

    /**
     * 密码的盐
     */
    @TableField
    protected String salt;

    /**
     * 最后一次登陆时间
     */
    @TableField("last_login_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date lastLoginTime;
}
