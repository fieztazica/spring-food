package owlvernyte.springfood.service;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import owlvernyte.springfood.entity.Order;
import owlvernyte.springfood.repository.IOrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private IOrderRepository orderRepo;
    private static final String ORDER_SESSION_KEY = "order";
    public List<Order> getAll(){
        return orderRepo.findAll();
    }

    public Order getById(Long id){
        Optional<Order>order = orderRepo.findById(id);
        return order.orElse(null);
    }
    public void addOrder(Order addOrder){
        orderRepo.save(addOrder);
    }
    public void updateOrder(Order addOrder){
        orderRepo.save(addOrder);
    }
    public void Delete(Long id){
        orderRepo.deleteById(id);
    }
    public Order getSessionOrder(@NotNull HttpSession session) {
        return Optional.ofNullable((Order) session.getAttribute(ORDER_SESSION_KEY))
                .orElseGet(() -> {
                    Order order = new Order();
                    session.setAttribute(ORDER_SESSION_KEY, order);
                    return order;
                });
    }
    public void updateSessionOrder(@NotNull HttpSession session, Order order) {
        session.setAttribute(ORDER_SESSION_KEY, order);
    }
    public void removeSessionOrder(@NotNull HttpSession session) {
        session.removeAttribute(ORDER_SESSION_KEY);
    }
}
