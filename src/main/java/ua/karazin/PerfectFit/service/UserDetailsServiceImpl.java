package ua.karazin.PerfectFit.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.karazin.PerfectFit.entity.Admin;
import ua.karazin.PerfectFit.entity.Client;
import ua.karazin.PerfectFit.entity.Coach;
import ua.karazin.PerfectFit.repository.AdminRepository;
import ua.karazin.PerfectFit.repository.ClientRepository;
import ua.karazin.PerfectFit.repository.CoachRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final ClientRepository clientRepository;
    private final CoachRepository coachRepository;


    @Autowired
    UserDetailsServiceImpl(ClientRepository clientRepository, CoachRepository coachRepository, AdminRepository adminRepository) {
        this.coachRepository = coachRepository;
        this.clientRepository = clientRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Проверяем таблицу Admin на наличие пользователя с заданным именем
        Coach coach = coachRepository.findCoachByMail(username);
        if (coach != null) {
            return User.builder()
                    .username(coach.getMail())
                    .password(coach.getPassword())
                    .roles("COACH")
                    .build();
        }
        // Проверяем таблицу Client на наличие пользователя с заданным именем
        Client client = clientRepository.findClientByMail(username);
        if (client != null) {
            return User.builder()
                    .username(client.getMail())
                    .password(client.getPassword())
                    .roles("CLIENT")
                    .build();
        }
        // Проверяем таблицу Admin на наличие пользователя с заданным именем
        Admin admin = adminRepository.findAdminByFio(username);
        if (admin != null) {
            return User.builder()
                    .username(admin.getFio())
                    .password(admin.getPassword())
                    .roles("ADMIN")
                    .build();
        }
        throw new UsernameNotFoundException("User " + username + " not found");
    }


}
