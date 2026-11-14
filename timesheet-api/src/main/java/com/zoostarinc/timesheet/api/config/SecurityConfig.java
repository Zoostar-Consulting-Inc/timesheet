package com.zoostarinc.timesheet.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity security, JwtDecoder jwtDecoder) throws Exception {
		log.info("{}...", "Configuring Security filter chain and CSRF");
		return security.cors(Customizer.withDefaults()).csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(authorize -> authorize
						// Allow preflight requests for CORS from front-end clients
						.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
						// Allow Swagger UI resources (CSS, JS, HTML, images)
						.requestMatchers("/swagger-ui/*.css", "/swagger-ui/*.js", "/v3/api-docs/**", "/webjars/**",
								"/static/**")
						.permitAll().anyRequest().authenticated())
				.oauth2Login(Customizer.withDefaults())
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.decoder(jwtDecoder))).build();
	}
	
	public static String getCurrentSubject() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }

        Object principal = auth.getPrincipal();

        if (principal instanceof Jwt jwt) {
        	log.debug("Found JWT principal: {}", jwt);
            return jwt.getSubject();
        } 
        
        if (principal instanceof OidcUser oidcUser) {
        	log.debug("Found OIDC principal: {}", oidcUser);
            return oidcUser.getSubject();
        }

        return null;
    }

}
