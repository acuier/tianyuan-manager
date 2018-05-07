package tianyuan.rbac.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@ToString
public class RoleParam {

    private Integer roleId;

    @NotBlank(message = "角色名称不可以为空")
    @Length(min = 2, max = 20, message = "角色名称长度需要在2-20个字之间")
    private String roleName;

    @NotNull(message = "必须指定角色的类型")
    @Min(value = 1, message = "角色类型不合法")
    @Max(value = 3, message = "角色类型不合法")
    private Integer roleType;

    @NotNull(message = "必须指定角色的状态")
    @Min(value = 0, message = "角色状态不合法")
    @Max(value = 1, message = "角色状态不合法")
    private Integer roleStatus;

    @Length(min = 0, max = 200, message = "角色备注长度需要在200个字符以内")
    private String roleRemark;











  }