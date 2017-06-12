package com.volia.eadmin.core.service;

import com.volia.eadmin.core.meta.ColumnInfo;
import com.volia.eadmin.core.meta.RowInfo;
import com.volia.eadmin.core.meta.ViewMetaInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

public interface CommonService {
    String getUserName(HttpServletRequest request);
    Set<String> getRequestMappingPrefixOfSubEntities(RowInfo rowInfo, List<ColumnInfo> columns);
}
