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
public class UserRole implements Serializable {

    private static final long serialVersionUID = -2509097068977269434L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userRoleId;

    private Integer userId;

    private Integer roleId;

    private String operator;

    private String operatorIp;

    private Date createTime;

    private Date updateTime;

}