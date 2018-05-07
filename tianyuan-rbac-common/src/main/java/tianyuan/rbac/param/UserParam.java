package tianyuan.rbac.param;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/30 10:46.
 * @Describution:
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserParam {

    private Integer userId;

    @NotBlank(message = "用户名必须填写！")
    @Length(min = 0,max = 20 ,message = "用户名的长度不可以超过20个字！")
    private String userName;

    private String operatorName;

    @NotBlank(message = "用户名必须填写！")
    private String userPhone;

    @NotBlank(message = "用户名必须填写！")
    private String userEmail;

    @NotNull(message = "必须填写用户所在的机构或者部门！")
    private Integer departmentId;

    @NotNull(message = "用户的状态必须填写！")
    @Min(value = 0,message = "用户状态不合法！")
    @Max(value = 3,message = "用户状态不合法！")
    private Integer userStatus;

    private String avatar;

}
