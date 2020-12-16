package pro.sb2.todojpa.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

public class ToDoValidationErrorBuilder {

    public static ToDoValidationError fromBindingErrors(Errors errors) {
        ToDoValidationError rsl = new ToDoValidationError("Validation failed. "
                + errors.getErrorCount() + " errors.");
        for (ObjectError er : errors.getAllErrors()) {
            rsl.addValidationError(er.getDefaultMessage());
        }
        return rsl;
    }
}
