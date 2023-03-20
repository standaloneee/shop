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
@Table(name = "userentity")
public class UserEntity {

    @Id
    private UUID id;

    private String email;

    private String password_hash;

    private double balance;
}
