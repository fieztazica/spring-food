package owlvernyte.springfood.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Data
@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "OrderedAt")
    private Date OrderedAt;
    @Column(name = "PaidAt")
    private Date PaidAt;
    @Column(name = "total")
    private Double total;
    @Column(name = "paid")
    private Double paid;
    @Column(name = "changeMoney")
    private Double changeMoney;
    @Column(name = "user_id")
    private Long user_id;
}