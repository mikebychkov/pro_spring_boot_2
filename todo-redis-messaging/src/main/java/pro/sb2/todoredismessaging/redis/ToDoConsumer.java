package pro.sb2.todoredismessaging.redis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.sb2.todoredismessaging.model.ToDo;
import pro.sb2.todoredismessaging.store.ToDoRepository;

@Component
public class ToDoConsumer {

    private final Logger log = LogManager.getLogger(ToDoConsumer.class);

    private ToDoRepository repository;

    @Autowired
    public ToDoConsumer(ToDoRepository repository) {
        this.repository = repository;
    }

    public void handleMessage(ToDo toDo) {
        log.info("### CONSUMER >>> " + toDo);
        log.info("### CREATED ToDo: " + repository.save(toDo));
    }
}
