package owlvernyte.springfood.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "OrderedAt")
    private LocalDate OrderedAt;

    @Column(name = "PaidAt")
    private LocalDate PaidAt;

    @Column(name = "total")
    private Double total;

    @Column(name = "paid")
    private Double paid;

    @Column(name = "changeMoney")
    private Double changeMoney;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private Set<OrderDetail> orderDetails = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "user_id")
    private User user;
}
