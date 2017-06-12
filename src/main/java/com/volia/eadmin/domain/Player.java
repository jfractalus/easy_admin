package com.volia.eadmin.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.volia.eadmin.core.annotation.EasyAdmin;
import com.volia.eadmin.core.annotation.EasyAdminField;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@EasyAdmin(title = "Игрок")
@Table(name = "player")
@Slf4j
public class Player extends AbstractEntity {
    @EasyAdminField(humanName = "Имя")
    @Column(name = "name", nullable = false)
    private String name;

    @EasyAdminField(humanName = "Возраст")
    @Column(name = "age", nullable = false)
    private int age;

    @JsonFormat(pattern = "dd.MM.yyyy")
    @EasyAdminField(humanName = "День рождения")
    @Column(name = "birthday")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate birthday;

    @JsonFormat(pattern="dd.MM.yyyy HH:mm")
    @EasyAdminField(humanName = "Замечен")
    @Column(name = "last_seen", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime lastSeen;

    @Override
    public String shortCaption() {
        return name;
    }
}
