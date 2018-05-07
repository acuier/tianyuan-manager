package tianyuan.rbac.dto;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 权限树的层级结构
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/30 18:15.
 * @Describution:
 */
@Data
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class PermissionLevelDto extends PermissionDto implements Serializable {

    private static final long serialVersionUID = 2451754902924832304L;
    //模块列表
    private List<PermissionLevelDto> permissionLevelDtoList = Lists.newArrayList();

    // 模块挂接的权限点
    private List<AclDto> aclDtoList = Lists.newArrayList();

    /**
     * 转换 infoDto到levelDto
     * @param permissionDto
     * @return
     */
//    public static DepartmentLevelDto departmentInfo2DepartmentInfoDto(DepartmentInfo departmentInfo){
//        DepartmentLevelDto dto = new DepartmentLevelDto();
//        BeanUtils.copyProperties(departmentInfo,dto);
//        return dto;
//    }
    public static PermissionLevelDto permissionDto2PermissionLevelDto(PermissionDto permissionDto){
        PermissionLevelDto dto = new PermissionLevelDto();
        BeanUtils.copyProperties(permissionDto,dto);
        return dto;
    }
}