package com.volia.eadmin.core.mapper.wrapper;

import lombok.Getter;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class DropdownEntityInfo<T> {

    private final Set<T> dropSet;
    private final Set<T> selectedValues;

    public DropdownEntityInfo(Set<T> dropSet, Set<T> selectedValues) {
        this.dropSet = dropSet;
        this.selectedValues = selectedValues;
    }

    public String toString() {
        return selectedValues.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }
}
