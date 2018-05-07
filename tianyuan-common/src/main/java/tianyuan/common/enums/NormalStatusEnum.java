package tianyuan.common.enums;


import lombok.Getter;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/29 10:57.
 * @Describution:
 */
@Getter
public enum NormalStatusEnum {
    USED(0, "正常！"),
    LOCKED(1, "被禁用或已经冻结！"),
    NON_ACTIVE(2, "未激活！"),
    NON_ENABLED(3, "已删除！")
    ;
    private Integer code;
    private String message;

    NormalStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
