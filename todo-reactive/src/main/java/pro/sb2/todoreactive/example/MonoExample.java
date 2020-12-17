package pro.sb2.todoreactive.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pro.sb2.todoreactive.model.ToDo;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoProcessor;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Configuration
public class MonoExample {

    private final Logger log = LogManager.getLogger(MonoExample.class);

    @Bean
    public CommandLineRunner runMonoExample() {
        return args -> {
            MonoProcessor<ToDo> promise = MonoProcessor.create();
            Mono<ToDo> rsl = promise
                    .doOnSuccess(p -> log.info("MONO >> ToDo: {}", p.getDescription()))
                    .doOnTerminate(() -> log.info("MONO >> Done"))
                    .doOnError(t -> log.error("MONO >> Error: " + t.getMessage(), t))
                    .subscribeOn(Schedulers.single());

            promise.onNext(new ToDo("Buy my ticket to SpringOne"));

            rsl.block(Duration.ofMillis(1000));
        };
    }
}
