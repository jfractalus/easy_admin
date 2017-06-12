package com.volia.eadmin.core.meta;

import lombok.Data;

import java.util.List;

@Data
public class RowInfo {
    private Long id;
    private String shortCaption;
    private String jsonViewRow;
    private List cells;

    public RowInfo(Long id, String shortCaption, String jsonViewRow, List cells) {
        this.id = id;
        this.shortCaption = shortCaption;
        this.jsonViewRow = jsonViewRow;
        this.cells = cells;
    }
}
