package pro.sb2.todoreactivedata.react;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ToDoRouter {

    private final ToDoHandler toDoHandler;

    @Autowired
    public ToDoRouter(ToDoHandler toDoHandler) {
        this.toDoHandler = toDoHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> monoRouterFunction() {
        return route(
                GET("/fn-api/todo/{id}").and(accept(MediaType.APPLICATION_JSON)), toDoHandler::getToDo
        ).andRoute(
                GET("/fn-api/todo").and(accept(MediaType.APPLICATION_JSON)), toDoHandler::getToDos
        ).andRoute(
                POST("/fn-api/todo").and(accept(MediaType.APPLICATION_JSON)), toDoHandler::addToDo
        );
    }
}
