package tianyuan.common.enums;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/29 11:27.
 * @Describution:
 */
public enum OperatorTypeEnum {

    INSERT(0, "添加/新增！"),
    UPDATE(1, "更新/修改！"),
    DELETE(2, "删除！")

    ;
    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    OperatorTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
