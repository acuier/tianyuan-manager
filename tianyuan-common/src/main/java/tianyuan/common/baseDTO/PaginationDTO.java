package tianyuan.common.baseDTO;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/24 7:16.
 * @Describution:
 */
@Data
@Builder
public class PaginationDTO implements Serializable {

    private static final long serialVersionUID = -6078963746324902618L;
    private Integer currentPage ; //当前是第几页
    private Integer totalPages; //总共多少页
    private Integer pageSize; // 每页多少数据
    private Boolean hasNext; //是否有下一页数据
    private Boolean hasPrev; //是否有前一页数据
    private Long total;// 总共多少数据
}
