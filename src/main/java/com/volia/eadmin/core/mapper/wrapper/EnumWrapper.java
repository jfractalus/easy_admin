package com.volia.eadmin.core.mapper.wrapper;

import com.volia.eadmin.core.meta.ColumnInfo;
import static com.volia.eadmin.util.ReflectionUtil.getDeclaredFieldValue;

public class EnumWrapper implements Wrapper<CellEnumInfo> {
    @Override
    public CellEnumInfo getValue(Object entity, ColumnInfo columnInfo) {
        return new CellEnumInfo((Enum)getDeclaredFieldValue(entity, columnInfo.getNativeName()));
    }
}
