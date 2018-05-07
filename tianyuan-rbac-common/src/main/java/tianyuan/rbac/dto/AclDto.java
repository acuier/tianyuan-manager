package tianyuan.rbac.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import tianyuan.rbac.model.admin.SystemAcl;

/**
 * 权限点
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/31 10:52.
 * @Describution:
 */
@Getter
@Setter
@ToString
public class AclDto extends SystemAcl {

    /**是否默认选中 .*/
    private boolean checked = false;

    /**是否拥有操作权限 .*/
    private boolean hasAcl = false;

    //SystemAcl 转换为 AclDto
    public static AclDto acl2Acldto(SystemAcl acl){
        AclDto dto = new AclDto();
        BeanUtils.copyProperties(acl,dto);
        return dto;
    }
}
