package com.volia.eadmin.core.meta;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.util.*;

@Getter
@Builder
@ToString
public class ViewMetaInfo {
    private final String title;
    private final String requestMappingPrefix;
    private final Set<CrudOperation> availableOperation = new TreeSet<>();
    private final TableViewInfo tableViewInfo = new TableViewInfo();
}
