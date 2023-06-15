package owlvernyte.springfood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import owlvernyte.springfood.entity.Order;
import owlvernyte.springfood.entity.User;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAllByUser(User user);
}
