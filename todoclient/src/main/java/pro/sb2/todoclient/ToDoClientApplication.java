package pro.sb2.todoclient;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pro.sb2.todoclient.model.ToDo;
import pro.sb2.todoclient.service.ToDoRestClient;

@SpringBootApplication
public class ToDoClientApplication {

    public static void main(String[] args) {
        //SpringApplication.run(ToDoClientApplication.class, args);
        SpringApplication app = new SpringApplication(ToDoClientApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }

    private Logger log = LogManager.getLogger(ToDoClientApplication.class);

    @Bean
    public CommandLineRunner process(ToDoRestClient client) {
        return args -> {
            Iterable<ToDo> toDos = client.findAll();
            assert toDos != null;
            toDos.forEach(log::info);

            ToDo newToDo = client.upsert(new ToDo("Drink plenty of water daily!"));
            assert newToDo != null;
            log.info(newToDo);

            ToDo toDo = client.findById(newToDo.getId());
            assert toDo != null;
            log.info(toDo);

            ToDo completed = client.setCompleted(newToDo.getId());
            assert completed.getCompleted();
            log.info(completed);

            client.delete(newToDo.getId());
            assert client.findById(newToDo.getId()) == null;
        };
    }
}
