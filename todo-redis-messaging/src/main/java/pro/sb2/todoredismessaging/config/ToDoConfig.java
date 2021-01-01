package pro.sb2.todoredismessaging.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import pro.sb2.todoredismessaging.model.ToDo;
import pro.sb2.todoredismessaging.redis.ToDoConsumer;

@Configuration
public class ToDoConfig {

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                  MessageListenerAdapter toDoListenerAdapter,
                                                  @Value("${todo.redis.topic}") String topic) {
        RedisMessageListenerContainer listener = new RedisMessageListenerContainer();
        listener.setConnectionFactory(connectionFactory);
        listener.addMessageListener(toDoListenerAdapter, new PatternTopic(topic));
        return listener;
    }

    @Bean
    public MessageListenerAdapter toDoListenerAdapter(ToDoConsumer consumer) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(consumer);
        adapter.setSerializer(new Jackson2JsonRedisSerializer<>(ToDo.class));
        return adapter;
    }

    @Bean
    public RedisTemplate<String, ToDo> toDoRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, ToDo> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setDefaultSerializer(new Jackson2JsonRedisSerializer<>(ToDo.class));
        template.afterPropertiesSet();
        return template;
    }
}
