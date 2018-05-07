package tianyuan.rbac.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tianyuan.entity.ApplicationTests;
import tianyuan.rbac.param.DepartmentParam;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/29 18:16.
 * @Describution:
 */
public class DepartmentInfoServiceImplTest extends ApplicationTests {

    @Autowired
    private DepartmentInfoServiceImpl departmentInfoService;

    @Test
    public void save() {

        DepartmentParam departmentParam = DepartmentParam.builder()
                .departmentName("阿萨斯")
                .departmentNickName("dfdfdfdf")
                .departmentAddress("qwqwqw")
                .departmentContacts("11")
                .departmentCorporation("aaa")
                .departmentSeq(1)
                .departmentPhone("asasas")
                .departmentEmail("asasas")
                .build();
        departmentInfoService.save(departmentParam);

    }
}