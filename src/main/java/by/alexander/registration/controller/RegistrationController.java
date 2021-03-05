package by.alexander.registration.controller;

import by.alexander.registration.model.dto.RegistrationDto;
import by.alexander.registration.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public String register(@RequestBody RegistrationDto registrationDto) {
        return registrationService.register(registrationDto);
    }

    @GetMapping("verify")
    public String verifyEmail(@RequestParam("token") String token) {
        return registrationService.verifyEmail(token);
    }
}
