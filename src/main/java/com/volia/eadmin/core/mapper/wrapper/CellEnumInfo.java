package com.volia.eadmin.core.mapper.wrapper;

import lombok.Getter;
import java.util.Optional;

@Getter
public class CellEnumInfo {
    private String name;
    private Object[] available;

    public CellEnumInfo(Enum source) {
        if(source != null){
            this.name = source.name();
            this.available = source.getClass().getEnumConstants();
        }
    }

    @Override
    public String toString() {
        return Optional.ofNullable(name).orElse("");
    }
}
