package pro.sb2.todojms.error;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.ErrorHandler;

public class ToDoErrorHandler implements ErrorHandler {

    private final Logger log = LogManager.getLogger(ToDoErrorHandler.class);

    @Override
    public void handleError(Throwable t) {
        log.warn("### ERROR ToDo ...");
        log.error(t.getCause().getMessage());
    }
}
