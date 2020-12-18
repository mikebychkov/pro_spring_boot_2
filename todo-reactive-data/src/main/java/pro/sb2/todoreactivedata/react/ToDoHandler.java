package pro.sb2.todoreactivedata.react;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pro.sb2.todoreactivedata.model.ToDo;
import pro.sb2.todoreactivedata.store.ToDoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;

@Component
public class ToDoHandler {

    private final Logger log = LogManager.getLogger(ToDoHandler.class);

    private final ToDoRepository repo;

    @Autowired
    public ToDoHandler(ToDoRepository repo) {
        this.repo = repo;
    }

    public Mono<ServerResponse> getToDo(ServerRequest request) {
        String id = request.pathVariable("id");
        return findById(id);
    }

    public Mono<ServerResponse> getToDos(ServerRequest request) {
        Flux<ToDo> toDos = repo.findAll();
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(toDos, ToDo.class);
    }

    public Mono<ServerResponse> addToDo(ServerRequest request) {
        Mono<ToDo> toDo = request.bodyToMono(ToDo.class);
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromPublisher(toDo.flatMap(this::save), ToDo.class));
    }

    private Mono<ServerResponse> findById(String id) {
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        Mono<ToDo> toDo = repo.findById(id);
        return toDo.flatMap(
                t -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromObject(t))
        ).switchIfEmpty(notFound);
    }

    private Mono<ToDo> save(ToDo toDo) {
        return Mono.fromSupplier(
                () -> {
                    repo.save(toDo).subscribe();
                    return toDo;
                }
        );
    }
}
