package com.volia.eadmin.core.support;

import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

public interface Filter<T> {
    default List<T> filter(List<T> items, Predicate<T> predicate){
        return items.stream()
                .filter(predicate)
                .collect(toList());
    }
}
