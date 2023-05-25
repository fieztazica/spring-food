package owlvernyte.springfood.repository;



import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import owlvernyte.springfood.entity.User;


@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    User findByUsername(String username);

    @Query ("SELECT u.id FROM User u WHERE u.username = ?1")
    Long getUserIdByUsername(String username);



}
