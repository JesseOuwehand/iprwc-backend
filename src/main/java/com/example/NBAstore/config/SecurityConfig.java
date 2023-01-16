package com.example.NBAstore.config;

import com.example.NBAstore.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final UserService userService;

    @Autowired
    public SecurityConfig(JwtFilter jwtFilter, UserService userService) {
        this.jwtFilter = jwtFilter;
        this.userService = userService;
    }

    // TODO Check alle auths! (/** heeft voorang op /add bijv.)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic().disable()
                .cors()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/register", "/api/v1/auth/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/category/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/category/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "api/v1/category/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/product/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/product/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "api/v1/product/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/user/info").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/api/v1/cart/**").hasAnyRole("ADMIN", "USER")
                .and()
                .userDetailsService(userService)
                .exceptionHandling()
                .authenticationEntryPoint(
                        ((request, response, authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"))
                )
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
        throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
