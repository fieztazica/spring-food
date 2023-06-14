package owlvernyte.springfood.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import owlvernyte.springfood.entity.Meal;
import org.springframework.data.domain.Pageable;

@Repository
public interface IMealRepository extends JpaRepository<Meal, Long> {
//    Page<Meal> findByAuthorContainingIgnoreCaseOrTitleContainingIgnoreCase(String keyword, String keyword1, Pageable pageable);
}
