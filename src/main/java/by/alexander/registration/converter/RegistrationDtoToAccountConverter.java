package by.alexander.registration.converter;

import by.alexander.registration.model.dto.RegistrationDto;
import by.alexander.registration.model.entity.Account;
import by.alexander.registration.model.entity.Role;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class RegistrationDtoToAccountConverter implements Converter<RegistrationDto, Account> {

    private static final Role DEFAULT_ROLE = Role.USER;
    private static final boolean DEFAULT_BLOCKED = false;
    private static final boolean DEFAULT_ENABLED = false;

    @Override
    public Account convert(RegistrationDto registrationDto) {
        String email = registrationDto.getEmail();
        String username = registrationDto.getUsername();
        String name = registrationDto.getName();
        String password = registrationDto.getPassword();

        return new Account.Builder()
                .setEmail(email)
                .setUsername(username)
                .setName(name)
                .setPassword(password)
                .setRole(DEFAULT_ROLE)
                .setBlocked(DEFAULT_BLOCKED)
                .setEnabled(DEFAULT_ENABLED)
                .build();
    }

}
