package tianyuan.rbac.model.admin;

import lombok.*;

import javax.persistence.Entity;
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class LogSystemWithBLOBs extends LogSystem {

    private String oldValue;

    private String newValue;


}