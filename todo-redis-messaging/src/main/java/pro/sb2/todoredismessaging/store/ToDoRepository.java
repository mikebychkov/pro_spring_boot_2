package pro.sb2.todoredismessaging.store;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sb2.todoredismessaging.model.ToDo;

public interface ToDoRepository extends JpaRepository<ToDo, String> {
}
