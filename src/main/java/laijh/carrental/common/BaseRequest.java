package laijh.carrental.common;

import laijh.carrental.dto.UserInfo;
import lombok.Data;

/**
 * @author laijh25319
 * @date 2021/3/20 14:32
 */
@Data
public class BaseRequest {

    /**
     * 登陆token
     */
    protected String token;

    private UserInfo user;
}
