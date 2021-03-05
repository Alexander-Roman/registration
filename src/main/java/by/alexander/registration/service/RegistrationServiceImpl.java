package by.alexander.registration.service;

import by.alexander.registration.email.EmailService;
import by.alexander.registration.model.dto.RegistrationDto;
import by.alexander.registration.model.entity.Account;
import by.alexander.registration.model.entity.EmailVerification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final Converter<RegistrationDto, Account> registrationDtoToAccountConverter;
    private final AccountService accountService;
    private final EmailVerificationService emailVerificationService;
    private final EmailService emailService;

    @Autowired
    public RegistrationServiceImpl(Converter<RegistrationDto, Account> registrationDtoToAccountConverter, AccountService accountService, EmailVerificationService emailVerificationService, EmailService emailService) {
        this.registrationDtoToAccountConverter = registrationDtoToAccountConverter;
        this.accountService = accountService;
        this.emailVerificationService = emailVerificationService;
        this.emailService = emailService;
    }

    @Override
    public String register(RegistrationDto registrationDto) {
        Account account = registrationDtoToAccountConverter.convert(registrationDto);
        Account saved = accountService.singUpAccount(account);
        EmailVerification emailVerification = emailVerificationService.createEmailVerification(saved);
        String email = registrationDto.getEmail();
        String username = registrationDto.getUsername();
        String token = emailVerification.getToken();
        emailService.sendEmailVerification(email, username, token);
        return token;
    }

    @Override
    @Transactional
    public String verifyEmail(String emailVerificationToken) {
        EmailVerification emailVerification = emailVerificationService.findByToken(emailVerificationToken);
        LocalDateTime confirmed = emailVerification.getConfirmed();
        if (confirmed != null) {
            throw new IllegalStateException("Email verified already!");
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expires = emailVerification.getExpires();
        if (expires.isBefore(now)) {
            throw new IllegalStateException("Verification time expired!");
        }
        EmailVerification confirmedEmailVerification = EmailVerification.Builder.from(emailVerification)
                .setConfirmed(now)
                .build();
        emailVerificationService.saveEmailVerification(confirmedEmailVerification);
        Account account = confirmedEmailVerification.getAccount();
        accountService.enableAccount(account);

        return "CONFIRMED";
    }
}
