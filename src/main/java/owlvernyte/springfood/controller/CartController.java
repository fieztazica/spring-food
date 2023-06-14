package owlvernyte.springfood.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import owlvernyte.springfood.entity.Cart;
import owlvernyte.springfood.entity.Order;
import owlvernyte.springfood.entity.OrderDetail;
import owlvernyte.springfood.entity.User;
import owlvernyte.springfood.service.CartService;
import owlvernyte.springfood.service.OrderDetailService;
import owlvernyte.springfood.service.OrderService;
import owlvernyte.springfood.service.UserService;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping
    public String showCart(HttpSession session, @NotNull Model model) {
        model.addAttribute("cart", cartService.getCart(session));
        model.addAttribute("totalPrice", cartService.getSumPrice(session));
        model.addAttribute("totalQuantity", cartService.getSumQuantity(session));
        return "cart/index";
    }

    @GetMapping("/removeFromCart/{id}")
    public String removeFromCart(HttpSession session, @PathVariable Long id) {
        var cart = cartService.getCart(session);
        cart.removeItems(id);
        return "redirect:/cart";
    }

    @GetMapping("/updateCart/{id}/{quantity}")
    public String updateCart(HttpSession session, @PathVariable Long id, @PathVariable int quantity) {
        var cart = cartService.getCart(session);
        cart.updateItems(id, quantity);
        return "cart/index";
    }

    @GetMapping("/clearCart")
    public String clearCart(HttpSession session) {
        cartService.removeCart(session);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model, HttpSession session, Principal principal) {
        Cart cart = cartService.getCart(session);
        model.addAttribute("cart", cart);
        return "order/checkout";
    }

    @PostMapping("/checkout")
    public String checkout(HttpSession session, Principal principal) {
        try {
            Cart cart = cartService.getCart(session);
            if (cart.getCartItems().isEmpty()) return "redirect:/cart";

            double totalPrice = cart.getCartItems()
                    .stream()
                    .map(x -> x.getPrice() * x.getQuantity())
                    .mapToDouble(x -> x)
                    .sum();

            double totalBill = totalPrice;
            User user = userService.findByUsername(principal.getName());
            Order order = new Order();
            order.setTotal(totalBill);
            order.setUser(user);
            order.setOrderedAt(LocalDate.now());
            orderService.addOrder(order);
            setOrderDetailsFromCart(order,cart);

            return "redirect:/orders/cash-pay";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "not-found";
        }
    }

    private void setOrderDetailsFromCart(Order order, Cart cart) {
        Set<OrderDetail> orderDetails = new HashSet<>();

        cart.getCartItems().forEach(item -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setMeal(item.getName());
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setTotalPrice(item.getQuantity() * item.getPrice());
            orderDetails.add(orderDetail);
            orderDetail.setOrder(order);
            orderDetailService.addOrderDetail(orderDetail);
        });
        order.setOrderDetails(orderDetails);
    }
}
