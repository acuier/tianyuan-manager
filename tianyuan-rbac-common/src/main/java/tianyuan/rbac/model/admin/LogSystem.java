package tianyuan.rbac.model.admin;

import lombok.AllArgsConstructor;

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
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class LogSystem implements Serializable {

    private static final long serialVersionUID = -8783026302379456474L;
    @GeneratedValue(strategy =GenerationType.AUTO)
    @Id
    private Integer logId;

    private Integer targetId;

    private Integer operatorType;



    private Integer logStatus;

    private Integer operatorId;

    private String operator;

    private Date createTime;

    private Date updateTime;


}