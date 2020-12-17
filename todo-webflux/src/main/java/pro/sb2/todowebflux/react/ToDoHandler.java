package pro.sb2.todowebflux.react;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pro.sb2.todowebflux.control.ToDoController;
import pro.sb2.todowebflux.model.ToDo;
import pro.sb2.todowebflux.store.ToDoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
public class ToDoHandler {

    private final Logger log = LogManager.getLogger(ToDoController.class);

    private final ToDoRepository repo;

    @Autowired
    public ToDoHandler(ToDoRepository repo) {
        this.repo = repo;
    }

    public Mono<ServerResponse> getToDo(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        Mono<ToDo> toDo = repo.findById(id);
        return toDo.flatMap(
                t -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromObject(t))
        ).switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> getToDos(ServerRequest request) {
        Flux<ToDo> toDos = repo.findAll();
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(toDos, ToDo.class);
    }
}
