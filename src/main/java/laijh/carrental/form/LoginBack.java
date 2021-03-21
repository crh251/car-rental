package laijh.carrental.form;

import io.swagger.annotations.ApiModelProperty;
import laijh.carrental.dto.UserInfo;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

/**
 * @author laijh25319
 * @date 2021/3/20 15:21
 */
@Data
@Builder
public class LoginBack {

    /**
     * {@link UserInfo#getId()}
     */
    @ApiModelProperty("用户id")
    private Long id;

    /**
     * {@link UserInfo#getUsername()}
     */
    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("token")
    private String token;

    @Tolerate
    public LoginBack() {
    }
}
