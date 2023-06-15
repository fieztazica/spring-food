package owlvernyte.springfood;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import owlvernyte.springfood.entity.Role;
import owlvernyte.springfood.repository.IRoleRepository;

@SpringBootApplication
public class SpringFoodApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringFoodApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(IRoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findAll().isEmpty()) {
                Role adminRole = new Role();
                adminRole.setName("ADMIN");
                Role userRole = new Role();
                userRole.setName("USER");
                roleRepository.save(adminRole);
                roleRepository.save(userRole);
            }
        };
    }

}
