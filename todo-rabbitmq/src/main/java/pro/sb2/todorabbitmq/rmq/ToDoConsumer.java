package pro.sb2.todorabbitmq.rmq;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.sb2.todorabbitmq.model.ToDo;
import pro.sb2.todorabbitmq.store.ToDoRepository;

@Component
public class ToDoConsumer {

    private final Logger log = LogManager.getLogger(ToDoConsumer.class);

    private ToDoRepository repository;

    @Autowired
    public ToDoConsumer(ToDoRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = "${todo.amqp.queue}")
    public void processToDo(ToDo toDo) {
        log.info("### CONSUMER >>> " + toDo);
        log.info("### CREATED ToDo: " + repository.save(toDo));
    }
}
