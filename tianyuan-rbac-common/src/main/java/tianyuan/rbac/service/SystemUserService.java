package tianyuan.rbac.service;

import org.springframework.data.domain.Page;
import tianyuan.common.baseDTO.SortDTO;
import tianyuan.rbac.model.admin.SystemUser;
import tianyuan.rbac.param.UserParam;

import java.util.List;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/30 10:55.
 * @Describution:
 */
public interface SystemUserService {

    void save(UserParam param);

    void update(UserParam param);

    Boolean checkPhoneExist(String phone,Integer Id);

    Boolean checkEmailExist(String email,Integer Id);

    Boolean checkUserNameExist(String userName,Integer Id);

    SystemUser findByKeyword(String keyword);

    SystemUser findOne(Integer userId);

    SystemUser findOne(String userName);

    List<SystemUser> listAll();

    List<SystemUser> listAllByUserIdIn(List<Integer> userIdList);

    Page<SystemUser> listAllByDeptId(Integer deptId, Integer currentPage, SortDTO... sortDTOS);

    Integer countUserWithDeptId(Integer deptId);
}
