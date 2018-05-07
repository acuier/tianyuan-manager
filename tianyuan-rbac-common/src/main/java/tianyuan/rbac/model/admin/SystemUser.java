package tianyuan.rbac.model.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
public class SystemUser implements Serializable {

    private static final long serialVersionUID = -7830464367698072192L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    private String userName;

    private String operatorName;

    private String userPassword;

    private String userPhone;

    private String userEmail;

    private String avatar;

    private Integer departmentId;

    private Integer userStatus;

    private String operator;

    private String operatorIp;

    private Date createTime;

    private Date updateTime;
}