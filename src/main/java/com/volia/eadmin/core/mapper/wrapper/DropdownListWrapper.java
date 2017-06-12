package com.volia.eadmin.core.mapper.wrapper;

import com.volia.eadmin.core.meta.ColumnInfo;
import com.volia.eadmin.domain.UserRole;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.volia.eadmin.util.ReflectionUtil.getDeclaredFieldValue;

public class DropdownListWrapper<T> implements Wrapper<DropdownEntityInfo<T>> {

    @Override
    public DropdownEntityInfo<T> getValue(Object entity, ColumnInfo columnInfo) {
        return new DropdownEntityInfo(Stream.of(UserRole.values()).collect(Collectors.toSet()),
                (Set<Enum>)getDeclaredFieldValue(entity, columnInfo.getNativeName()));
    }

}
