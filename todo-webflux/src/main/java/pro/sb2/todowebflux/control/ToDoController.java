package pro.sb2.todowebflux.control;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pro.sb2.todowebflux.model.ToDo;
import pro.sb2.todowebflux.store.ToDoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class ToDoController {

    private final Logger log = LogManager.getLogger(ToDoController.class);

    private final ToDoRepository repo;

    @Autowired
    public ToDoController(ToDoRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/todo")
    public Flux<ToDo> getAll() {
        return repo.findAll();
    }

    @GetMapping("/todo/{id}")
    public Mono<ToDo> getById(@PathVariable String id) {
        var rsl = repo.findById(id);
        return rsl;
    }
}
