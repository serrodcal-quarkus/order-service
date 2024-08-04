package dev.serrodcal.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.List;

@Entity(name = "customers")
@Cacheable
public class Customer extends PanacheEntityBase {

    @Id
    @SequenceGenerator(allocationSize = 1, name = "customersSequence", schema = "public", sequenceName = "customers_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customersSequence")
    @Column(name = "id", nullable = false)
    public Long id;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false, unique = true)
    public String email;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Order> orders;

}
