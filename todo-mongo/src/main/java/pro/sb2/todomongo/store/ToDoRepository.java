package pro.sb2.todomongo.store;

import org.springframework.data.repository.CrudRepository;
import pro.sb2.todomongo.model.ToDo;

public interface ToDoRepository extends CrudRepository<ToDo, String> {
}
