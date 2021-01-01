package pro.sb2.todojms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pro.sb2.todojms.jms.ToDoProducer;
import pro.sb2.todojms.model.ToDo;

@Configuration
public class ToDoSender {

    @Bean
    public CommandLineRunner sendToDos(@Value("${todo.jms.destination}") String destination, ToDoProducer producer) {
        return args -> {
            producer.sendTo(destination, new ToDo("Workout in the morning."));
        };
    }
}
