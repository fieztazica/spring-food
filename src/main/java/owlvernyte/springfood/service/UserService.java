package owlvernyte.springfood.service;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import owlvernyte.springfood.constants.Provider;
import owlvernyte.springfood.constants.Role;
import owlvernyte.springfood.entity.User;
import owlvernyte.springfood.repository.IRoleRepository;
import owlvernyte.springfood.repository.IUserRepository;

@Service
public class UserService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    public void save(User user) {
        user.setProvider(Provider.LOCAL.value);
        user.getRoles().add(roleRepository.findRoleById(Role.USER.value));
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    public void saveOauthUser(String email, @NotNull String username) {
        if (userRepository.findByUsername(username) != null) return;
        var user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(new BCryptPasswordEncoder().encode(username));
        user.setProvider(Provider.GOOGLE.value);
        user.getRoles().add(roleRepository.findRoleById(Role.USER.value));
        userRepository.save(user);
    }

    public void setDefaultRole(String username){
        userRepository.findByUsername(username).getRoles()
                .add(roleRepository
                        .findRoleById(Role.USER.value));
    }
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
