package laijh.carrental.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.validation.constraints.NotBlank;

/**
 * @author laijh25319
 * @date 2021/3/21 21:20
 */
@Data
@Builder
public class LoginForm {

    @NotBlank
    @ApiModelProperty(value = "用户名", required = true)
    String username;

    @NotBlank
    @ApiModelProperty(value = "密码", required = true)
    String password;

    @Tolerate
    public LoginForm() {
    }
}
