package by.alexander.registration.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

@Configuration
@PropertySource("classpath:property/email/from.properties")
public class SpringMailConfig {

    public static final String EMAIL_TEMPLATE_ENCODING = "UTF-8";
    private static final String JAVA_MAIL_PROPS = "property/email/javamail.properties";
    private final String host;
    private final int port;
    private final String protocol;
    private final String username;
    private final String password;

    public SpringMailConfig(@Value("${mail.server.host}") String host,
                            @Value("${mail.server.port}") int port,
                            @Value("${mail.server.protocol}") String protocol,
                            @Value("${mail.server.username}") String username,
                            @Value("${mail.server.password}") String password) {
        this.host = host;
        this.port = port;
        this.protocol = protocol;
        this.username = username;
        this.password = password;
    }

    /*
     * SPRING + JAVAMAIL: JavaMailSender instance, configured via .properties files.
     */
    @Bean
    public JavaMailSender mailSender() throws IOException {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // Basic mail sender configuration, based on from.properties
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setProtocol(protocol);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        // JavaMail-specific mail sender configuration, based on javamail.properties
        Resource resource = new ClassPathResource(JAVA_MAIL_PROPS);
        Properties javaMailProperties = PropertiesLoaderUtils.loadProperties(resource);
        mailSender.setJavaMailProperties(javaMailProperties);

        return mailSender;
    }

    @Bean
    public EmailService emailService() throws IOException {
        JavaMailSender javaMailSender = mailSender();
        TemplateEngine templateEngine = emailTemplateEngine();
        return new EmailServiceImpl(javaMailSender, templateEngine);
    }

    /*
     *  Message externalization/internationalization for emails.
     *
     *  NOTE we are avoiding the use of the name 'messageSource' for this bean because that
     *       would make the MessageSource defined in SpringWebConfig (and made available for the
     *       web-side template engine) delegate to this one, and thus effectively merge email
     *       messages into web messages and make both types available at the web side, which could
     *       bring undesired collisions.
     *
     *  NOTE also that given we want this specific message source to be used by our
     *       SpringTemplateEngines for emails (and not by the web one), we will set it explicitly
     *       into each of the TemplateEngine objects with 'setTemplateEngineMessageSource(...)'
     */
    @Bean
    public ResourceBundleMessageSource emailMessageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("property/email/localization");
        return messageSource;
    }


    /* ******************************************************************** */
    /*  THYMELEAF-SPECIFIC ARTIFACTS FOR EMAIL                              */
    /*  TemplateResolver(3) <- TemplateEngine                               */
    /* ******************************************************************** */

    @Bean
    public TemplateEngine emailTemplateEngine() {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        // Resolver for TEXT emails
        templateEngine.addTemplateResolver(textTemplateResolver());
        // Resolver for HTML emails (except the editable one)
        templateEngine.addTemplateResolver(htmlTemplateResolver());
        // Resolver for HTML editable emails (which will be treated as a String)
        templateEngine.addTemplateResolver(stringTemplateResolver());
        // Message source, internationalization specific to emails
        templateEngine.setTemplateEngineMessageSource(emailMessageSource());
        return templateEngine;
    }

    private ITemplateResolver textTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setOrder(1);
        templateResolver.setResolvablePatterns(Collections.singleton("text/*"));
        templateResolver.setPrefix("/email/");
        templateResolver.setSuffix(".txt");
        templateResolver.setTemplateMode(TemplateMode.TEXT);
        templateResolver.setCharacterEncoding(EMAIL_TEMPLATE_ENCODING);
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    private ITemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setOrder(2);
        templateResolver.setPrefix("/email/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(EMAIL_TEMPLATE_ENCODING);
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    private ITemplateResolver stringTemplateResolver() {
        final StringTemplateResolver templateResolver = new StringTemplateResolver();
        templateResolver.setOrder(3);
        // No resolvable pattern, will simply process as a String template everything not previously matched
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCacheable(false);
        return templateResolver;
    }
}
