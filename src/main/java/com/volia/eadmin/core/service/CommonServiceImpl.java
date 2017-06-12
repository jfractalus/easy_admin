package com.volia.eadmin.core.service;

import com.volia.eadmin.core.mapper.wrapper.EntityInfo;
import com.volia.eadmin.core.meta.ColumnInfo;
import com.volia.eadmin.core.meta.RowInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.volia.eadmin.core.meta.ViewTypeField.*;

@Slf4j
@Service
public class CommonServiceImpl implements CommonService {

    @Override
    public String getUserName(HttpServletRequest request) {
        return request.getUserPrincipal().getName();
    }

    @Override
    public Set<String> getRequestMappingPrefixOfSubEntities(RowInfo rowInfo, List<ColumnInfo> columns) {
        Set<String> collect = IntStream.range(0, columns.size())
                .filter(i -> EnumSet.of(SINGLE_ENTITY, LIST_OF_ENTITY, EMBEDDABLE_ENTITY).contains(valueOf(columns.get(i).getType())))
                .mapToObj(i -> ((EntityInfo) rowInfo.getCells().get(i)).getLink())
                .collect(Collectors.toSet());
        return collect;
    }
}
