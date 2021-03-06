package pro.sb2.todojdbc.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ToDo {

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
