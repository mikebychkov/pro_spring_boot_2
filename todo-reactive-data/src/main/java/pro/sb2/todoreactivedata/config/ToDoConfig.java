package pro.sb2.todoreactivedata.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import pro.sb2.todoreactivedata.model.ToDo;
import pro.sb2.todoreactivedata.store.ToDoRepository;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "pro.sb2.todoreactivedata.store")
public class ToDoConfig extends AbstractReactiveMongoConfiguration {

    private final Environment environment;

    @Autowired
    public ToDoConfig(Environment environment) {
        this.environment = environment;
    }

    @Override
    protected String getDatabaseName() {
        return "todos";
    }

    @Override
    @Bean
    @DependsOn("embeddedMongoServer")
    public MongoClient reactiveMongoClient() {
        int port = environment.getProperty("local.mongo.port", Integer.class);
        return MongoClients.create(String.format("mongodb://localhost:%d", port));
    }

    @Bean
    public CommandLineRunner insertAndView(ToDoRepository repo, ApplicationContext context) {
        return args -> {
            repo.save(new ToDo("Do homework")).subscribe();
            repo.save(new ToDo("Workout in the morning", true)).subscribe();
            repo.save(new ToDo("Make dinner tonight")).subscribe();
            repo.save(new ToDo("Clean the studio", true)).subscribe();

            repo.findAll().subscribe(System.out::println);
        };
    }
}
