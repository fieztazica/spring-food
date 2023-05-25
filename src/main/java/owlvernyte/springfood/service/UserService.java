package owlvernyte.springfood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import owlvernyte.springfood.entity.User;
import owlvernyte.springfood.repository.IUserRepository;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;


    public void save(User user) {
        userRepository.save(user);
        Long userId = userRepository.getUserIdByUsername(user.getUsername());

        }

    }
