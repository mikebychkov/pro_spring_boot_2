package pro.sb2.todoclient.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "todo")
@Data
public class ToDoRestClientProperties {
    private String url;
    private String basePath;
}
