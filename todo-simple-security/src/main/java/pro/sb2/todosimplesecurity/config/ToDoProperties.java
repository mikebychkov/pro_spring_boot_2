package pro.sb2.todosimplesecurity.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "todo.auth")
public class ToDoProperties {

    private String findByEmailUri;
    private String username;
    private String password;
}
