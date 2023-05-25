package owlvernyte.springfood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import owlvernyte.springfood.entity.Order;

@Repository
public interface IOrderRepository extends JpaRepository<Order,Long> {
}
