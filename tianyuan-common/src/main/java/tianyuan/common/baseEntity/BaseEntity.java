package tianyuan.common.baseEntity;

import tianyuan.common.enums.NormalStatusEnum;

import java.util.Date;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018-05-05 10:06.
 * @Describution:
 */
public class BaseEntity {

    private Integer status = NormalStatusEnum.USED.getCode();

    private String operator;

    private String operatorIp;

    private Date createTime;

    private Date updateTime;
}
