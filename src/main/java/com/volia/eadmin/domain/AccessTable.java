package com.volia.eadmin.domain;

import com.volia.eadmin.core.annotation.EasyAdmin;
import com.volia.eadmin.core.annotation.EasyAdminField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@Entity
@EasyAdmin(title = "Права доступа")
@Table(name = "access_table")
@ToString
public class AccessTable extends AbstractEntity {
    @Column(name = "role_name", nullable = false)
    @Enumerated(value = EnumType.STRING)
    @EasyAdminField(humanName = "Роль")
    private UserRole roleName;

    @Column(name = "access_table_name")
    @EasyAdminField(humanName = "Список запрещенных таблиц")
    private String accessTableName;

    @Embedded
    @EasyAdminField(humanName = "Права")
    private Crud crud = new Crud();

    @Override
    public String shortCaption() {
        return "";
    }
}
