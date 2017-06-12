package com.volia.eadmin.core.mapper.wrapper;

import com.volia.eadmin.domain.EmbeddableEntity;

import java.util.Map;

public class EmbeddableEntityInfo implements EmbeddableEntity, EntityInfo {

    private EmbeddableEntity item;

    public EmbeddableEntityInfo(EmbeddableEntity item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return item.shortCaption();
    }

    @Override
    public Map toMap() {
        return item.toMap();
    }
}
