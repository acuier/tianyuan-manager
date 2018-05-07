package tianyuan.rbac.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import tianyuan.rbac.model.admin.DepartmentInfo;
import tianyuan.rbac.model.admin.SystemUser;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/30 7:52.
 * @Describution:
 */
@Data
@Getter
@Setter
public class DepartmentInfoDto {

    private Integer departmentId;

    private String departmentName;

    private String departmentNickName;

    private Integer departmentParentId;

    private String departmentLevel;

    private Integer departmentSeq;

    private String departmentAddress;

    private String departmentIntroduction;

    private String departmentCorporation;

    private String departmentContacts;

    private String departmentPhone;

    private String departmentEmail;

    private Integer departmentStatus;

    private Integer departmentStar ;

    private String departmentDescriptionUrl = "";

    List<SystemUser> userList = new ArrayList<>();

    /**
     * 转换
     */
    public static DepartmentInfoDto departmentInfo2DepartmentInfoDto(DepartmentInfo departmentInfo){
        DepartmentInfoDto departmentInfoDto = new DepartmentInfoDto();
        BeanUtils.copyProperties(departmentInfo,departmentInfoDto);
        return departmentInfoDto;
    }
}
