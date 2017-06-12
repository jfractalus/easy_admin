package com.volia.eadmin.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.volia.eadmin.core.annotation.EasyAdmin;
import com.volia.eadmin.core.annotation.EasyAdminField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@EasyAdmin(title = "Клиенты")
@Table(name = "client")
@Slf4j
public class Client extends AbstractEntity {
    @EasyAdminField(humanName = "Имя")
    @Column(name = "name", nullable = false)
    private String name;

    @EasyAdminField(humanName = "Возраст")
    @Column(name = "age", nullable = false)
    private int age;

    @EasyAdminField(humanName = "Сообщения")
    @OneToMany(mappedBy = "client", cascade = {CascadeType.REMOVE, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JsonManagedReference
    private List<Message> messages = new ArrayList<>();

    @EasyAdminField(humanName = "Тип клиента")
    @Column(name = "type_client", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TypeClient typeClient;

    @Override
    public String shortCaption() {
        return name;
    }

}
