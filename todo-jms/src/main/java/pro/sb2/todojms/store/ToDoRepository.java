package pro.sb2.todojms.store;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sb2.todojms.model.ToDo;

public interface ToDoRepository extends JpaRepository<ToDo, String> {
}
