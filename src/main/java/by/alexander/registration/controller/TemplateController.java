package by.alexander.registration.controller;

import by.alexander.registration.model.dto.RegistrationDto;
import by.alexander.registration.service.RegistrationService;
import by.alexander.registration.validator.RegistrationDtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TemplateController {

    private final RegistrationDtoValidator registrationDtoValidator;
    private final RegistrationService registrationService;

    @Autowired
    public TemplateController(RegistrationDtoValidator registrationDtoValidator, RegistrationService registrationService) {
        this.registrationDtoValidator = registrationDtoValidator;
        this.registrationService = registrationService;
    }

    @GetMapping("home")
    public String getHomeView() {
        return "home";
    }

    @GetMapping("login")
    public String getLoginView() {
        return "login";
    }

    @GetMapping("register")
    public String getRegisterView(Model model) {
        RegistrationDto registrationDto = new RegistrationDto(null, null, null, null, null);
        model.addAttribute("registrationDto", registrationDto);
        return "register";
    }

    @PostMapping("register")
    public String submitRegisterForm(@ModelAttribute RegistrationDto registrationDto, BindingResult bindingResult, Model model) {
        registrationDtoValidator.validate(registrationDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "register";
        }
        registrationService.register(registrationDto);
        return "redirect:home";
    }
}
