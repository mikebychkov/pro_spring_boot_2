package pro.sb2.todorest.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sb2.todorest.model.ToDo;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, String> {
}
