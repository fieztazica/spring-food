package owlvernyte.springfood.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import owlvernyte.springfood.entity.Cart;
import owlvernyte.springfood.entity.Order;
import owlvernyte.springfood.entity.OrderDetail;
import owlvernyte.springfood.service.CartService;
import owlvernyte.springfood.service.OrderDetailService;
import owlvernyte.springfood.service.OrderService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderDetailService orderDetailService;
    @GetMapping
    public String showAllOrder(Model model) {
        List<Order> orders = orderService.getAll();
        model.addAttribute("orders", orders);
        return "order/list";
    }

    @GetMapping("/add")
    public String addOrder(Model model) {
        model.addAttribute("order", new Order());
        return "order/add";
    }

    @PostMapping("/add")
    public String addOrder(@ModelAttribute("order") Order order) {
        orderService.addOrder(order);
        return "redirect:/orders";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable("id") Long id) {
        orderService.Delete(id);
        return "redirect:/orders";
    }

    @GetMapping("/edit/{id}")
    public String EditOrderF(@PathVariable("id") Long id, Model model) {
        Order order = orderService.getById(id);
        model.addAttribute("order", order);
        return "order/edit";
    }

    @PostMapping("/edit")
    public String editOrder(@ModelAttribute("order") Order orderUpdate) {
        Order order = orderService.getById(orderUpdate.getId());
        orderService.updateOrder(orderUpdate);
        return "redirect:/orders";
    }
    @GetMapping("cash-pay")
    public String cashPay(HttpSession session) {
        cartService.removeCart(session);
        orderService.removeSessionOrder(session);
        return "order/cash-result";
    }
}
