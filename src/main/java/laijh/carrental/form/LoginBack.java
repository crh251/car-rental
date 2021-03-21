package laijh.carrental.form;

import laijh.carrental.dto.UserInfo;
import lombok.Builder;
import lombok.Data;

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
    private Long id;

    /**
     * {@link UserInfo#getUsername()}
     */
    private String username;

    private String token;
}
