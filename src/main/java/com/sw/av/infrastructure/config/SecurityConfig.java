package com.sw.av.infrastructure.config;


import com.sw.av.infrastructure.secutiry.JwtAuthenticationFilter;
import java.util.Collections;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig  {

    @Autowired
    private final JwtAuthenticationFilter jwtAuthorizationFilter;

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }
    
    
    @Bean
    public SecurityFilterChain configure(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {

        http
            .cors(cors -> cors.configurationSource(corsConfigurationSourceA()))
            .csrf((csrf) -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                                                //.requestMatchers(HttpMethod.POST, "/users/**").permitAll()
                                                .requestMatchers(mvc.pattern("/users/**")).permitAll()
                                                .requestMatchers(mvc.pattern("/swagger-ui/**")).permitAll()
                                                //.requestMatchers(mvc.pattern("/v3/api-docs/**")).permitAll()
                                                .requestMatchers(mvc.pattern("/v3/**")).permitAll()
                                                .requestMatchers(mvc.pattern("/openapi/**")).permitAll()
                                                .requestMatchers(mvc.pattern("/resource/**")).permitAll()
                                                .anyRequest().authenticated()
                                  )
            .addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    
    /*
    @Bean
    public SecurityFilterChain configure(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {

        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            //.cors(cors -> cors.disable())
            .csrf((csrf) -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                                                //.requestMatchers(HttpMethod.POST, "/users/**").permitAll()
                                                .requestMatchers(mvc.pattern("/users/**")).permitAll()
                                                .requestMatchers(mvc.pattern("/swagger-ui/**")).permitAll()
                                                //.requestMatchers(mvc.pattern("/v3/api-docs/**")).permitAll()
                                                .requestMatchers(mvc.pattern("/v3/**")).permitAll()
                                                .requestMatchers(mvc.pattern("/openapi/**")).permitAll()
                                                .requestMatchers(mvc.pattern("/resource/**")).permitAll()
                                                .anyRequest().authenticated()
                                  )
            .addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    */
    
    @Bean
    public CorsConfigurationSource corsConfigurationSourceA() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            
            // Extraer el dominio desde la petición y permitirlo
            String origin = request.getHeader("Origin");
            if (origin != null && !origin.isEmpty()) {
                config.setAllowedOrigins(Collections.singletonList(origin));
            }

            config.setAllowedMethods(Collections.singletonList("*")); // Permitir todos los métodos
            config.setAllowedHeaders(Collections.singletonList("*")); // Permitir todos los headers
            config.setAllowCredentials(true); // Permitir credenciales

            return config;
        };
    }
    
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSourceB() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("http://localhost:4200","*")); // Permitir cualquier origen
        //config.setAllowedOrigins(List.of("http://localhost:4200")); // Permitir cualquier origen
        config.setAllowedMethods(List.of("*")); // Permitir cualquier método HTTP
        config.setAllowedHeaders(List.of("*")); // Permitir cualquier header
        config.setExposedHeaders(List.of("*")); // Exponer cualquier header en la respuesta
        config.setAllowCredentials(true); // Permitir credenciales
        
        /*
        config.addAllowedOriginPattern("*"); // Permitir todos los orígenes
        config.addAllowedHeader("*"); // Permitir todos los encabezados
        config.addAllowedMethod("*"); // Permitir todos los métodos (GET, POST, PUT, etc.)
        //config.addExposedHeader("ip_address_01"); // Opcional, si necesitas exponer este encabezado al cliente
        config.addExposedHeader("*");
        //config.addAllowedHeader("ip_address_01"); // Permitir el encabezado personalizado
        config.setAllowCredentials(true); // Permitir cookies o credenciales
        */
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Aplica la configuración globalmente
        return source;
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSourceC() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*")); // Permitir todos los orígenes
        configuration.setAllowedMethods(List.of("*")); // Permitir todos los métodos (GET, POST, PUT, DELETE, etc.)
        configuration.setAllowedHeaders(List.of("*")); // Permitir todas las cabeceras
        configuration.setAllowCredentials(true); // Permitir envío de cookies y credenciales

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
}
