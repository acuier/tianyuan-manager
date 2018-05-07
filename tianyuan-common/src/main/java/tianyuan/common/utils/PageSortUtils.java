package tianyuan.common.utils;


import org.springframework.data.domain.Sort;
import tianyuan.common.baseDTO.SortDTO;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/23 17:17.
 * @Describution:
 */
public class PageSortUtils {

    public static Sort basicSort(){
       return basicSort("DESC","updateTime");
    }


    //单字段排序
    public static Sort basicSort(String orderType, String orderField) {
        return  new Sort(Sort.Direction.fromString(orderType), orderField);

    }

    public static Sort basicSort(SortDTO... dtos) {
        Sort result = null;
        for(int i=0; i<dtos.length; i++) {
            SortDTO dto = dtos[i];
            if(result == null) {
                result = new Sort(Sort.Direction.fromString(dto.getOrderType()), dto.getOrderField());
            } else {
                result = result.and(new Sort(Sort.Direction.fromString(dto.getOrderType()), dto.getOrderField()));
            }
        }
      return result;
    }

}
