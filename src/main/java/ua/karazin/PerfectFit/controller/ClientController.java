package ua.karazin.PerfectFit.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.karazin.PerfectFit.entity.AbsenceRecordClient;
import ua.karazin.PerfectFit.entity.Client;
import ua.karazin.PerfectFit.entity.SaleSubscription;
import ua.karazin.PerfectFit.repository.ClientRepository;
import ua.karazin.PerfectFit.service.AbsenceRecordClientService;
import ua.karazin.PerfectFit.service.ClientService;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;

import java.util.*;

@RequestMapping("/client")
@Controller
public class ClientController {

    private final ClientRepository clientRepository;
    private final ClientService clientService;
    private final AbsenceRecordClientService absenceRecordClientService;

    @Autowired
    public ClientController(ClientRepository clientRepository, ClientService clientService, AbsenceRecordClientService absenceRecordClientService) {
        this.clientRepository = clientRepository;
        this.clientService = clientService;
        this.absenceRecordClientService = absenceRecordClientService;
    }

    @GetMapping
    public String showMyPage(Model model, @RequestParam(defaultValue = "0") Integer dateIndex) {
        TreeMap<LocalDate, TreeMap<String, String>> schedule = new TreeMap<>();

        // Получить объект аутентификации из контекста безопасности
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Проверить, аутентифицирован ли пользователь
        if (authentication.isAuthenticated()) {
            // Получить информацию о пользователе
            Object principal = authentication.getPrincipal();

            // Проверить, что информация о пользователе является объектом UserDetails
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                String username = userDetails.getUsername();
                Client client = clientRepository.findClientByMail(username);
                model.addAttribute("client", client);
                schedule = clientService.getSchedule(dateIndex, client);
            }
        }

        model.addAttribute("schedule", schedule);
        return "my-page-client";
    }


    @PostMapping("/save-absence-record")
    public String processAbsenceForm(@Valid AbsenceRecordClient form, Model model) {

        // Получить объект аутентификации из контекста безопасности
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Проверить, аутентифицирован ли пользователь
        if (authentication.isAuthenticated()) {
            // Получить информацию о пользователе
            Object principal = authentication.getPrincipal();

            // Проверить, что информация о пользователе является объектом UserDetails
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                String username = userDetails.getUsername();
                Client client = clientRepository.findClientByMail(username);
                form.setClient(client);

                absenceRecordClientService.save(form);

            }
        }
        return "redirect:/client?dateIndex=0";
    }

    @PostMapping("/update")
    public String updateClient(@ModelAttribute("client") Client updatedClient) throws UnsupportedEncodingException {
        if(updatedClient.getFio() == null || updatedClient.getMail() == null || updatedClient.getPhoneNumber() == null) {
            return "redirect:/client?dateIndex=0&result=" + URLEncoder.encode("Void form were presented.");
        }
        if(updatedClient.equals(clientService.updateClient(updatedClient.getId(),updatedClient))) {
            return "redirect:/client?dateIndex=0&result=" + URLEncoder.encode("Nothing was changed.");
        }
        clientService.updateClient(updatedClient.getId(),updatedClient);


        return "redirect:/client?dateIndex=0&result=" + URLEncoder.encode("Successful operation!");
    }

    @GetMapping("/change-password")
    public String changePassword() {
        return "my-change-password";
    }
    @PostMapping("/change-password")
    public String changePassword(Model model,
                                 @RequestParam("current-password") String currentPassword,
                                 @RequestParam("new-password") String newPassword,
                                 @RequestParam("confirm-password") String confirmPassword) {
        if(currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            model.addAttribute("errorVoid", "All fields must be filled out.");
            return "my-change-password";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("errorWithNewPassword", "New password and confirm password do not match.");
            return "my-change-password";
        }


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        if (authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                String username = userDetails.getUsername();
                Client client = clientRepository.findClientByMail(username);
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

                if (!passwordEncoder.matches(currentPassword, client.getPassword())) {
                    model.addAttribute("errorWithOldPassword", "Current password is incorrect.");
                    return "my-change-password";
                }

                client.setPassword(passwordEncoder.encode(newPassword));
                clientService.updateClient(client.getId(), client);

            }
        }
            return "my-change-password";
    }


    @GetMapping("/my-courses")
    public String showMyCourses(Model model, @RequestParam(defaultValue = "0") Integer index) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int countOfCols = 3;

        if (authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                String username = userDetails.getUsername();
                Client client = clientRepository.findClientByMail(username);
                List<List<String>> courses = clientService.getCourses(client);
                List<List<String>> indexCourses = new ArrayList<>();
                Integer maxPages = (courses.size() / countOfCols == 0) ? courses.size() / countOfCols : courses.size() / countOfCols + 1;

                int startIndex = countOfCols * index;
                int endIndex = Math.min(countOfCols * (index + 1), courses.size());

                for (int i = startIndex; i < endIndex; i++) {
                    if (!courses.get(i).isEmpty()) {
                        indexCourses.add(courses.get(i));
                    } else {
                        indexCourses.add(new java.util.ArrayList<>(Collections.nCopies(5, null)));
                    }
                }

                model.addAttribute("client", client);
                model.addAttribute("maxPages", maxPages);
                model.addAttribute("courses", indexCourses);
            }
        }
        return "my-courses";
    }


    @PostMapping("/delete-course")
    public String deleteCourse(@RequestParam("id") String id) {

        clientService.removeCourse(Long.valueOf(id));
    return "redirect:/client/my-courses?index=0";

    }


    @GetMapping("/my-subscription")
    public String showMySubscription(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                String username = userDetails.getUsername();
                Client client = clientRepository.findClientByMail(username);
                SaleSubscription saleSubscription = clientService.getSubscription(client);
                model.addAttribute("client", client);
                model.addAttribute("subscription", saleSubscription);
                if (saleSubscription != null) {
                    model.addAttribute("number", saleSubscription.getSubscription().getMaxCoursesCapacity() - clientService.getCountOfCourses(client));
                }
            }
        }
        return "my-subscription";
    }

    @PostMapping("/delete-subscription")
    public String deleteSubscription() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                String username = userDetails.getUsername();
                Client client = clientRepository.findClientByMail(username);
                clientService.removeSubscription(client);
            }
        }
        return "redirect:/client/my-subscription";
    }


    @PostMapping("/update-subscription")
    public String updateSubscription(Model model, @RequestParam(defaultValue = "0")Long numberOfDays) throws UnsupportedEncodingException {

        if(numberOfDays <= 0) {
            String encodedSaleResult = URLEncoder.encode("It seems you send impossible number of months.", "UTF-8");
            return "redirect:/client/my-subscription?result="+encodedSaleResult;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                String username = userDetails.getUsername();
                Client client = clientRepository.findClientByMail(username);
                clientService.updateSubscription(client, numberOfDays);
            }
        }

        return "redirect:/client/my-subscription";
    }

}