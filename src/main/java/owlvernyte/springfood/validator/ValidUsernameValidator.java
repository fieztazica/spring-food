package owlvernyte.springfood.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import owlvernyte.springfood.repository.IUserRepository;
import owlvernyte.springfood.validator.annotation.ValidUsername;

public class ValidUsernameValidator implements ConstraintValidator<ValidUsername, String> {
    @Autowired
    private IUserRepository userRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (userRepository == null) return true;
        return userRepository.findByUsername(username) == null;
    }
}
