package pro.sb2.todoredismessaging.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pro.sb2.todoredismessaging.model.ToDo;
import pro.sb2.todoredismessaging.redis.ToDoProducer;

@Configuration
public class ToDoSender {

    @Bean
    public CommandLineRunner sendToDos(ToDoProducer producer, @Value("${todo.redis.topic}") String topic) {
        return args -> {
            producer.sendTo(topic, new ToDo("Not forget to slap Guitarez in the face"));
        };
    }
}
