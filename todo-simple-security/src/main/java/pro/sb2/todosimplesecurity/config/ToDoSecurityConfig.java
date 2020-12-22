package pro.sb2.todosimplesecurity.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class ToDoSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication().passwordEncoder(encoder())
                .withUser("admin").password(encoder().encode("admin123")).roles("ADMIN", "USER")
                .and()
                .withUser("sara").password(encoder().encode("sara123")).roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .requestMatchers(
                        PathRequest.toStaticResources().atCommonLocations()
                ).permitAll()
                .anyRequest().fullyAuthenticated()
                .and()
                    .formLogin().loginPage("/login").permitAll()
                .and()
                    .logout().logoutRequestMatcher(
                        new AntPathRequestMatcher("/logout")
                    ).logoutSuccessUrl("/login")
                .and()
                .httpBasic();
    }
}
