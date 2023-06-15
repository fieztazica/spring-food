package owlvernyte.springfood.service;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import owlvernyte.springfood.entity.Order;
import owlvernyte.springfood.entity.User;
import owlvernyte.springfood.repository.IOrderRepository;
import owlvernyte.springfood.repository.IUserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private IUserRepository userRepository;

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public List<Order> findAllByUser(User user) {
        return orderRepository.findAllByUser(user);
    }

    public Order findOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.orElse(null);
    }

    public void addOrder(Order addOrder) {
        orderRepository.save(addOrder);
    }

    public void updateOrder(Order addOrder) {
        orderRepository.save(addOrder);
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    public void delete(Order order){
        orderRepository.delete(order);
    }
}
