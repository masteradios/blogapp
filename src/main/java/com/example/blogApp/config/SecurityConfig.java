package com.example.blogApp.config;

import com.example.blogApp.security.CustomUserDetailsService;
import com.example.blogApp.security.jwt.AuthEntryPointJwt;
import com.example.blogApp.security.jwt.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AuthTokenFilter authTokenFilter;

    @Autowired
    private AuthEntryPointJwt authEntryPointJwt;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)
                requests
                        .requestMatchers("/public/**")
                        .permitAll()
                        .anyRequest())
                .authenticated());

        http.sessionManagement(
                (session)->
                        session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //http.formLogin(Customizer.withDefaults());
        //http.httpBasic(Customizer.withDefaults());
        http.csrf((csrfConfigurer -> csrfConfigurer.disable()));
        http.exceptionHandling((configurer)->configurer.authenticationEntryPoint(authEntryPointJwt));
        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);


        return (SecurityFilterChain)http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

}
