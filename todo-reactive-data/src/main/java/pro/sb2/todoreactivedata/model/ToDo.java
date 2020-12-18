package pro.sb2.todoreactivedata.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document
@Data
public class ToDo {

    @Id
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
