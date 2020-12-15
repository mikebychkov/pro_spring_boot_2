package pro.sb2.todojdbc.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sb2.todojdbc.model.ToDo;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ToDoRepository implements CommonRepository<ToDo> {

    private static final String SQL_INSERT = "INSERT INTO todo (id, description, created, modified, completed)"
            + "VALUES (:id, :description, :created, :modified, :completed)";
    private static final String SQL_FIND_ALL = "SELECT id, description, created, modified, completed FROM todo";
    private static final String SQL_FIND_BY_ID = SQL_FIND_ALL + " WHERE id = :id";
    private static final String SQL_UPDATE = "UPDATE todo SET description = :description, modified = :modified"
            + ", completed = :completed WHERE id = :id";
    private static final String SQL_DELETE = "DELETE FROM todo WHERE id = :id";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public ToDoRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<ToDo> toDoRowMapper = (ResultSet rs, int rowNum) -> {
        ToDo toDo = new ToDo();
        toDo.setId(rs.getString("id"));
        toDo.setDescription(rs.getString("description"));
        toDo.setCreated(rs.getTimestamp("created").toLocalDateTime());
        toDo.setModified(rs.getTimestamp("modified").toLocalDateTime());
        toDo.setCompleted(rs.getBoolean("completed"));
        return toDo;
    };

    private ToDo upsert(final ToDo toDo, final String sql) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", toDo.getId());
        params.put("description", toDo.getDescription());
        params.put("created", toDo.getCreated());
        params.put("modified", toDo.getModified());
        params.put("completed", toDo.getCompleted());
        jdbcTemplate.update(sql, params);
        return findById(toDo.getId());
    }

    @Override
    public ToDo save(ToDo toDo) {
        ToDo rsl = findById(toDo.getId());
        if (rsl != null) {
            rsl.setDescription(toDo.getDescription());
            rsl.setModified(toDo.getModified());
            rsl.setCompleted(toDo.getCompleted());
            return upsert(toDo, SQL_UPDATE);
        }
        return upsert(toDo, SQL_INSERT);
    }

    @Override
    public Iterable<ToDo> save(Collection<ToDo> toDos) {
        toDos.forEach(this::save);
        return findAll();
    }

    @Override
    public void delete(ToDo toDo) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", toDo.getId());
        jdbcTemplate.update(SQL_DELETE, params);
    }

    @Override
    public ToDo findById(String id) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, params, toDoRowMapper);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Iterable<ToDo> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, toDoRowMapper);
    }
}
