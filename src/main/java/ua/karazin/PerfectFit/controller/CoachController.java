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

import ua.karazin.PerfectFit.entity.AbsenceRecordCoach;
import ua.karazin.PerfectFit.entity.Coach;
import ua.karazin.PerfectFit.service.AbsenceRecordCoachService;
import ua.karazin.PerfectFit.service.CoachService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.TreeMap;

@RequestMapping("/coach")
@Controller
public class CoachController {
   private final CoachService coachService;
   private final AbsenceRecordCoachService absenceRecordCoachService;

    @Autowired
    public CoachController(CoachService coachService, AbsenceRecordCoachService absenceRecordCoachService) {
        this.coachService = coachService;
        this.absenceRecordCoachService = absenceRecordCoachService;
    }




    @GetMapping
    public String showMyPage(Model model, @RequestParam(defaultValue = "0") Integer dateIndex) {
        TreeMap<LocalDate, TreeMap<String, String>> schedule = new TreeMap<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                String username = userDetails.getUsername();
                Coach coach = coachService.findCoachByMail(username);
                model.addAttribute("coach", coach);
                schedule = coachService.getSchedule(dateIndex, coach);

            }
        }

        model.addAttribute("schedule", schedule);


        return "my-page-coach";
    }

    @PostMapping("/update")
    public String updateCoach(@ModelAttribute("coach") Coach updatedCoach) throws UnsupportedEncodingException {

        if(updatedCoach.getFio() == null || updatedCoach.getMail() == null || updatedCoach.getPhoneNumber() == null) {
            return "redirect:/coach?dateIndex=0?result="+ URLEncoder.encode("Void form were presented.", "UTF-8");
        }
        if(updatedCoach.equals(coachService.getCoachById(Long.valueOf(updatedCoach.getId())))) {
            return "redirect:/coach?dateIndex=0&result="+ URLEncoder.encode("Nothing was changed.", "UTF-8");
        }
        coachService.updateCoach(updatedCoach);

        return "redirect:/coach?dateIndex=0&result="+ URLEncoder.encode("Successful operation!", "UTF-8");
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
                Coach coach = coachService.findCoachByMail(username);
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

                if (!passwordEncoder.matches(currentPassword, coach.getPassword())) {
                    model.addAttribute("errorWithOldPassword", "Current password is incorrect.");
                    return "my-change-password";
                }

                coach.setPassword(passwordEncoder.encode(newPassword));
                coachService.updateCoach(coach);

            }
        }
        return "my-change-password";
    }

    @PostMapping("/save-absence-record")
    public String processAbsenceForm(@Valid AbsenceRecordCoach form, Model model) {

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
                Coach coach = coachService.findCoachByMail(username);
                form.setCoach(coach);
                System.out.println(form.getReason());

                absenceRecordCoachService.save(form);

            }
        }
        return "redirect:/coach?dateIndex=0";
    }



}
