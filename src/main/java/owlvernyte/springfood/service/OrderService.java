package owlvernyte.springfood.service;

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
}
