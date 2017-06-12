package com.volia.eadmin.util;

import com.volia.eadmin.core.annotation.AnnotationParser;
import com.volia.eadmin.core.mapper.wrapper.*;
import com.volia.eadmin.core.meta.ViewTypeField;

public final class TypeFieldWrapperUtil {
    private TypeFieldWrapperUtil(){}

    public static Wrapper getViewValueRendererByType(ViewTypeField type, AnnotationParser annotationParser){
        switch (type) {
            case DATE_TIME:
                return new DateTimeWrapper();
            case LOCAL_DATE:
                return new LocalDateWrapper();
            case SINGLE_ENTITY:
                return new EntityWrapper(annotationParser);
            case LIST_OF_ENTITY:
                return new ListEntityWrapper(annotationParser);
            case ENUM:
                return new EnumWrapper();
            case EMBEDDABLE_ENTITY:
                return new EmbeddedEntityWrapper();
            case DROP_DOWN_LIST:
                return new DropdownListWrapper();
            default:
                return new DefaultWrapper();
        }
    }
}
