package owlvernyte.springfood.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class Cart {
    private List<CartItem> cartItems = new ArrayList<>();
    public void addItems(CartItem item) {
        boolean isExist = cartItems.stream().filter(i -> Objects.equals(i.getId(), item.getId()))
                .findFirst()
                .map(i -> { i.setQuantity(i.getQuantity() + item.getQuantity());
                    return true;
                })
                .orElse(false);
        if (!isExist) {
            cartItems.add(item);
        }
    }
    public void removeItems(Long Id) {
        cartItems.removeIf(item -> Objects.equals(item.getId(), Id));
    }
    public void updateItems(Long Id, int quantity) {
        cartItems.stream().filter(item -> Objects.equals(item.getId(), Id)).forEach(item -> item.setQuantity(quantity));
    }
}
