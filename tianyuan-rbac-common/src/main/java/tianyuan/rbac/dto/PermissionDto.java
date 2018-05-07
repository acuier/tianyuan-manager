package tianyuan.rbac.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import tianyuan.rbac.model.admin.SystemAcl;
import tianyuan.rbac.model.admin.SystemPermission;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/30 18:15.
 * @Describution:
 */
@Data
@Getter
@Setter
public class PermissionDto implements Serializable {

    private static final long serialVersionUID = -773097158297155278L;

    private Integer permissionId;

    private Integer parentId ;

    private String permissionName;

    private String permissionLevel;

    private Integer permissionSort;

    private String permissionDescription;

    private Integer permissionStatus;

    List<SystemAcl> aclList = new ArrayList<>();

    /**
     * 转换
     */
    public static PermissionDto permission2PermissionDto(SystemPermission permission){
        PermissionDto permissionDto = new PermissionDto();
        BeanUtils.copyProperties(permission,permissionDto);
        return permissionDto;
    }
}