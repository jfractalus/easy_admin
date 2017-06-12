package com.volia.eadmin.core.meta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.LinkedList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TableViewInfo {
    private List<ColumnInfo> columns = new LinkedList<>();
    private List<RowInfo> rows = new LinkedList<>();
}
