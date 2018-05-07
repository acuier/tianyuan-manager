package tianyuan.rbac.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestProperties;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tianyuan.common.baseDTO.SortDTO;

import tianyuan.common.enums.ResultEnum;
import tianyuan.common.exception.ParamException;
import tianyuan.common.utils.BeanValidator;
import tianyuan.common.utils.PageSortUtils;
import tianyuan.rbac.dao.SystemAclDao;
import tianyuan.rbac.model.admin.SystemAcl;
import tianyuan.rbac.param.AclParam;
import tianyuan.rbac.service.SystemAclService;
import tianyuan.rbac.service.SystemLogService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/31 8:21.
 * @Describution:
 */
@Service
@Slf4j
@CacheConfig(cacheNames = "acl")//,keyGenerator = "wiselyKeyGenerator")
public class SystemAclServiceImpl implements SystemAclService {

    @Autowired
    private SystemAclDao aclDao;

    @Autowired
    private RepositoryRestProperties properties;

    @Autowired
    private SystemLogService logService;

    @Override
    @CacheEvict(allEntries = true)
    public void save(AclParam param) {

        BeanValidator.check(param);
        if (checkExist(param.getAclModuleId(),param.getAclName(),param.getAclId())){
            log.error("【创建权限点信息】参数不正确, param={}", param);
            throw new ParamException(ResultEnum.DUPLICATE_ACL_NAME);
        }
        SystemAcl acl = SystemAcl.builder().build();
        BeanUtils.copyProperties(param,acl);
        //生成一个code，作用：
        acl.setAclCode(generateCode());
        //设置操作员信息
        acl.setOperator("1");
        acl.setOperatorIp("sdsdsd");
        aclDao.save(acl);

        logService.saveAclLog(null,acl);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void update(AclParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getAclModuleId(),param.getAclName(),param.getAclId())){
            log.error("【创建权限点信息】参数不正确, param={}", param);
            throw new ParamException(ResultEnum.DUPLICATE_ACL_NAME);
        }
//        Optional<SystemAcl> oldAcl = aclAdo.findById(param.getAclId());
//        if (!oldAcl.isPresent()){
//            log.error("【创建权限点信息】参数不正确, param={}", param);
//            throw new ParamException(ResultEnum.ACL_NOT_EXIST);
//        }
        SystemAcl oldAcl = aclDao.findById(param.getAclId()).orElseThrow(()->new ParamException(ResultEnum
                .ACL_NOT_EXIST));

        SystemAcl newAcl = SystemAcl.builder().build();
        BeanUtils.copyProperties(param,newAcl);

        //设置操作员信息

        aclDao.save(newAcl);

        logService.saveAclLog(oldAcl,newAcl);
    }

    @Override
    public boolean checkExist(int aclModuleId, String aclName, Integer aclId) {
        if (aclId == null){
            aclId = -1 ;
        }
        return  (aclDao.countByAclModuleIdAndAclNameAndAclIdNot(aclModuleId,aclName,aclId)>0);
    }

    public String generateCode(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date())+"_"+(int)(Math.random()*100);
    }


    @Override
    public Page<SystemAcl> listByAclModuleID(Integer aclModuleId, Integer currentPage, SortDTO... dtos) {

        Pageable pageable = PageRequest.of(currentPage,properties.getDefaultPageSize(), PageSortUtils.basicSort(dtos));
        Page<SystemAcl> aclPage = aclDao.findAllByAclModuleId(aclModuleId,pageable);
        //转换一下
        List<SystemAcl> aclList = aclPage.getContent();
        return new  PageImpl<>(aclList,pageable,aclPage.getTotalElements());
    }

    @Override
    public boolean checkModule(Integer aclModuleId) {
        if (aclModuleId == null){aclModuleId = -1;}
        return aclDao.countByAclModuleId(aclModuleId)>0;
    }

    @Override
    @Cacheable
    public List<SystemAcl> listAll() {
        return aclDao.findAll();
    }


}
