package owlvernyte.springfood.entity;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import owlvernyte.springfood.constants.Role;
import owlvernyte.springfood.repository.IRoleRepository;
import owlvernyte.springfood.repository.IUserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {
    private final OAuth2User oAuth2User;

    private final IUserRepository userRepository;

    private final IRoleRepository roleRepository;

    public CustomOAuth2User(OAuth2User oAuth2User,IUserRepository userRepository,IRoleRepository roleRepository) {
        this.oAuth2User = oAuth2User;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        oAuth2User.getAuthorities().forEach(ga -> authorities.add(ga));
        authorities.add(new SimpleGrantedAuthority(roleRepository.findRoleById(Role.USER.value).getName()));
        return authorities;
    }

    @Override
    public String getName() {
        return oAuth2User.getAttribute("name");
    }

    public String getEmail() {
        return oAuth2User.<String>getAttribute("email");
    }
}
