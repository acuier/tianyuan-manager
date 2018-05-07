package tianyuan.rbac.service.impl;

import com.google.common.base.Preconditions;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import tianyuan.common.PluginBeans.TySpecification;
import tianyuan.common.baseDTO.SortDTO;

import tianyuan.common.utils.JsonMapper;
import tianyuan.common.utils.PageSortUtils;
import tianyuan.rbac.beans.LogType;
import tianyuan.rbac.dao.SystemLogDao;
import tianyuan.rbac.dto.SearchLogDto;


import tianyuan.rbac.model.admin.*;
import tianyuan.rbac.param.SearchLogParam;
import tianyuan.rbac.service.SystemLogService;

import java.util.List;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/4/2 8:05.
 * @Describution:
 */
@Service
@Slf4j
public class SystemLogServiceImpl implements SystemLogService {

    @Autowired
    private SystemLogDao logDao;
    @Autowired
    private RepositoryRestProperties properties;

//    @Autowired
//    private DepartmentInfoService deptService;

    @Override

    public Page<LogSystemWithBLOBs> searchPageList(SearchLogParam param, Integer currentPage, SortDTO... sortDTOS) {

        Pageable pageable = PageRequest.of(currentPage,properties.getDefaultPageSize(), PageSortUtils.basicSort(sortDTOS));
//        Page<SystemAcl> aclPage = aclAdo.findAllByAclModuleId(aclModuleId,pageable);
        //转换一下
//        List<SystemAcl> aclList = aclPage.getContent();

//        BeanValidator.check(param);
        Page<LogSystemWithBLOBs> logSystemPage = logDao.findAll(new TySpecification<LogSystemWithBLOBs>()
                .and(TySpecification.Cnd.like("newValue","%"+param.getNewValue()+"%"))
        .and(TySpecification.Cnd.like("oldValue","%"+param.getOldValue()+"%"))
        .and(TySpecification.Cnd.eq("operator",param.getOperator()))
               .asc("updateTime"),pageable
        );
        SearchLogDto logDto = new SearchLogDto();
        logDto.setType(param.getType());
        if (StringUtils.isNotBlank(param.getOldValue())){

        }
        return logSystemPage;//        return new PageImpl<>(aclList,pageable,aclPage.getTotalElements());
    }

    @Override
    public void saveDepLog(DepartmentInfo oldDept, DepartmentInfo newDept) {
        LogSystemWithBLOBs sysLog = new LogSystemWithBLOBs();
        sysLog.setOperatorType(LogType.TYPE_DEPT);
        sysLog.setTargetId(newDept == null ? oldDept.getDepartmentId():newDept.getDepartmentId());
        sysLog.setOldValue(oldDept == null ? "":JsonMapper.obj2String(oldDept));
        sysLog.setNewValue(newDept == null ? "":JsonMapper.obj2String(newDept));
        sysLog.setOperatorId(0);
        sysLog.setOperator("");
        sysLog.setLogStatus(0);
        logDao.save(sysLog);

    }

    @Override
    public void saveUserLog(SystemUser oldUser, SystemUser newUser) {
        LogSystemWithBLOBs sysLog = new LogSystemWithBLOBs();
        sysLog.setOperatorType(LogType.TYPE_USER);
        sysLog.setTargetId(newUser == null ? oldUser.getUserId():newUser.getUserId());
        sysLog.setOldValue(oldUser == null ? "":JsonMapper.obj2String(oldUser));
        sysLog.setNewValue(newUser == null ? "":JsonMapper.obj2String(newUser));
        sysLog.setOperatorId(0);
        sysLog.setOperator("");
        sysLog.setLogStatus(0);
        logDao.save(sysLog);
    }

    @Override
    public void saveAcLModuleLog(SystemPermission oldPermission, SystemPermission newPermission) {
        LogSystemWithBLOBs sysLog = new LogSystemWithBLOBs();
        sysLog.setOperatorType(LogType.TYPE_ACL_MODULE);
        sysLog.setTargetId(newPermission == null ? oldPermission.getPermissionId():newPermission.getPermissionId());
        sysLog.setOldValue(oldPermission == null ? "":JsonMapper.obj2String(oldPermission));
        sysLog.setNewValue(newPermission == null ? "":JsonMapper.obj2String(newPermission));
        sysLog.setOperatorId(0);
        sysLog.setOperator("");
        sysLog.setLogStatus(0);
        logDao.save(sysLog);
    }

