package tianyuan.rbac.param;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/30 17:05.
 * @Describution:
 */

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PermissionParam {

    private Integer permissionId;

    private Integer parentId = 0;

    @NotBlank(message = "权限名称必须填写")
    @Length(min = 2,max = 20,message = "权限模块名称长度2-20个字！")
    private String permissionName;


    @NotNull(message = "权限模块展示顺序不可以为空")
    private Integer permissionSort = 0;


    @Length(max = 128, message = "权限模块备注需要在128个字之间")
    private String permissionDescription;

    @NotNull(message = "权限模块状态不可以为空")
    @Min(value = 0, message = "权限模块状态不合法")
    @Max(value = 1, message = "权限模块状态不合法")
    private Integer permissionStatus = 0;



}
