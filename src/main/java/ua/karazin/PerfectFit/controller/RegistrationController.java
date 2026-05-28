package ua.karazin.PerfectFit.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.karazin.PerfectFit.configuration.UrlAuthenticationSuccessHandler;
import ua.karazin.PerfectFit.entity.Client;
import ua.karazin.PerfectFit.repository.ClientRepository;

@Controller


@RequestMapping("/registration")
public class RegistrationController {
    private final ClientRepository clientRepository;
    private final UrlAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    RegistrationController(ClientRepository clientRepository,UrlAuthenticationSuccessHandler authenticationSuccessHandler) {
        this.clientRepository = clientRepository;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @GetMapping
    public String showRegistrationPage(Model model) {
        return "registration-page";
    }

    @PostMapping
    public String processRegistrationForm(@Valid Client client, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                model.addAttribute(error.getField() + "Error", error.getDefaultMessage());
            }
            return "registration-page";
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        String hashedPassword = passwordEncoder.encode(client.getPassword());
        client.setPassword(hashedPassword);

        clientRepository.save(client);

        return "redirect:/login";
    }

    //TODO: ОБРАБОТАТЬ /registration?error

}
