package pro.sb2.todojdbc.model;

public class ToDoBuilder {

    private static ToDoBuilder builder = new ToDoBuilder();
    private String id = null;
    private String description = "";

    private ToDoBuilder() {
    }

    public static ToDoBuilder create() {
        return builder;
    }

    public ToDoBuilder withId(String id) {
        this.id = id;
        return builder;
    }

    public ToDoBuilder withDescription(String description) {
        this.description = description;
        return builder;
    }

    public ToDo build() {
        ToDo rsl = new ToDo(description);
        if (id != null) {
            rsl.setId(id);
        }
        return rsl;
    }
}
