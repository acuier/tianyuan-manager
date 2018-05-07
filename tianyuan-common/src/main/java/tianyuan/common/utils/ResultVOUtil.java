package tianyuan.common.utils;


import tianyuan.common.baseDTO.PaginationDTO;
import tianyuan.common.baseVO.ResultVO;
import tianyuan.common.enums.ResultEnum;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/15 20:28.
 * @Describution: 通用返回格式工具
 */
public class ResultVOUtil {

//    final static Boolean OK = true;
//    final static Boolean FAILURE = false;

    public static ResultVO success(Object object){
        ResultVO resultVO = new ResultVO();
//        resultVO.setSuccess(OK);
        resultVO.setMsg(ResultEnum.SUCCESS.getMessage());
        resultVO.setCode(ResultEnum.SUCCESS.getCode());
        resultVO.setPage(null);
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO success(){
        return success(null);
    }

    public static ResultVO success(Object object,PaginationDTO page){
        ResultVO resultVO = new ResultVO();
        resultVO.setPage(page);
        resultVO.setMsg(ResultEnum.SUCCESS.getMessage());
        resultVO.setCode(ResultEnum.SUCCESS.getCode());
        resultVO.setData(object);
        return resultVO;
    }
    public static ResultVO failure(ResultEnum resultEnum){
        ResultVO resultVO = new ResultVO();
        resultVO.setMsg(resultEnum.getMessage());
        resultVO.setCode(resultEnum.getCode());
        resultVO.setPage(null);
        resultVO.setData(null);
        return resultVO;
    }
    public static ResultVO failure(Integer code,String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setMsg(msg);
        resultVO.setCode(code);
        resultVO.setPage(null);
        resultVO.setData(null);
        return resultVO;
    }
}
