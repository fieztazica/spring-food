package owlvernyte.springfood.util;


import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import owlvernyte.springfood.service.CustomUserDetailService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService());
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(@NotNull
                                                   HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/",
                                "/register", "/error")
                        .permitAll()
                        .requestMatchers("/meals/edit",
                                "/meals/delete", "/meals/add")
                        .hasAnyAuthority("ADMIN")
                        .requestMatchers("/meals")
                        .hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers("/orders/**")
                        .hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers("/api/**")
                        .hasAnyAuthority("USER", "ADMIN")
                        .anyRequest()
                        .authenticated()
                )
                .logout(logout ->
                        logout.logoutUrl("/logout")
                                .logoutSuccessUrl("/login")
                                .deleteCookies("JSESSIONID")
                                .invalidateHttpSession(true)
                                .clearAuthentication(true)
                                .permitAll()
                )
                .formLogin(formLogin ->
                        formLogin.loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/")
                                .failureUrl("/login?error=true")
                                .permitAll()
                )
                .rememberMe(rememberMe ->
                        rememberMe.key("dat").rememberMeCookieName("dat")
                                .tokenValiditySeconds(24 * 60 * 60)
                                .userDetailsService(userDetailsService())
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.accessDeniedPage("/403"))
                .sessionManagement(sessionManagement ->
                        sessionManagement.maximumSessions(1)
                                .expiredUrl("/login")
                )
                .httpBasic(httpBasic -> httpBasic.realmName("dat"))
                .build();
    }

}
