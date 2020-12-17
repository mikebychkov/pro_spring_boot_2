package pro.sb2.todoreactive.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pro.sb2.todoreactive.model.ToDo;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Configuration
public class FluxExample {

    private final Logger log = LogManager.getLogger(FluxExample.class);

    @Bean
    public CommandLineRunner runFluxExample() {
        return args -> {
            EmitterProcessor<ToDo> stream = EmitterProcessor.create();
            Mono<List<ToDo>> promise = stream
                    .filter(s -> s.getCompleted())
                    .doOnNext(s -> log.info("FLUX >> ToDo: {}", s.getDescription()))
                    .collectList()
                    .subscribeOn(Schedulers.single());

            stream.onNext(new ToDo("Read a book", true));
            stream.onNext(new ToDo("Listen classical music", true));
            stream.onNext(new ToDo("Workout in the mornings"));
            stream.onNext(new ToDo("Organize my room", true));
            stream.onNext(new ToDo("Learn Project Reactor", true));

            stream.onComplete();

            promise.block();
        };
    }
}
