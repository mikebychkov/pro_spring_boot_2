package pro.sb2.todowebsocket.event;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import pro.sb2.todowebsocket.config.ToDoProperties;
import pro.sb2.todowebsocket.model.ToDo;

@Component
@RepositoryEventHandler(ToDo.class)
public class ToDoEventHandler {

    private final Logger log = LogManager.getLogger(ToDoEventHandler.class);
    private SimpMessagingTemplate messagingTemplate;
    private ToDoProperties properties;

    @Autowired
    public ToDoEventHandler(SimpMessagingTemplate messagingTemplate, ToDoProperties properties) {
        this.messagingTemplate = messagingTemplate;
        this.properties = properties;
    }

    @HandleAfterCreate
    public void handleToDoSave(ToDo toDo) {
        messagingTemplate.convertAndSend(properties.getBroker() + "/new", toDo);
        log.info(">>> SENDING MESSAGE TO WS: ws://todo/new - " + toDo);
    }
}
