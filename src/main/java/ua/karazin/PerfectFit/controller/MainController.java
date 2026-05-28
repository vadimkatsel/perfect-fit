package ua.karazin.PerfectFit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.karazin.PerfectFit.entity.*;
import ua.karazin.PerfectFit.repository.ClientRepository;
import ua.karazin.PerfectFit.repository.SubscriptionRepository;
import ua.karazin.PerfectFit.service.ClientService;
import ua.karazin.PerfectFit.service.CourseService;
import ua.karazin.PerfectFit.service.SubscriptionService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;


@Controller
public class MainController {

    private SubscriptionService subscriptionService;
    private CourseService courseService;
    private ClientService clientService;

    @Autowired
    public MainController(SubscriptionService subscriptionService, CourseService courseService, ClientService clientService) {
        this.subscriptionService = subscriptionService;
        this.courseService = courseService;
        this.clientService = clientService;

    }

    @GetMapping("/")
    public String greetings(Model model) {
        model.addAttribute("title", "PerfectFit");
        return "home-page";
    }

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout,
                                Model model) {

        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "login-page";
    }

    @GetMapping("/subscriptions")
    public String showSubscriptionPage(Model model) {
        List<Subscription> subscriptions = subscriptionService.getAllSubscriptions();
        model.addAttribute("subscriptions", subscriptions);

        return "home-subscriptions-page";
    }

    @PostMapping("/buy-subscription")
    public String buySubscription(Model model, @RequestParam("id") Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String result = "You must be authenticated!";
        if (authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                String username = userDetails.getUsername();
                Client client = clientService.findClientByMail(username);
                result = clientService.saleSubscriptionToClient(client, id);
            }
        }

        model.addAttribute("saleResult", result);

        return "redirect:/subscriptions?saleResult=" + result;

    }


    @GetMapping("/courses")
    public String showCoursesPage(Model model, @RequestParam(defaultValue = "0") Integer index) {
        int countOfRows = 6;
        List<Course> courses = courseService.getAllAvailableCourses();

        Map<Course, List<Schedule>> courseListMap = new HashMap<>();


        int startIndex = countOfRows * index;
        int endIndex = Math.min(countOfRows * (index + 1), courses.size());


        Integer maxPages = (courses.size() % countOfRows == 0) ? courses.size() / countOfRows : courses.size() / countOfRows + 1;

        for (int i = startIndex; i < endIndex; i++) {
            courseListMap.put(courses.get(i), courseService.getAllDaysOfWeekForCourse(courses.get(i)));
        }

        model.addAttribute("maxPages", maxPages);
        model.addAttribute("courses", courseListMap);

        return "home-courses-page";
    }

    @PostMapping("/enrol-course")
    public String enrolCourse(Model model, @RequestParam("id") Long id) throws UnsupportedEncodingException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String result = null;
        if (authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                String username = userDetails.getUsername();
                Client client = clientService.findClientByMail(username);
                result = clientService.saleCourseToClient(client, id);
            }
        }
        else {
            model.addAttribute("warning", "You must be authenticated!");
        }

        String encodedSaleResult = URLEncoder.encode(result, "UTF-8");


        return "redirect:/courses?index=0&saleResult=" + encodedSaleResult;
    }


}
