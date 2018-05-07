package tianyuan.common.baseDTO;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/24 8:42.
 * @Describution: 排序
 */

public class SortDTO {

    //排序方式
    private String orderType;

    //排序字段
    private String orderField;

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public SortDTO(String orderType, String orderField) {
        this.orderType = orderType;
        this.orderField = orderField;
    }

    public SortDTO() {
    }

    public SortDTO(String orderField) {
        this.orderType = "DESC";
        this.orderField = orderField;
    }
}
