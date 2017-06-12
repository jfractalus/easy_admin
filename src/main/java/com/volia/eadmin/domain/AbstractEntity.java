package com.volia.eadmin.domain;

import com.volia.eadmin.core.annotation.EasyAdmin;
import com.volia.eadmin.core.annotation.EasyAdminField;
import com.volia.eadmin.core.support.Filter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@EasyAdmin
@MappedSuperclass
public abstract class AbstractEntity implements ShortCaption, Filter {
    @Id
    @EasyAdminField(humanName = "#", editable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private Long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity that = (AbstractEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}