package owlvernyte.springfood.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "meal")
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Column(name = "amountLeft")
    private Long amountLeft;

    @Column(name = "price")
    private double Price;
}
