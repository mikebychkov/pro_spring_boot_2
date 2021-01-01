package pro.sb2.todorabbitmq.store;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sb2.todorabbitmq.model.ToDo;

public interface ToDoRepository extends JpaRepository<ToDo, String> {
}
