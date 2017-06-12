package com.volia.eadmin.core.mapper.wrapper;

import com.volia.eadmin.core.meta.ColumnInfo;

public interface Wrapper<R> {
    R getValue(Object entity, ColumnInfo columnInfo);
}
