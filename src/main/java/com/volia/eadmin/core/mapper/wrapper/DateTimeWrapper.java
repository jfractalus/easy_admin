package com.volia.eadmin.core.mapper.wrapper;

import com.volia.eadmin.core.meta.ColumnInfo;
import org.joda.time.DateTime;

import static com.volia.eadmin.util.DateTimeUtil.printDateTimeByPattern;
import static com.volia.eadmin.util.ReflectionUtil.getDeclaredFieldValue;

public class DateTimeWrapper implements Wrapper {
    @Override
    public Object getValue(Object entity, ColumnInfo columnInfo) {
        return printDateTimeByPattern((DateTime) getDeclaredFieldValue(entity, columnInfo.getNativeName()), columnInfo.getDateMask());
    }
}