    @Override
    public void saveAclLog(SystemAcl oldAcl, SystemAcl newAcl) {
        LogSystemWithBLOBs sysLog = new LogSystemWithBLOBs();
        sysLog.setOperatorType(LogType.TYPE_ACL);
        sysLog.setTargetId(newAcl == null ? oldAcl.getAclId():newAcl.getAclId());
        sysLog.setOldValue(oldAcl == null ? "":JsonMapper.obj2String(oldAcl));
        sysLog.setNewValue(newAcl == null ? "":JsonMapper.obj2String(newAcl));
        sysLog.setOperatorId(0);
        sysLog.setOperator("");
        sysLog.setLogStatus(0);
        logDao.save(sysLog);
    }

    @Override
    public void saveRoleLog(SystemRole oldRole, SystemRole newRole) {
        LogSystemWithBLOBs sysLog = new LogSystemWithBLOBs();
        sysLog.setOperatorType(LogType.TYPE_ROLE);
        sysLog.setTargetId(newRole == null ? oldRole.getRoleId():newRole.getRoleId());
        sysLog.setOldValue(oldRole == null ? "":JsonMapper.obj2String(oldRole));
        sysLog.setNewValue(newRole == null ? "":JsonMapper.obj2String(newRole));
        sysLog.setOperatorId(0);
        sysLog.setOperator("");
        sysLog.setLogStatus(0);
        logDao.save(sysLog);
    }

    @Override
    public void saveRoleAclLog(Integer roleId, List<Integer> oldRoleAclList, List<Integer> newRoleAclList){
        LogSystemWithBLOBs sysLog = new LogSystemWithBLOBs();
        sysLog.setOperatorType(LogType.TYPE_ROLE_ACL);
        sysLog.setTargetId(roleId);
        sysLog.setOldValue(oldRoleAclList == null ? "":JsonMapper.obj2String(oldRoleAclList));
        sysLog.setNewValue(newRoleAclList == null ? "":JsonMapper.obj2String(newRoleAclList));
        sysLog.setOperatorId(0);
        sysLog.setOperator("");
        sysLog.setLogStatus(0);
        logDao.save(sysLog);
    }

    @Override
    public void saveRoseUserLog(Integer roleId,List<Integer>  oldUserRoleList,List<Integer>  newUserRoleList){
        LogSystemWithBLOBs sysLog = new LogSystemWithBLOBs();
        sysLog.setOperatorType(LogType.TYPE_ROLE_USER);
        sysLog.setTargetId(roleId);
        sysLog.setOldValue(oldUserRoleList == null ? "":JsonMapper.obj2String(oldUserRoleList));
        sysLog.setNewValue(newUserRoleList == null ? "":JsonMapper.obj2String(newUserRoleList));
        sysLog.setOperatorId(0);
        sysLog.setOperator("");
        sysLog.setLogStatus(0);
        logDao.save(sysLog);
    }

    @Override
    public void recoverLog(Integer id) {
        //找到这条日志的数据
        LogSystemWithBLOBs sysLog = logDao.findById(id).orElse(null);
        Preconditions.checkNotNull(sysLog,"待还原的日志记录不存在！");
        switch (sysLog.getOperatorType()){
            case LogType.TYPE_DEPT:
//                //原来的值,待还原的部门（日志中的目标ID）
//                DepartmentInfo oldDept = deptService.getOne(sysLog.getTargetId());
//                //判断一下，现在还有没有
//                Preconditions.checkNotNull(oldDept,"待还原的部门不存在！");
//                //如果日志中没有值，则不更新,可能是新增或者删除时的日志
//                if (StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())){
//                    throw new AcmException(ResultEnum.ACL_NOT_EXIST);
//                }
//                //取出日志中的值
//                DepartmentInfo newDept = JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<DepartmentInfo>() {
//                });
//
//                saveDepLog(oldDept,newDept);
                break;
            case LogType.TYPE_USER:
                break;
            case LogType.TYPE_ACL_MODULE:
                break;
            case LogType.TYPE_ACL:
                break;
            case LogType.TYPE_ROLE:
                break;
            case LogType.TYPE_ROLE_ACL:
                //要还原的角色是否存在
                //changeROleAcl
                break;
            case LogType.TYPE_ROLE_USER:
                break;
            default:

        }


    }
}
