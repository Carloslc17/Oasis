package es.uclm.OasisProject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
         		.authorizeHttpRequests(auth -> auth
                // PÚBLICO
                .requestMatchers(
                    "/",
                    "/login",
                    "/doLogin",
                    "/registro",
                    "/propietario",
                    "/inquilino",
                    "/css/**"
                ).permitAll()

                // SOLO PROPIETARIOS
                .requestMatchers(
                    "/registrarInmueble",
                    "/misInmuebles",
                    "/reservas"
                ).hasRole("PROPIETARIO")
                
                // SOLO INQUILINOS	                           
                .requestMatchers(
	                "/busqueda",
	                "/favoritos",
	                "/misReservas"
	            ).hasRole("INQUILINO")

	            // RESTO: AUTENTICADO
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .loginPage("/login")              // GET 
                .loginProcessingUrl("/doLogin")   // POST 
                .successHandler((request, response, authentication) -> {

                    var authorities = authentication.getAuthorities();

                    if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_PROPIETARIO"))) {
                        response.sendRedirect("/homePropietario");
                    } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_INQUILINO"))) {
                        response.sendRedirect("/homeInquilino");
                    } else {
                        response.sendRedirect("/");
                    }
             })
                .permitAll()
            )
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                    .permitAll()
            );

        return http.build();
    }
    

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    
}

