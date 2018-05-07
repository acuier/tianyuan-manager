package tianyuan.common.utils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.collections.MapUtils;
import tianyuan.common.exception.ParamException;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

/**
 * @Author: Created by 崔先生
 * @Date: Create on 2018/3/29 16:39.
 * @Describution: 参数校验工具类
 */
public class BeanValidator {

    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    private static <T> Map<String, String> validate(T t, Class... groups) {
        Validator validator = validatorFactory.getValidator();
        Set validateResult = validator.validate(t, groups);
        if (validateResult.isEmpty()) {
            return Collections.emptyMap();
        } else {
            LinkedHashMap errors = new LinkedHashMap();
            Iterator iterator = validateResult.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation violation = (ConstraintViolation) iterator.next();
                errors.put(violation.getPropertyPath().toString(), violation.getMessage());
            }

            return errors;
        }
    }

    private static Map<String,String> validateList(Collection<?> collection){
        Preconditions.checkNotNull(collection);
        Iterator iterator = collection.iterator();
        Map errors ;
        do {
            if (!iterator.hasNext()){
                return Collections.emptyMap();
            }
            Object object = iterator.next();
            errors = validate(object,new Class[0]);
        } while (errors.isEmpty());
        return errors;
    }

    public static Map<String,String> validateObject(Object first,Object... objects){

        if(objects != null && objects.length > 0 ){
            return validateList(Lists.asList(first,objects));
        } else {
            return validate(first,new Class[0]);
        }

    }

    public static void check(Object param) throws ParamException {

        Map<String,String> map = BeanValidator.validateObject(param);
        if(MapUtils.isNotEmpty(map)) {
            throw new ParamException(map.toString());
        }
    }
}
