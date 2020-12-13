package pro.sb2.todoapp.store;

import org.springframework.stereotype.Repository;
import pro.sb2.todoapp.model.ToDo;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ToDoRepository implements CommonRepository<ToDo> {

    private Map<String, ToDo> store = new HashMap<>();

    @Override
    public ToDo save(ToDo todo) {
        ToDo rsl = store.get(todo.getId());
        if (rsl != null) {
            rsl.setModified(LocalDateTime.now());
            rsl.setDescription(todo.getDescription());
            rsl.setCompleted(todo.getCompleted());
            todo = rsl;
        }
        store.put(todo.getId(), todo);
        return store.get(todo.getId());
    }

    @Override
    public Iterable<ToDo> save(Collection<ToDo> todos) {
        todos.forEach(this::save);
        return findAll();
    }

    @Override
    public void delete(ToDo todo) {
        store.remove(todo.getId());
    }

    @Override
    public ToDo findById(String id) {
        return store.get(id);
    }

    @Override
    public Iterable<ToDo> findAll() {
        return store.entrySet().stream().sorted(entryComparator)
                .map(Map.Entry::getValue).collect(Collectors.toList());
    }

    private Comparator<Map.Entry<String, ToDo>> entryComparator =
            (Map.Entry<String, ToDo> e1, Map.Entry<String, ToDo> e2) -> {
                return e1.getValue().getCreated().compareTo(e2.getValue().getCreated());
            };
}
