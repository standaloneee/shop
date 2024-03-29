package ru.shop.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import ru.shop.exception.ProductOutOfStockException;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    private UUID id;

    private String name;

    private String description;

    private double price;

    private int quantity;

    private String sale_description;

    private double grade;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "product_feedbacks",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "feedback_id")
    )
    private Set<Feedback> feedback;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_tags",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tag;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parameter_id")
    private ParametersEntity properties;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "product_sales",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "sale_id"))
    private Sale sale;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_users",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<Customer> customers;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "product_organizations",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "organization_id")
    )
    private Organization organization;

    private double all_grades_value;

    private int number_of_grades;

    private String status;

    public void updateGrade(double grade) {
        number_of_grades++;
        all_grades_value += grade;
        this.grade = all_grades_value / number_of_grades;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Product product = (Product) o;
        return getId() != null && Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public void decreaseQuantity() {
        if (quantity == 0) {
            throw new ProductOutOfStockException(name);
        }
        quantity -= 1;
    }

}
