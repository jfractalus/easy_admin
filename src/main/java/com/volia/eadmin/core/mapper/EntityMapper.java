package com.volia.eadmin.core.mapper;

import com.volia.eadmin.core.annotation.AnnotationParser;
import com.volia.eadmin.core.meta.ColumnInfo;
import com.volia.eadmin.core.meta.RowInfo;
import com.volia.eadmin.core.meta.ViewMetaInfo;
import com.volia.eadmin.domain.AbstractEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.volia.eadmin.core.meta.ViewTypeField.valueOf;
import static com.volia.eadmin.util.JsonUtil.fromObject;
import static com.volia.eadmin.util.TypeFieldWrapperUtil.getViewValueRendererByType;
import static java.util.stream.Collectors.toCollection;

@Component
public class EntityMapper<T extends AbstractEntity>{
    @Autowired
    private AnnotationParser annotationParser;

    public ViewMetaInfo toModel(List<T> entities, String clazz) {
        ViewMetaInfo info = getMetaInfoByNameOfEntity(clazz);
        info.getTableViewInfo().setRows(buildRowsInfo(entities, info.getTableViewInfo().getColumns()));
        return info;
    }

    private ViewMetaInfo getMetaInfoByNameOfEntity(String nameOfEntity){
        return annotationParser.getViewMetaInfoMap().get(nameOfEntity);
    }

    private LinkedList<RowInfo> buildRowsInfo(List<T> entities, List<ColumnInfo> columns) {
        return entities.stream().map(entity -> buildRowInfo(entity, columns)).collect(toCollection(LinkedList::new));
    }

    public ViewMetaInfo toModel(T entity, String clazz) {
        ViewMetaInfo info = getMetaInfoByNameOfEntity(clazz);
        info.getTableViewInfo().setRows(Arrays.asList(buildRowInfo(entity, info.getTableViewInfo().getColumns())));
        return info;
    }

    private RowInfo buildRowInfo(T entity, List<ColumnInfo> columns) {
        return new RowInfo(entity.getId(), entity.shortCaption(), fromObject(entity),
                columns.stream()
                        .map(column -> getViewValueRendererByType(valueOf(column.getType()), annotationParser).getValue(entity, column))
                        .collect(toCollection(LinkedList::new)));
    }
}
