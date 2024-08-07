package dev.serrodcal.dbos;

import dev.serrodcal.dbos.metadata.Metadata;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity(name = "orders")
@Cacheable
public class OrderDBO extends PanacheEntityBase {

    @Id
    @SequenceGenerator(allocationSize = 1, name = "ordersSequence", schema = "public", sequenceName = "orders_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ordersSequence")
    @Column(name = "id", nullable = false)
    public Long id;

    @Column(nullable = false)
    public String product;

    @Column(nullable = false)
    public Integer quantity;

    @Embedded
    public Metadata metadata;

}
