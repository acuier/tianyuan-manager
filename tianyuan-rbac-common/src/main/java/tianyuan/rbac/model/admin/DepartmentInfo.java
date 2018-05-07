package tianyuan.rbac.model.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import tianyuan.common.enums.NormalStatusEnum;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Builder
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentInfo implements Serializable {

    private static final long serialVersionUID = -6576532505344220025L;
    @GeneratedValue(strategy =GenerationType.AUTO)
    @Id
    private Integer departmentId;

    private String departmentName;

    private String departmentNickName;

    private Integer departmentParentId = 0;

    private String departmentLevel;

    private Integer departmentSeq;

    private String departmentAddress;

    private String departmentIntroduction;

    private String departmentCorporation;

    private String departmentContacts;

    private String departmentPhone;

    private String departmentEmail;

    private Integer departmentStatus = NormalStatusEnum.USED.getCode();

    private Integer departmentStar = 0;

    private String departmentDescriptionUrl;

    private String departmentDescription;

    private String operator;

    private String operatorIp;

    private Date createTime;

    private Date updateTime;

}