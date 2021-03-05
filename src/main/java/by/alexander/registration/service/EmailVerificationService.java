package by.alexander.registration.service;

import by.alexander.registration.model.entity.Account;
import by.alexander.registration.model.entity.EmailVerification;

public interface EmailVerificationService {

    EmailVerification saveEmailVerification(EmailVerification emailVerification);

    EmailVerification findByToken(String token);

    EmailVerification createEmailVerification(Account account);
}
