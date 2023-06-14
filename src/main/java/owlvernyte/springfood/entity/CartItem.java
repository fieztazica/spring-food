package owlvernyte.springfood.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    private Long Id;
    private String name;
    private Double price;
    private int quantity;
}
