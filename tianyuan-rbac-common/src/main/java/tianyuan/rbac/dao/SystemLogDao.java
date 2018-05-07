package tianyuan.rbac.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import tianyuan.rbac.model.admin.LogSystemWithBLOBs;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/4/2 8:06.
 * @Describution:
 */
public interface SystemLogDao extends JpaRepository<LogSystemWithBLOBs,Integer>,JpaSpecificationExecutor<LogSystemWithBLOBs> {

}
