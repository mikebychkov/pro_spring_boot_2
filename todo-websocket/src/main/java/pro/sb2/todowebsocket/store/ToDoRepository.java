package pro.sb2.todowebsocket.store;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sb2.todowebsocket.model.ToDo;

public interface ToDoRepository extends JpaRepository<ToDo, String> {
}
