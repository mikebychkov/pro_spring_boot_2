package pro.sb2.todoapp.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pro.sb2.todoapp.model.ToDo;
import pro.sb2.todoapp.model.ToDoBuilder;
import pro.sb2.todoapp.store.CommonRepository;
import pro.sb2.todoapp.validation.ToDoValidationError;
import pro.sb2.todoapp.validation.ToDoValidationErrorBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api")
public class ToDoController {

    private final CommonRepository<ToDo> repo;

    @Autowired
    public ToDoController(CommonRepository<ToDo> repo) {
        this.repo = repo;
    }

    @GetMapping("/todo")
    public ResponseEntity<Iterable<ToDo>> getAll() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/todo/{id}")
    public ResponseEntity<ToDo> getById(@PathVariable String id) {
        return ResponseEntity.ok(repo.findById(id));
    }

    @PutMapping("/todo/{id}")
    public ResponseEntity<ToDo> setCompleted(@PathVariable String id) {
        ToDo toDo = repo.findById(id);
        toDo.setCompleted(true);
        repo.save(toDo);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(toDo.getId()).toUri();
        return ResponseEntity.ok().header("Location", location.toString()).build();
    }

    @RequestMapping(value = "/todo", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<?> create(@Valid @RequestBody ToDo toDo, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ToDoValidationErrorBuilder.fromBindingErrors(errors));
        }
        ToDo rsl = repo.save(toDo);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(rsl.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/todo/{id}")
    public ResponseEntity<ToDo> delete(@PathVariable String id) {
        repo.delete(ToDoBuilder.create().withId(id).build());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/todo")
    public ResponseEntity<ToDo> delete(@RequestBody ToDo toDo) {
        repo.delete(toDo);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ToDoValidationError handleException(Exception exception) {
        return new ToDoValidationError(exception.getMessage());
    }
}
