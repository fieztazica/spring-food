package owlvernyte.springfood.entity;


import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table (name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 50, nullable = false, unique = true)
    private String username;

    @Column(name = "password", length = 250, nullable = false)
    private String password;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "phone", length = 50)
    private String phone;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Meal> meals = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @Column(name = "provider", length = 50)
    private String provider;
}
