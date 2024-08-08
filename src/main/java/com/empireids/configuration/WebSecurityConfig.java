package com.empireids.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@ComponentScan
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/", "/home").permitAll()
				.anyRequest()
			)
			.formLogin((form) -> form
				.loginPage("/login")
				.permitAll()
			)
			.logout((logout) -> logout.permitAll());

		return http.build();
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        var encoders = new HashMap<String, PasswordEncoder>(
                Map.of("bcrypt",new BCryptPasswordEncoder(),
                        "noop", NoOpPasswordEncoder.getInstance()));

        var e = new DelegatingPasswordEncoder("noop", encoders);
        return e;
    }
	
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user =
				User.withUsername("atlas")
				.password("Pass4Atlas")
				.roles("ROLE_ADMIN")
				.build();

		return new InMemoryUserDetailsManager(user);
	}
	
	@Bean
	public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
	    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	    provider.setPasswordEncoder(passwordEncoder);
	    provider.setUserDetailsService(userDetailsService);
	    return new ProviderManager(provider);
	}
	
	@Bean
	static GrantedAuthorityDefaults grantedAuthorityDefaults() {
		return new GrantedAuthorityDefaults("ROLE_ADMIN");
	}
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configurer ->
                   configurer.requestMatchers("/css/**").permitAll()
                .anyRequest());
        
        http.authorizeHttpRequests(configurer ->
        		configurer.requestMatchers("/images/**").permitAll()
        			.anyRequest());
        
        http.authorizeHttpRequests(configurer ->
				configurer.requestMatchers("/js/**").permitAll()
					.anyRequest());
        
        http.authorizeHttpRequests(configurer ->
				configurer.requestMatchers("/home").permitAll()
					.anyRequest());
        
        http.formLogin(Customizer.withDefaults());

        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}