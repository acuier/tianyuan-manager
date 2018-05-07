package tianyuan.rbac.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tianyuan.common.baseDTO.SortDTO;
import tianyuan.common.enums.ResultEnum;
import tianyuan.common.exception.ParamException;
import tianyuan.common.utils.BeanValidator;
import tianyuan.common.utils.PageSortUtils;
import tianyuan.common.utils.PasswordUtil;
import tianyuan.rbac.dao.SystemUserDao;
import tianyuan.rbac.model.admin.SystemUser;
import tianyuan.rbac.param.UserParam;
import tianyuan.rbac.service.SystemLogService;
import tianyuan.rbac.service.SystemUserService;

import java.util.List;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/30 10:57.
 * @Describution:
 */
@Service
@Slf4j
@CacheConfig(cacheNames = "user")//,keyGenerator = "wiselyKeyGenerator")
public class SystemUserServiceImpl implements SystemUserService {
    @Autowired
    private SystemUserDao userDao;
    @Autowired
    private SystemLogService logService;
    @Autowired
    private RepositoryRestProperties properties;


    @Override
    @CacheEvict(allEntries = true)
    public void save(UserParam param) {
        //1校验参数
        BeanValidator.check(param);
        //2检查数据，mail phone username不能重复
        if (checkUserNameExist(param.getUserName(),param.getUserId())){
            log.error("【创建用户信息】参数不正确,用户名已经存在！ param={}", param);
            throw new ParamException(ResultEnum.DUPLICATE_USER_NAME);
        }
        if (checkPhoneExist(param.getUserPhone(),param.getUserId())){
            log.error("【创建用户信息】参数不正确,电话被占用了！ param={}", param);
            throw new ParamException(ResultEnum.DUPLICATE_USER_PHONE);
        }
        if(checkEmailExist(param.getUserEmail(),param.getUserId())){
            log.error("【创建用户信息】参数不正确, 邮箱被占用了！param={}", param);
            throw new ParamException(ResultEnum.DUPLICATE_USER_EMAIL);
        }
        //---针对后台
        SystemUser user = SystemUser.builder().build();
        BeanUtils.copyProperties(param,user);
        //3密码生成
        String password = PasswordUtil.randomPassword();
        user.setUserPassword(encryptPassword(password));
        //4填写其他信息 todo:
        user.setOperator("1");//todo:
        user.setOperatorIp("1212");
        //5激活提示，发送邮件或者短信 todo:

        //6保存
        userDao.save(user);

        logService.saveUserLog(null,user);
    }

    /**
     * 加密密码
     */
    public  String encryptPassword(String password){
        return new BCryptPasswordEncoder().encode(password).trim();
    }

    public Boolean checkPhoneExist(String phone,Integer Id){

        return userDao.countByUserPhoneAndUserIdNot(phone,Id)>0;
    }

    public Boolean checkEmailExist(String email,Integer Id){

        return userDao.countByUserEmailAndUserIdNot(email,Id)>0;
    }

    @Override
    @CacheEvict(allEntries = true)
    public void update(UserParam param) {
        //1校验参数
        BeanValidator.check(param);
        //2检查数据，mail phone username不能重复
        if (checkUserNameExist(param.getUserName(),param.getUserId())){
            log.error("【创建用户信息】参数不正确,用户名已经存在！ param={}", param);
            throw new ParamException(ResultEnum.DUPLICATE_USER_NAME);
        }
        if (checkPhoneExist(param.getUserPhone(),param.getUserId())){
            log.error("【创建用户信息】参数不正确,电话被占用了！ param={}", param);
            throw new ParamException(ResultEnum.DUPLICATE_USER_PHONE);
        }
        if(checkEmailExist(param.getUserEmail(),param.getUserId())){
            log.error("【创建用户信息】参数不正确, 邮箱被占用了！param={}", param);
            throw new ParamException(ResultEnum.DUPLICATE_USER_EMAIL);
        }
        //3
//        Optional<SystemUser> oldUser = userDao.findById(param.getUserId());
//        if (!oldUser.isPresent()){
//            log.error("【创建用户信息】参数不正确, param={}", param);
//            throw new ParamException(ResultEnum.USER_NOT_EXIST);
//        }
        SystemUser oldUser = userDao.findById(param.getUserId())
                .orElseThrow(() -> new ParamException(ResultEnum.USER_NOT_EXIST));

        SystemUser newUser = SystemUser.builder().build();
        BeanUtils.copyProperties(param,newUser);

        userDao.save(newUser);

        logService.saveUserLog(oldUser,newUser);
    }

    @Override
    public Boolean checkUserNameExist(String userName, Integer Id) {
        return userDao.countByUserNameAndUserIdNot(userName,Id)>0;
    }

    @Override
    public SystemUser findByKeyword(String keyword) {
        SystemUser user = new SystemUser();
        user = userDao.findByKeyWord(keyword);
        if (user == null ){
            throw new ParamException(ResultEnum.USER_NOT_EXIST);
        }
        return user ;
    }

    @Override
    @Cacheable
    public List<SystemUser> listAll() {
        return userDao.findAll();
    }

    @Override
    public Integer countUserWithDeptId(Integer deptId) {
        return userDao.countByDepartmentId(deptId) ;
    }

    @Override
    public Page<SystemUser> listAllByDeptId(Integer deptId, Integer currentPage, SortDTO... sortDTOS) {
        Pageable pageable = PageRequest.of(currentPage,properties.getDefaultPageSize(), PageSortUtils.basicSort(sortDTOS));

        Page<SystemUser> userPage = userDao.findAllByDepartmentId(deptId,pageable);

        return new PageImpl<>(userPage.getContent(),pageable,userPage.getTotalElements());

    }

    @Override
    public List<SystemUser> listAllByUserIdIn(List<Integer> userIdList) {
        return userDao.findAllById(userIdList);
    }

    @Override
    public SystemUser findOne(Integer userId) {
        return userDao.findById(userId).orElseThrow( ()->new ParamException(ResultEnum
                .USER_NOT_EXIST));
    }

    @Override
    public SystemUser findOne(String userName) {
        if (StringUtils.isEmpty(userName)){
            throw new ParamException(ResultEnum.PARAM_ERROR);
        }
        return userDao.findByUserName(userName).orElseThrow( ()->new ParamException(ResultEnum
                .USER_NOT_EXIST));
    }

}
