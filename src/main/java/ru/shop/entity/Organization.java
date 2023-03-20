package ru.shop.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "organization")
public class Organization {
    @Id
    private UUID id;

    private String description;

    private String logo_url;

    private Set<Product> products;


}
