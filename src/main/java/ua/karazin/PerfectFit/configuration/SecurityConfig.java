package ua.karazin.PerfectFit.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration

public class SecurityConfig{
    private final UserDetailsService userDetailsService;
    private final UrlAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, UrlAuthenticationSuccessHandler authenticationSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/","/static/**", "/subscriptions", "/courses").permitAll()
                        .requestMatchers("/registration").anonymous()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/coach/**").hasAnyRole("COACH")
                        .requestMatchers("/client/**").hasAnyRole("CLIENT")
                        .anyRequest().authenticated()
                )
                .formLogin().loginPage("/login").permitAll()
                .successHandler(authenticationSuccessHandler)
                .and()
                .logout().permitAll()
                .logoutSuccessUrl("/login")
                .and()
                .csrf().disable();

        return http.build();
    }


    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }


}

