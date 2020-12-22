package pro.sb2.tododirectorysecurity.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import pro.sb2.tododirectorysecurity.model.Person;
import pro.sb2.tododirectorysecurity.store.PersonRepository;

public class DirectoryUserDetailService implements UserDetailsService {

    private PersonRepository repository;

    public DirectoryUserDetailService(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        try {
            final Person person = repository.findByEmailIgnoreCase(s);
            if (person != null) {
                PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
                String password = encoder.encode(person.getPassword());
                return User.withUsername(person.getEmail()).accountLocked(!person.isEnabled())
                        .password(password).roles(person.getRole()).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new UsernameNotFoundException(s);
    }
}
