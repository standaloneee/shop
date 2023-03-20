package ru.shop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
}
