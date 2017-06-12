package com.volia.eadmin.domain;

import com.google.gson.internal.LinkedHashTreeMap;
import com.volia.eadmin.core.annotation.EasyAdminField;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Map;

@Data
@Embeddable
public class Crud implements EmbeddableEntity{
    @Column(name = "`create`", columnDefinition="tinyint(1) default 1")
    @EasyAdminField(humanName = "Создание")
    private boolean create;

    @Column(name = "`read`", columnDefinition="tinyint(1) default 1")
    @EasyAdminField(humanName = "Просмотр")
    private boolean read;

    @Column(name = "`update`", columnDefinition="tinyint(1) default 1")
    @EasyAdminField(humanName = "Изменение")
    private boolean update;

    @Column(name = "`delete`", columnDefinition="tinyint(1) default 1")
    @EasyAdminField(humanName = "Удаление")
    private boolean delete;

    @Column(name = "visible", columnDefinition="tinyint(1) default 1")
    @EasyAdminField(humanName = "Видимость")
    private boolean visible;

    @Override
    public Map toMap() {
        Map result = new LinkedHashTreeMap();
        result.put("create", isCreate());
        result.put("read", isRead());
        result.put("update", isUpdate());
        result.put("delete", isDelete());
        result.put("visible", isVisible());
        return result;
    }

    @Override
    public String shortCaption() {
        StringBuilder result = new StringBuilder("");
        if(isCreate()){  result.append("c"); }
        if(isRead()){    result.append("r"); }
        if(isUpdate()){  result.append("u"); }
        if(isDelete()){  result.append("d"); }
        if(isVisible()){ result.append("v"); }
        return result.toString();
    }
}
