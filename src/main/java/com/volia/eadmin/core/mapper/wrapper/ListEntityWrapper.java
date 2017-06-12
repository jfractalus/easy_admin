package com.volia.eadmin.core.mapper.wrapper;

import com.volia.eadmin.core.annotation.AnnotationParser;
import com.volia.eadmin.core.meta.ColumnInfo;
import com.volia.eadmin.domain.AbstractEntity;
import com.volia.eadmin.util.ReflectionUtil;

import java.util.List;

import static com.volia.eadmin.util.JsonUtil.fromObject;
import static com.volia.eadmin.util.ReflectionUtil.getDeclaredFieldValue;
import static org.springframework.util.CollectionUtils.isEmpty;

public class ListEntityWrapper extends AbstractEntityWrapper<CellEntityInfo> {
    public ListEntityWrapper(AnnotationParser annotationParser) {
        super(annotationParser);
    }

    @Override
    public CellEntityInfo getValue(Object entity, ColumnInfo columnInfo) {
        CellEntityInfo info = new CellEntityInfo(getAnnotationParser().getViewMetaInfoMap().get(ReflectionUtil.getNameOfGenericEntity(entity, columnInfo.getNativeName())).getRequestMappingPrefix());
        List<AbstractEntity> values = (List)getDeclaredFieldValue(entity, columnInfo.getNativeName());
        if(!isEmpty(values)){
            values.stream().forEach(v -> info.addValue(v, fromObject(v)));
        }
        return info;
    }
}
