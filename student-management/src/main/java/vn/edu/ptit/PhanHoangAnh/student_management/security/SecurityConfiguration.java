package vn.edu.ptit.PhanHoangAnh.student_management.security;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.HandlerExceptionResolver;
import vn.edu.ptit.PhanHoangAnh.student_management.helper.exception.CustomAccessDeniedHandler;
import vn.edu.ptit.PhanHoangAnh.student_management.helper.exception.CustomAuthenticationEntryPoint;
import vn.edu.ptit.PhanHoangAnh.student_management.service.CustomUserDetailsService;
import vn.edu.ptit.PhanHoangAnh.student_management.service.UserService;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Value("${student-management.security.jwt.base64-secret}")
    private String jwtKey;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(getSecretKey()));
    }
    private SecretKey getSecretKey() {
        byte[] keyBytes = jwtKey.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, JwtConfiguration.JWT_ALGORITHM.getName());
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withSecretKey(getSecretKey()).macAlgorithm(JwtConfiguration.JWT_ALGORITHM).build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
         JwtGrantedAuthoritiesConverter scopeConverter = new JwtGrantedAuthoritiesConverter();
         scopeConverter.setAuthoritiesClaimName("scope");
         scopeConverter.setAuthorityPrefix("");
         JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
         converter.setJwtGrantedAuthoritiesConverter(scopeConverter);

         return converter;
    }
    @Bean
    public AuthenticationManager authenticationProvider(CustomUserDetailsService customUserDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(customUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   CustomAccessDeniedHandler customAccessDeniedHandler,
                                                   CustomAuthenticationEntryPoint authenticationEntryPoint, CustomAuthenticationEntryPoint customAuthenticationEntryPoint)
            throws Exception {

        http.authorizeHttpRequests(
                configurer -> configurer.requestMatchers("/register/**").permitAll()
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/auth/refresh").permitAll()
                        .requestMatchers("/auth/refresh-with-cookie").permitAll()
                        // Students/Teachers can view Schools & Classes, but only admins can mutate them
                        .requestMatchers(HttpMethod.GET, "/api/school/**", "/api/class/**").hasAnyRole("STUDENT", "TEACHER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/school/**", "/api/class/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/school/**", "/api/class/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/school/**", "/api/class/**").hasRole("ADMIN")
                        .requestMatchers("/api/student/**").hasAnyRole("STUDENT", "TEACHER", "ADMIN")
                        .requestMatchers("/api/teacher/**").hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers("/api/admin/**", "/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
        ).formLogin(
                login -> login.disable());
        http.csrf(csrf -> csrf.disable());
        http.cors(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        http.oauth2ResourceServer(abccc -> abccc.accessDeniedHandler(customAccessDeniedHandler)
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter())));
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

}
