package pro.sb2.todosimplesecurity.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pro.sb2.todosimplesecurity.model.Person;

import java.net.URI;

@Component("UserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Logger log = LogManager.getLogger(UserDetailsServiceImpl.class);
    private final ToDoProperties properties;
    private RestTemplate rest;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public UserDetailsServiceImpl(RestTemplateBuilder restTemplateBuilder
            , ToDoProperties properties, BCryptPasswordEncoder encoder) {
        this.properties = properties;
        this.encoder = encoder;
        this.rest = restTemplateBuilder.basicAuthentication(
                properties.getUsername(), properties.getPassword()
        ).build();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(properties.getFindByEmailUri())
                    .queryParam("email", s);
            log.info("Querying: " + builder.toUriString());
            ResponseEntity<Person> response = rest.exchange(
                    RequestEntity.get(URI.create(builder.toUriString()))
                        .accept(MediaTypes.HAL_JSON)
                        .build()
                    , new ParameterizedTypeReference<Person>() { });
            if (response.getStatusCode() == HttpStatus.OK) {
                Person person = response.getBody();
                //PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
                String password = encoder.encode(person.getPassword());
                return User.withUsername(person.getEmail())
                        .password(password)
                        .accountLocked(!person.isEnabled())
                        .roles(person.getRole())
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new UsernameNotFoundException(s);
    }
}
