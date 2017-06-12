package com.volia.eadmin.core.meta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ColumnInfo {
    private final String name;
    private final boolean nullable;
    private final boolean unique;
    private final boolean richText;
    private final int length;
    private final String type;
    private final String nativeName;
    private final String dateMask;
    private final boolean visible;
    private final boolean editable;
}
