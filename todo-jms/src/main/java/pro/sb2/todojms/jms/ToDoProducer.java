package pro.sb2.todojms.jms;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import pro.sb2.todojms.model.ToDo;

@Component
public class ToDoProducer {

    private final Logger log = LogManager.getLogger(ToDoProducer.class);

    private JmsTemplate jmsTemplate;

    @Autowired
    public ToDoProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendTo(String destination, ToDo toDo) {
        jmsTemplate.convertAndSend(destination, toDo);
        log.info("### PRODUCER >>> MESSAGE SENT");
    }
}
