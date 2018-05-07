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
public class SystemPermission implements Serializable {

    private static final long serialVersionUID = -5492992441157102264L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer permissionId;

    private Integer parentId;

    private String permissionName;

    private String permissionLevel;

    private Integer permissionSort;

    private String permissionDescription;

    private Integer permissionStatus;

    private String operator;

    private String operatorIp;

    private Date createTime;

    private Date updateTime;


}