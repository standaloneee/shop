package ru.shop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "sale")
public class Sale {
    @Id
    private UUID id;

    private Set<Product> products;

    private short discount;

    private Date startSaleDate;

    private int hoursToExpire;
}
