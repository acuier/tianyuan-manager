package tianyuan.rbac.param;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import tianyuan.common.enums.NormalStatusEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/29 15:26.
 * @Describution:
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentParam {

    private Integer departmentId;

    @NotBlank(message = "机构部门的名称必须填写！")
    private String departmentName;
    @NotBlank(message = "机构部门的简称必须填写！")
    private String departmentNickName;

    private Integer departmentParentId = 0;

    @NotNull
    private Integer departmentSeq;

    @NotBlank(message = "机构部门的地址必须填写！")
    private String departmentAddress;

    @Length(max = 200,message = "简介不要超过200字")
    private String departmentIntroduction;

    @NotBlank(message = "机构部门的法人必须填写！")
    private String departmentCorporation;

    private String departmentContacts;
    @NotBlank(message = "机构部门的联系电话必须填写！")
    private String departmentPhone;
    @NotBlank(message = "机构部门的联系邮件必须填写！")
    private String departmentEmail;

    private Integer departmentStatus = NormalStatusEnum.USED.getCode();

    private Integer departmentStar = 0;

    private String departmentDescription;
}
