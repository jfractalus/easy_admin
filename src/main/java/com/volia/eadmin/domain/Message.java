package com.volia.eadmin.domain;

import com.fasterxml.jackson.annotation.*;
import com.volia.eadmin.core.annotation.EasyAdmin;
import com.volia.eadmin.core.annotation.EasyAdminField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import javax.persistence.*;

@Getter
@Setter
@Entity
@EasyAdmin(title = "Имя энтити")
@Table(name = "message")
@ToString
public class Message extends AbstractEntity {

    @EasyAdminField(humanName = "Код ошибки")
    @Column(name = "code", nullable = false)
    private String code;

    @EasyAdminField(humanName = "Значение", richText = true)
    @Column(name = "value", nullable = false, unique = true, length = 100)
    private String value;

    @JsonFormat(pattern="dd.MM.yyyy HH:mm")
    @EasyAdminField(humanName = "Дата и время")
    @Column(name = "date_and_time", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime dateAndTime;

    @JsonFormat(pattern = "dd.MM.yyyy")
    @EasyAdminField(humanName = "Дата")
    @Column(name = "local_date")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate localDate;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @EasyAdminField(humanName = "Клиент")
    @JsonBackReference
    private Client client;

    @EasyAdminField(humanName = "Примитив тест")
    @Column(name = "int_test", nullable = false)
    private int intTest;

    @Override
    public String shortCaption() {
        return value;
    }
}
