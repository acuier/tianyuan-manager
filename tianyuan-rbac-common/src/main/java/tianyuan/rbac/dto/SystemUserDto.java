package tianyuan.rbac.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018-04-17 8:29.
 * @Describution:
 */
@Data
@Getter
@Setter
@Builder
public class SystemUserDto {

    private Integer userId;

    private String userName;

    private String userPassword;

    private String userPhone;

    private String userEmail;

    private Integer departmentId;

    private Integer userStatus;

    private List<String> userAcl;

}
