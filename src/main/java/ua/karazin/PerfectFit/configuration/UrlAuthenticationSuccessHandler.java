package ua.karazin.PerfectFit.configuration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ua.karazin.PerfectFit.entity.AbsenceRecordClient;
import ua.karazin.PerfectFit.entity.AbsenceRecordCoach;
import ua.karazin.PerfectFit.entity.Client;
import ua.karazin.PerfectFit.entity.Course;
import ua.karazin.PerfectFit.repository.ClientRepository;
import ua.karazin.PerfectFit.repository.CourseRepository;


import java.io.IOException;
import java.util.*;

@Component
public class UrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    CourseRepository courseRepository;
    ClientRepository clientRepository;

    @Autowired
    public UrlAuthenticationSuccessHandler(CourseRepository courseRepository, ClientRepository clientRepository) {
        this.courseRepository = courseRepository;
        this.clientRepository = clientRepository;
    }

    protected String determineTargetUrl(Authentication authentication) {
        Map<String, String> roleTargetUrlMap = new HashMap<>();
        roleTargetUrlMap.put("ROLE_ADMIN", "/admin");
        roleTargetUrlMap.put("ROLE_COACH", "/coach?dateIndex=0");
        roleTargetUrlMap.put("ROLE_CLIENT", "/client?dateIndex=0");

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            String authorityName = authority.getAuthority().toUpperCase();
            if (roleTargetUrlMap.containsKey(authorityName)) {
                return roleTargetUrlMap.get(authorityName);
            }
        }

        throw new IllegalStateException("No target URL found for the user's role");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(authentication);
        Client client = clientRepository.findClientByMail(authentication.getName());
        Map<Course, AbsenceRecordCoach> courseAbsenceRecordCoachMap = new HashMap<>();
        List<String> message = new ArrayList<>();

        List<Object[]> declinedCourses = courseRepository.getDeclinedCourses(client);

        for (Object[] row : declinedCourses) {
            courseAbsenceRecordCoachMap.put((Course) row[0],(AbsenceRecordCoach)row[1]);
        }

        for (Map.Entry<Course, AbsenceRecordCoach> entry : courseAbsenceRecordCoachMap.entrySet()) {
            String row = "Course " + entry.getKey().getName() + " is declined from " + entry.getValue().getAbsenceDateBegin() + " to " + entry.getValue().getAbsenceDateEnd();
            message.add(row);
        }

        request.getSession().setAttribute("message", message);


        response.sendRedirect(targetUrl);

    }


}
