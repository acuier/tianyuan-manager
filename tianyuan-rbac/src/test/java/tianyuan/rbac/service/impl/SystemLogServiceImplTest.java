package tianyuan.rbac.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tianyuan.common.baseDTO.SortDTO;
import tianyuan.entity.ApplicationTests;
import tianyuan.rbac.param.SearchLogParam;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018-04-11 20:54.
 * @Describution:
 */
public class SystemLogServiceImplTest extends ApplicationTests {

    @Autowired
    private SystemLogServiceImpl service;
    @Test
    public void searchPageList() {
        SearchLogParam param = new SearchLogParam();
        param.setOldValue("22");
        param.setNewValue("22");
        param.setOperator("0");


        SortDTO sortDTOS = new SortDTO("ASC","operatorId");
//        Page<LogSystemWithBLOBs> page = service.searchPageList(param,0,sortDTOS);
//        System.out.println("?????"+page.getContent());
    }
}