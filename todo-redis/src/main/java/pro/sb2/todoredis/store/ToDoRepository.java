package pro.sb2.todoredis.store;

import org.springframework.data.repository.CrudRepository;
import pro.sb2.todoredis.model.ToDo;

public interface ToDoRepository extends CrudRepository<ToDo, String> {
}
