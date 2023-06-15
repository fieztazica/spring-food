package owlvernyte.springfood.controller;

import jakarta.servlet.http.HttpSession;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import owlvernyte.springfood.constants.Role;
import owlvernyte.springfood.entity.Order;
import owlvernyte.springfood.entity.OrderDetail;
import owlvernyte.springfood.entity.User;
import owlvernyte.springfood.repository.IRoleRepository;
import owlvernyte.springfood.service.CartService;
import owlvernyte.springfood.service.OrderDetailService;
import owlvernyte.springfood.service.OrderService;
import owlvernyte.springfood.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @Autowired
    private IRoleRepository roleRepository;
    Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderDetailService orderDetailService;
    @GetMapping
    public String showAllOrder(Model model,  Principal principal) {
        User user = userService.findByUsername(principal.getName());
        if (user.getRoles().contains(roleRepository.findRoleById(Role.ADMIN.value))){
            List<Order> orders = orderService.getAll();
            model.addAttribute("orders", orders);
        }
        else {
            List<Order> orders = orderService.findAllByUser(user);
            model.addAttribute("orders", orders);
        }
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
        Order order = orderService.findOrderById(id);
        List<OrderDetail> orderDetails = orderDetailService.getOrderDetailByOrderId(id);
        orderDetails.forEach(orderDetail -> {
            orderDetailService.deleteOrderDetail(orderDetail.getId());
        });
        order.setOrderDetails(null);
        order.setUser(null);
        orderService.updateOrder(order);
        orderService.delete(id);
        return "redirect:/orders";
    }

    @GetMapping("/edit/{id}")
    public String getEditOrderForm(@PathVariable("id") Long id, Model model) {
        Order order = orderService.findOrderById(id);
        model.addAttribute("order", order);
        return "order/edit";
    }

    @PostMapping("/edit")
    public String editOrder(@ModelAttribute("order") Order orderUpdate) {
        Order order = orderService.findOrderById(orderUpdate.getId());
        orderService.updateOrder(order);
        return "redirect:/orders";
    }

    @GetMapping("/cash-pay")
    public String cashPay() {
        return "order/cash-result";
    }
}
