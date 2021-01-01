package pro.sb2.todojms.jms;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import pro.sb2.todojms.model.ToDo;
import pro.sb2.todojms.store.ToDoRepository;

import javax.validation.Valid;

@Component
public class ToDoConsumer {

    private final Logger log = LogManager.getLogger(ToDoConsumer.class);

    private ToDoRepository repository;

    @Autowired
    public ToDoConsumer(ToDoRepository repository) {
        this.repository = repository;
    }

    @JmsListener(destination = "${todo.jms.destination}", containerFactory = "jmsFactory")
    public void processToDo(@Valid ToDo toDo) {
        log.info("### CONSUMER >>> " + toDo);
        log.info("### CREATED ToDo: " + repository.save(toDo));
    }
}
