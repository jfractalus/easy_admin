package com.volia.eadmin.core.mapper.wrapper;

import com.volia.eadmin.core.meta.ColumnInfo;
import com.volia.eadmin.domain.EmbeddableEntity;

import static com.volia.eadmin.util.ReflectionUtil.getDeclaredFieldValue;

public class EmbeddedEntityWrapper implements Wrapper {
    @Override
    public Object getValue(Object entity, ColumnInfo columnInfo) {
        return new EmbeddableEntityInfo((EmbeddableEntity)getDeclaredFieldValue(entity, columnInfo.getNativeName()));
    }
}
