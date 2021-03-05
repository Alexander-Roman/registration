package by.alexander.registration.service;

import by.alexander.registration.model.dto.RegistrationDto;

public interface RegistrationService {

    String register(RegistrationDto registrationDto);

    String verifyEmail(String emailVerificationToken);
}
