package owlvernyte.springfood.rest;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import owlvernyte.springfood.service.CartService;

@RestController
@RequestMapping("api/cart")
@CrossOrigin("*")
public class ApiCartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/size")
    public ResponseEntity<Integer> getSize(HttpSession session) {
        return ResponseEntity.status(HttpStatus.OK).body(
                cartService.getCart(session).getCartItems().size()
        );
    }
}
