package by.alexander.registration.validator;

import by.alexander.registration.model.dto.RegistrationDto;
import by.alexander.registration.model.entity.Account;
import by.alexander.registration.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RegistrationDtoValidator implements Validator {

    private static final int USERNAME_MIN_LENGTH = 8;
    private static final int USERNAME_MAX_LENGTH = 32;
    private static final int PASSWORD_MIN_LENGTH = 8;
    private static final int PASSWORD_MAX_LENGTH = 32;
    private static final String EMAIL_PATTERN = "[^@]+@[^@.]+\\.[^@.]+";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_PATTERN);

    private final AccountService accountService;

    @Autowired
    public RegistrationDtoValidator(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public boolean supports(@Nullable Class<?> aClass) {
        return RegistrationDto.class.equals(aClass);
    }

    @Override
    public void validate(@NonNull Object o, @NonNull Errors errors) {
        RegistrationDto registrationDto = (RegistrationDto) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "dto.registration.username.required");
        String username = registrationDto.getUsername();
        int usernameLength = username.length();
        if (usernameLength < USERNAME_MIN_LENGTH || USERNAME_MAX_LENGTH < usernameLength) {
            errors.rejectValue("username", "dto.registration.username.length");
        }
        String email = registrationDto.getEmail();

        Matcher matcher = PATTERN.matcher(email);
        if (!matcher.matches()) {
            errors.rejectValue("email", "dto.registration.email");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "dto.registration.password.required");
        String password = registrationDto.getPassword();
        int passwordLength = password.length();
        if (passwordLength < PASSWORD_MIN_LENGTH || PASSWORD_MAX_LENGTH < passwordLength) {
            errors.rejectValue("password", "dto.registration.password.length");
        }
        String passwordConfirm = registrationDto.getPasswordConfirm();
        if (!password.equals(passwordConfirm)) {
            errors.rejectValue("passwordConfirm", "dto.registration.password.confirm.equals");
        }
        Optional<Account> foundByUsername = accountService.findByUsername(username);
        if (foundByUsername.isPresent()) {
            errors.rejectValue("username", "dto.registration.username.exists");
        }
        Optional<Account> foundByEmail = accountService.findByEmail(email);
        if (foundByEmail.isPresent()) {
            errors.rejectValue("email", "dto.registration.email.exists");
        }
    }
}
