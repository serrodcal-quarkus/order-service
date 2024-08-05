package dev.serrodcal.entities;

import dev.serrodcal.entities.metadata.Metadata;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity(name = "orders")
@Cacheable
public class Order extends PanacheEntityBase {

    @Id
    @SequenceGenerator(allocationSize = 1, name = "ordersSequence", schema = "public", sequenceName = "orders_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ordersSequence")
    @Column(name = "id", nullable = false)
    public Long id;

    @Column(nullable = false)
    @NotNull
    @NotBlank
    public String product;

    @Column(nullable = false)
    @NotNull
    public Integer quantity;

    @Embedded
    public Metadata metadata;

}
