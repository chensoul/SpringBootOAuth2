package willydekeyser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated())
                .oauth2Login(oauth2Login ->
                        oauth2Login.loginPage("/oauth2/authorization/myoauth2")
                                .userInfoEndpoint(userInfo -> userInfo
                                        .userAuthoritiesMapper(userAuthoritiesMapper())
                                )
                )
                .oauth2Client(withDefaults());
        return http.build();
    }

    @Bean
    public GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return oauthAuthorities -> {
            final String role = "USER";
            final List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
            return authorities;
        };
    }
}
