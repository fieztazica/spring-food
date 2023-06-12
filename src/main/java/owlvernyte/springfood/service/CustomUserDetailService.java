package owlvernyte.springfood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import owlvernyte.springfood.constants.Role;
import owlvernyte.springfood.entity.CustomUserDetail;
import owlvernyte.springfood.entity.User;
import owlvernyte.springfood.repository.IRoleRepository;
import owlvernyte.springfood.repository.IUserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("User not found");
        return new CustomUserDetail(user, userRepository);
    }

}
