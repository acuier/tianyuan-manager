package tianyuan.rbac.model.admin;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

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

public class SystemAcl implements Serializable {

    private static final long serialVersionUID = 9183599194414741640L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer aclId;

    private String aclCode;

    private String aclName;

    private Integer aclModuleId;

    private String aclUrl;

    private Integer aclType;

    private Integer aclStatus;

    private Integer aclSeq;

    private String aclRemark;

    private String operator;

    private String operatorIp;

    private Date createTime;

    private Date updateTime;

  }