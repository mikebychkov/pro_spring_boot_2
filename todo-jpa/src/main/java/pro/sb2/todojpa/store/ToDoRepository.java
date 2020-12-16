package pro.sb2.todojpa.store;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sb2.todojpa.model.ToDo;

public interface ToDoRepository extends JpaRepository<ToDo, String> {
}
