package com.volia.eadmin.domain;

public interface ShortCaption {
    default String shortCaption(){
        return toString();
    }
}
