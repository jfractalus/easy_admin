package com.volia.eadmin.domain;

import com.volia.eadmin.core.annotation.EasyAdmin;
import com.volia.eadmin.core.annotation.EasyAdminField;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@EasyAdmin(title = "Пользователи системы")
@Table(name = "system_user")
public class SystemUser extends AbstractEntity {
    @EasyAdminField(humanName = "Логин")
    @Column(name = "login", nullable = false, unique = true, length = 100)
    private String login;

    @EasyAdminField(humanName = "Пароль")
    @Column(name = "password", nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    @Column(name = "role")
    @OrderColumn()
    @EasyAdminField(humanName = "Роли")
    private Set<UserRole> userRoles = new HashSet<>();

    @EasyAdminField(humanName = "Пользователь активный")
    @Column(name = "is_enabled", nullable = false, columnDefinition="tinyint(1) default 1")
    private boolean enabled = false;

    @Override
    public String shortCaption() {
        return login;
    }
}
