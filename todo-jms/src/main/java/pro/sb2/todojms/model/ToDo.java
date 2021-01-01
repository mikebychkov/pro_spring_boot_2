package pro.sb2.todojms.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class ToDo {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    @NotNull
    @NotEmpty
    private String description;
    @Column(insertable = true, updatable = false)
    private LocalDateTime created;
    private LocalDateTime modified;
    private Boolean completed;

    public ToDo(String description) {
        this.description = description;
    }

    @PrePersist
    void onCreate() {
        setCreated(LocalDateTime.now());
        setModified(LocalDateTime.now());
    }

    @PreUpdate
    void onUpdate() {
        setModified(LocalDateTime.now());
    }
}
