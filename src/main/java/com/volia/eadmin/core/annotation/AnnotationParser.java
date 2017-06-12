package com.volia.eadmin.core.annotation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.volia.eadmin.core.meta.*;
import com.volia.eadmin.util.FieldTypeConverterUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.PostConstruct;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static com.volia.eadmin.util.ReflectionUtil.getObjectByGenericSuperclass;
import static com.volia.eadmin.util.ReflectionUtil.loadClass;
import static org.springframework.util.StringUtils.isEmpty;

@Slf4j
@Configuration
@Getter
public class AnnotationParser {
    @Value("${application.controllers.path}")
    private String pathToControllers;
    private Map<String, ViewMetaInfo> viewMetaInfoMap = new HashMap<>();

    @PostConstruct
    public void initMetaInfo() {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Controller.class));
        for (BeanDefinition beanDefinition : scanner.findCandidateComponents(pathToControllers)){
            Class controllerClass = loadClass(scanner.getResourceLoader().getClassLoader(), beanDefinition.getBeanClassName());
            RequestMapping annotation = (RequestMapping)controllerClass.getAnnotation(RequestMapping.class);
            if(annotation != null){
                ViewMetaInfo.ViewMetaInfoBuilder viewBuilder = ViewMetaInfo.builder();
                String[] requestMapping = annotation.value();
                viewBuilder.requestMappingPrefix(requestMapping[0]);
                Class entityClass = getObjectByGenericSuperclass(controllerClass);
                EasyAdmin easyAdminRootAnnotation = (EasyAdmin)entityClass.getAnnotation(EasyAdmin.class);
                if(easyAdminRootAnnotation != null){
                    String title = easyAdminRootAnnotation.title();
                    viewBuilder.title(!isEmpty(title) ? title : entityClass.getSimpleName());
                    viewMetaInfoMap.put(entityClass.getSimpleName(), fillViewMetaInfo(entityClass, easyAdminRootAnnotation, viewBuilder.build()));
                }
            }
        }
    }

    private ViewMetaInfo fillViewMetaInfo(Class entityClass, EasyAdmin easyAdminRootAnnotation, ViewMetaInfo view) {
        view = fillAvailableOperation(easyAdminRootAnnotation.crud(), view);
        view = fillColumnInfo(getFields(entityClass), view);
        return view;
    }

    private ViewMetaInfo fillAvailableOperation(String[] crud, ViewMetaInfo view) {
        for (String s : crud) {
            view.getAvailableOperation().add(CrudOperation.getByValue(s));
        }
        return view;
    }


    private ViewMetaInfo fillColumnInfo(Field[] mergeEntityFields, ViewMetaInfo view) {
        for (Field field : mergeEntityFields) {
            if(field.isAnnotationPresent(EasyAdminField.class)){
                EasyAdminField easyAdminFieldAnnotation = field.getAnnotation(EasyAdminField.class);
                String fieldType = FieldTypeConverterUtil.convertFieldTypeToString(field, easyAdminFieldAnnotation.richText());
                ColumnInfo.ColumnInfoBuilder builder = ColumnInfo.builder();
                builder = fillInfoByColumnAnnotation(builder, field.getAnnotation(Column.class));
                builder = fillInfoByJoinColumnAnnotation(builder, field.getAnnotation(JoinColumn.class));
                builder = fillInfoByEasyAdminFieldAnnotation(builder, easyAdminFieldAnnotation);
                builder = fillDateMaskByJsonFormatAnnotation(builder, field.getAnnotation(JsonFormat.class));
                builder = fillTypeAndName(builder, fieldType, field.getName());
                view.getTableViewInfo().getColumns().add(builder.build());
            }
        }
        return view;
    }

    private Field[] getFields(Class entityClass) {
        Field[] fieldsOfAbstractEntity = entityClass.getSuperclass().getDeclaredFields();
        Field[] fieldsOfEntity = entityClass.getDeclaredFields();
        return Stream.of(fieldsOfAbstractEntity, fieldsOfEntity).flatMap(Stream::of).toArray(Field[]::new);
    }

    private ColumnInfo.ColumnInfoBuilder fillInfoByColumnAnnotation(ColumnInfo.ColumnInfoBuilder builder, Column column) {
        if(column != null){
            builder.nullable(column.nullable())
                    .unique(column.unique())
                    .length(column.length());
        }
        return builder;
    }


    private ColumnInfo.ColumnInfoBuilder fillInfoByJoinColumnAnnotation(ColumnInfo.ColumnInfoBuilder builder, JoinColumn joinColumn) {
        if(joinColumn != null){
            builder.nullable(joinColumn.nullable())
                .unique(joinColumn.unique());
        }
        return builder;
    }


    private ColumnInfo.ColumnInfoBuilder fillInfoByEasyAdminFieldAnnotation(ColumnInfo.ColumnInfoBuilder builder, EasyAdminField easyAdminField) {
        if(easyAdminField != null){
            builder.richText(easyAdminField.richText())
                    .name(!isEmpty(easyAdminField.humanName()) ? easyAdminField.humanName() : "")
                    .visible(easyAdminField.visible())
                    .editable(easyAdminField.editable());
        }
        return builder;
    }

    private ColumnInfo.ColumnInfoBuilder fillDateMaskByJsonFormatAnnotation(ColumnInfo.ColumnInfoBuilder builder, JsonFormat jsonFormat){
        if(jsonFormat != null){
            builder.dateMask(jsonFormat.pattern());
        }
        return builder;
    }


    private ColumnInfo.ColumnInfoBuilder fillTypeAndName(ColumnInfo.ColumnInfoBuilder builder, String fieldType, String fieldName) {
        return builder
                .type(fieldType)
                .nativeName(fieldName);
    }
}
