package com.volia.eadmin.core.mapper.wrapper;

import com.volia.eadmin.core.meta.ColumnInfo;

import static com.volia.eadmin.util.ReflectionUtil.getDeclaredFieldValue;

public class DefaultWrapper implements Wrapper {
    @Override
    public Object getValue(Object entity, ColumnInfo columnInfo) {
        return getDeclaredFieldValue(entity, columnInfo.getNativeName());
    }
}
