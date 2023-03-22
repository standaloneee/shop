package ru.shop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "sale")
@ToString
public class Sale {
    @Id
    private UUID id;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "product_sales",
//            joinColumns = @JoinColumn(name = "sale_id"),
//            inverseJoinColumns = @JoinColumn(name = "product_id"))
//    private Set<Product> products;

    private short discount;

    @Column(name = "start_date")
    @Future
    private LocalDate start_date;

    @Column(name = "expiration_time")
    private int expiration_time;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Sale sale = (Sale) o;
        return getId() != null && Objects.equals(getId(), sale.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
