package nido.backnido.utils;

import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

public class UtilsException {

    public static List<String> fieldBindingErrors(BindingResult result){
        List<String> errors = new ArrayList<>();
        result.getFieldErrors().forEach(fbe -> {
                errors.add(fbe.getField() + " "+ fbe.getDefaultMessage());
        });
        return errors;
    }
}
