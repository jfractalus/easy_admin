package com.volia.eadmin.core.mapper.wrapper;

import com.volia.eadmin.core.annotation.AnnotationParser;
import com.volia.eadmin.core.meta.ColumnInfo;
import com.volia.eadmin.domain.AbstractEntity;
import static com.volia.eadmin.util.JsonUtil.fromObject;
import static com.volia.eadmin.util.ReflectionUtil.getDeclaredFieldValue;
import static com.volia.eadmin.util.ReflectionUtil.getObjectNameByFieldName;

public class EntityWrapper extends AbstractEntityWrapper<CellEntityInfo> {

    public EntityWrapper(AnnotationParser annotationParser) {
        super(annotationParser);
    }

    @Override
    public CellEntityInfo getValue(Object rootEntity, ColumnInfo columnInfo) {
        CellEntityInfo info = new CellEntityInfo(getAnnotationParser().getViewMetaInfoMap().get(getObjectNameByFieldName(rootEntity, columnInfo.getNativeName())).getRequestMappingPrefix());
        AbstractEntity subEntity = (AbstractEntity) getDeclaredFieldValue(rootEntity, columnInfo.getNativeName());
        return info.addValue(subEntity, fromObject(subEntity));
    }
}
