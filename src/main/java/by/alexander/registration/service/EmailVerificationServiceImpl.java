package by.alexander.registration.service;

import by.alexander.registration.model.entity.Account;
import by.alexander.registration.model.entity.EmailVerification;
import by.alexander.registration.repository.EmailVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private static final int EMAIL_VERIFICATION_MINUTES_EXPIRATION_TIME = 15;

    private final EmailVerificationRepository emailVerificationRepository;

    @Autowired
    public EmailVerificationServiceImpl(EmailVerificationRepository emailVerificationRepository) {
        this.emailVerificationRepository = emailVerificationRepository;
    }

    @Override
    public EmailVerification saveEmailVerification(EmailVerification emailVerification) {
        return emailVerificationRepository.save(emailVerification);
    }

    @Override
    public EmailVerification findByToken(String token) {
        Optional<EmailVerification> found = emailVerificationRepository.findByToken(token);
        if (found.isEmpty()) {
            String errorMessage = String.format("EmailVerification entity with token '%s' is not found", token);
            throw new EntityNotFoundException(errorMessage);
        }
        return found.get();
    }

    @Override
    public EmailVerification createEmailVerification(Account account) {
        UUID uuid = UUID.randomUUID();
        String token = uuid.toString();
        LocalDateTime issued = LocalDateTime.now();
        LocalDateTime expires = issued.plusMinutes(EMAIL_VERIFICATION_MINUTES_EXPIRATION_TIME);

        EmailVerification emailVerification = new EmailVerification.Builder()
                .setToken(token)
                .setIssued(issued)
                .setExpires(expires)
                .setConfirmed(null)
                .setAccount(account)
                .build();

        return emailVerificationRepository.save(emailVerification);
    }
}
