package pro.sb2.todorabbitmq.rmq;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.sb2.todorabbitmq.model.ToDo;

@Component
public class ToDoProducer {

    private final Logger log = LogManager.getLogger(ToDoProducer.class);

    private RabbitTemplate template;

    @Autowired
    public ToDoProducer(RabbitTemplate template) {
        this.template = template;
    }

    public void sendTo(String queue, ToDo toDo) {
        template.convertAndSend(queue, toDo);
        log.info("### PRODUCER >>> MESSAGE SENT");
    }
}
