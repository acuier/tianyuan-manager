package tianyuan.common.enums;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018-04-10 16:59.
 * @Describution:
 */
/**
 * 运算符
 */
public enum OperatorSpecification {
    /**
     * 等于
     */
    eq(" = "),
    /**
     * 不等于
     */
    ne(" != "),
    /**
     * 大于
     */
    gt(" > "),
    /**
     * 小于
     */
    lt(" < "),
    /**
     * 大于等于
     */
    ge(" >= "),
    /**
     * 小于等于
     */
    le(" <= "),
    /**
     * 类似
     */
    like(" like "),
    /**
     * 类似
     */
    rlike(" like "),
    /**
     * 类似
     */
    llike(" like "),
    /**
     * 类似
     */
    ilike(" like "),
    /**
     * 包含
     */
    in(" in "),
    /**
     * 包含
     */
    notIn(" not in "),
    /**
     * 包含
     */
    not(" not "),
    /**
     * 为Null
     */
    isNull(" is NULL "),
    /**
     * 不为Null
     */
    isNotNull(" is not NULL ");
    OperatorSpecification(String operator) {
        this.operator = operator;
    }
    private String operator;
    public String getOperator() {
        return operator;
    }
    public void setOperator(String operator) {
        this.operator = operator;
    }
}
