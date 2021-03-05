package by.alexander.registration.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender,
                            TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    @Async
    public void sendEmailVerification(String to, String username, String token) {
        Context context = new Context();//TODO: Locale
        context.setVariable("username", username);
        context.setVariable("token", token);

        // Create the HTML body using Thymeleaf
        String htmlContent = templateEngine.process("email-verification.html", context);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");

        try {
            mimeMessageHelper.setFrom("sender@example.com");
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("Email verification!");
            mimeMessageHelper.setText("This is the message body");
            mimeMessageHelper.setText(htmlContent, true); // true = isHtml
        } catch (MessagingException e) {
            LOGGER.error("Failed to send email!", e);
            throw new IllegalStateException("Failed to send email!", e);
        }

        javaMailSender.send(mimeMessage);
    }
}
