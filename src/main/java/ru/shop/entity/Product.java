package ru.shop.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "proudct")
public class Product {

    @Id
    private UUID id;

    private String name;

    private String description;

    private double price;

    private int quantity;

    private String sale_description;

    private Feedback feedback;

    private String tags;

    private ParametersEntity properties;

    private byte grade;

}
