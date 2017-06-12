package com.volia.eadmin.core.mapper.wrapper;

import com.volia.eadmin.core.meta.ColumnInfo;
import org.joda.time.LocalDate;
import static com.volia.eadmin.util.DateTimeUtil.printLocalDateByPattern;
import static com.volia.eadmin.util.ReflectionUtil.getDeclaredFieldValue;

public class LocalDateWrapper implements Wrapper {
    @Override
    public Object getValue(Object entity, ColumnInfo columnInfo) {
        return printLocalDateByPattern((LocalDate) getDeclaredFieldValue(entity, columnInfo.getNativeName()), columnInfo.getDateMask());
    }
}
