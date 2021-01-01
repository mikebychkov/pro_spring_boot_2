package pro.sb2.todorabbitmq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pro.sb2.todorabbitmq.model.ToDo;
import pro.sb2.todorabbitmq.rmq.ToDoProducer;

import java.text.SimpleDateFormat;
import java.util.Date;

@EnableScheduling
@Configuration
public class ToDoSender {

    @Autowired
    private ToDoProducer producer;

    @Value("${todo.amqp.queue}")
    private String queue;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 500L)
    private void sendMsg() {
        producer.sendTo(queue, new ToDo("Thinking of Spring Boot at " + dateFormat.format(new Date())));
    }

    @Bean
    public CommandLineRunner sendToDos() {
        return args -> {
            sendMsg();
        };
    }
}
