package com.volia.eadmin.util;

import com.volia.eadmin.core.meta.ViewTypeField;
import com.volia.eadmin.domain.AbstractEntity;
import com.volia.eadmin.domain.EmbeddableEntity;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.lang.reflect.Field;
import java.util.Collection;

import static com.volia.eadmin.core.meta.ViewTypeField.*;

public final class FieldTypeConverterUtil {
   private FieldTypeConverterUtil(){}

    public static String convertFieldTypeToString(Field field, boolean rich){
        Class<?> fieldType = field.getType();
        if(Long.class.isAssignableFrom(fieldType) || Long.TYPE.isAssignableFrom(fieldType) || Integer.class.isAssignableFrom(fieldType)  || Integer.TYPE.isAssignableFrom(fieldType)){
            return NUMBER.name();
        } else if(DateTime.class.isAssignableFrom(fieldType)){
            return DATE_TIME.name();
        } else if(LocalDate.class.isAssignableFrom(fieldType)){
            return LOCAL_DATE.name();
        } else if(String.class.isAssignableFrom(fieldType)){
            return rich ? RICH_TEXT.name() : TEXT.name();
        } else if(Boolean.class.isAssignableFrom(fieldType) || Boolean.TYPE.isAssignableFrom(fieldType) ){
            return BOOLEAN.name();
        } else if(AbstractEntity.class.isAssignableFrom(fieldType)){
            return SINGLE_ENTITY.name();
        }  else if(Collection.class.isAssignableFrom(fieldType)){
            String type = DROP_DOWN_LIST.name();
            if(ReflectionUtil.isEntity(field.getGenericType())){
                type = LIST_OF_ENTITY.name();
            }
            return type;
        } else if(Enum.class.isAssignableFrom(fieldType)){
            return ENUM.name();
        } else if(EmbeddableEntity.class.isAssignableFrom(fieldType)){
            return EMBEDDABLE_ENTITY.name();
        } else {
            throw new UnsupportedOperationException("Type is not supported");
        }
    }
}
