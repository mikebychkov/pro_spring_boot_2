package pro.sb2.todojms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "todo.jms")
public class ToDoProperties {

    private String destination;
}
