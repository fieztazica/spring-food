package owlvernyte.springfood.util;


import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.SecurityFilterChain;
import owlvernyte.springfood.service.CustomOAuth2UserService;
import owlvernyte.springfood.service.CustomUserDetailService;
import owlvernyte.springfood.service.OAuthService;
import owlvernyte.springfood.service.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService oAuthService;

    private final UserService userService;

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
        http
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/css/**", "/js/**", "/",
                                        "/register", "/error", "/contact", "/email/**", "/oauth/**", "/api/**")
                                .permitAll()
                                .requestMatchers("/meals/edit", "/meals/edit/**", "/meals/add", "/meals/delete/**")
                                .hasAnyAuthority("ADMIN")
                                .requestMatchers("/meals/**")
                                .permitAll()
//                        .hasAnyAuthority("USER", "ADMIN")
                                .requestMatchers("/orders","/orders/cash-pay")
                                .hasAnyAuthority("USER", "ADMIN")
                                .requestMatchers("/orders/**")
                                .hasAnyAuthority("ADMIN")
//                                .requestMatchers("/api/cart/**", "/api/images/**")
//                                .permitAll()
//                                .requestMatchers("/api/**").permitAll()
//                                .hasAnyAuthority("USER", "ADMIN")
                                .anyRequest()
                                .authenticated()
                )
                .oauth2Login(oauth2Login -> oauth2Login.loginPage("/login")
                        .failureUrl("/login?error")
                        .userInfoEndpoint(userInfoEndpoint ->
                                userInfoEndpoint
                                        .userService(oAuthService)
                        )
                        .successHandler(
                                (request, response,
                                 authentication) -> {
                                    var oidcUser = (DefaultOidcUser) authentication.getPrincipal();
                                    userService.saveOauthUser(oidcUser.getEmail(), oidcUser.getName());
                                    response.sendRedirect("/");
                                }
                        )
                        .permitAll())
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
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
        ;

        return http.build();
    }

}
