package tianyuan.rbac.dto;

import com.google.common.collect.Lists;
import lombok.*;
import org.springframework.beans.BeanUtils;
import tianyuan.rbac.model.admin.DepartmentInfo;

import java.io.Serializable;
import java.util.List;

/**
 * 机构部门树的层级结构
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/29 19:33.
 * @Describution:
 */
@Data
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class DepartmentLevelDto extends DepartmentInfo implements Serializable{

    private static final long serialVersionUID = -3885210800140306066L;

    private List<DepartmentLevelDto> departmentInfoList = Lists.newArrayList();

    /**
     * 转换 infoDto到levelDto
     * @param departmentInfo
     * @return
     */
//    public static DepartmentLevelDto departmentInfo2DepartmentInfoDto(DepartmentInfo departmentInfo){
//        DepartmentLevelDto dto = new DepartmentLevelDto();
//        BeanUtils.copyProperties(departmentInfo,dto);
//        return dto;
//    }
    public static DepartmentLevelDto departmentInfo2DepartmentInfoDto(DepartmentInfo departmentInfo){
        DepartmentLevelDto dto = new DepartmentLevelDto();
        BeanUtils.copyProperties(departmentInfo,dto);
        return dto;
    }
}
