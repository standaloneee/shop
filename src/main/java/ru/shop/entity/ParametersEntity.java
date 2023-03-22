package ru.shop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "parameters")
// Пока что заглушка
public class ParametersEntity {
    @Id
    private UUID id;

    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        ParametersEntity parametersEntity = (ParametersEntity) o;
        return getId() != null && Objects.equals(getId(), parametersEntity.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
