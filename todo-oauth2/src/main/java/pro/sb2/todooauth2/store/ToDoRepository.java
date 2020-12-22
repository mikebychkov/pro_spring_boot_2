package pro.sb2.todooauth2.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pro.sb2.todooauth2.model.ToDo;

@RepositoryRestResource
public interface ToDoRepository extends JpaRepository<ToDo, String> {
}
