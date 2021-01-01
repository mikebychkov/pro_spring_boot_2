package pro.sb2.todoredismessaging.redis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import pro.sb2.todoredismessaging.model.ToDo;

@Component
public class ToDoProducer {

    private final Logger log = LogManager.getLogger(ToDoProducer.class);

    private RedisTemplate template;

    @Autowired
    public ToDoProducer(@Qualifier("toDoRedisTemplate") RedisTemplate template) {
        this.template = template;
    }

    public void sendTo(String topic, ToDo toDo) {
        template.convertAndSend(topic, toDo);
        log.info("### PRODUCER >>> MESSAGE SENT");
    }
}
