package pro.sb2.todomongo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Document
@Data
public class ToDo {

    @Id
    @NotNull
    private String id;
    @NotNull
    @NotEmpty
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
}
