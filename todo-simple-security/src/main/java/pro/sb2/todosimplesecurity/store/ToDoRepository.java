package pro.sb2.todosimplesecurity.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pro.sb2.todosimplesecurity.model.ToDo;

@RepositoryRestResource
public interface ToDoRepository extends JpaRepository<ToDo, String> {
}
