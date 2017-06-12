package com.volia.eadmin.core.mapper.wrapper;

import com.volia.eadmin.domain.AbstractEntity;
import lombok.Getter;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Getter
public class CellEntityInfo implements EntityInfo {
    private String link;
    private Map<Long, String> shortCaptions = new TreeMap<>();
    private Map<Long, String> jsonViews = new TreeMap<>();

    public CellEntityInfo(String link){
        this.link = "/" + link;
    }

    public CellEntityInfo addValue(AbstractEntity value, String jsonView){
        if(value != null){
            shortCaptions.put(value.getId(), value.shortCaption());
            jsonViews.put(value.getId(), jsonView);
        }
        return this;
    }

    @Override
    public String toString() {
        return shortCaptions.values().stream().collect(Collectors.joining(", "));
    }
}
