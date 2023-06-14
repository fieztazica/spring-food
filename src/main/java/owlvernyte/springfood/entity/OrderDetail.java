package owlvernyte.springfood.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "meal")
    private String Meal;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "total_price")
    private double totalPrice;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;
}
