package by.alexander.registration.email;

public interface EmailService {

    void sendEmailVerification(String to, String username, String token);
}
