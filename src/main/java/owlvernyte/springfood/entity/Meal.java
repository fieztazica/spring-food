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
    private double price;


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Meal() {
    }

    public Meal(Long id, String name, String category, String imageUrl, Long amountLeft, double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.imageUrl = imageUrl;
        this.amountLeft = amountLeft;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getAmountLeft() {
        return amountLeft;
    }

    public void setAmountLeft(Long amountLeft) {
        this.amountLeft = amountLeft;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
