package pro.sb2.tododirectorysecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import pro.sb2.tododirectorysecurity.store.PersonRepository;

@Configuration
public class DirectorySecurityConfig extends WebSecurityConfigurerAdapter {

    private PersonRepository repository;

    @Autowired
    public DirectorySecurityConfig(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new DirectoryUserDetailService(repository));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/**").hasRole("ADMIN")
            .and()
            .httpBasic();
    }
}
