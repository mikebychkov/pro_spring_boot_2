package pro.sb2.todowebflux.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ToDo {

    private String id;
    private String description;
    private LocalDateTime created;
    private LocalDateTime modified;
    private Boolean completed;

    public ToDo() {
        this.id = UUID.randomUUID().toString();
        LocalDateTime date = LocalDateTime.now();
        this.created = date;
        this.modified = date;
        this.completed = false;
    }

    public ToDo(String description) {
        this();
        this.description = description;
    }

    public ToDo(String description, Boolean completed) {
        this();
        this.description = description;
        this.completed = completed;
    }
}
