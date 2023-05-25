package owlvernyte.springfood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import owlvernyte.springfood.entity.Meal;

@Repository
public interface IMealRepository extends JpaRepository<Meal, Long> {
}
