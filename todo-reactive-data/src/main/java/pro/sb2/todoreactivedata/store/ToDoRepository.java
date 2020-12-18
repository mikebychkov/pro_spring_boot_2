package pro.sb2.todoreactivedata.store;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import pro.sb2.todoreactivedata.model.ToDo;

@Repository
public interface ToDoRepository extends ReactiveMongoRepository<ToDo, String> {
}
