package org.frogsoft.mall.common.util;

import java.util.List;
import javax.persistence.AttributeConverter;
import com.alibaba.fastjson.JSON;

public class JpaConverterListJson implements AttributeConverter<Object, String> {
    @Override
    public String convertToDatabaseColumn(Object o) {
        return JSON.toJSONString(o);
    }

    @Override
    public Object convertToEntityAttribute(String s) {
        return JSON.parseArray(s);
    }


}
