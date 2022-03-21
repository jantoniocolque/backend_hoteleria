package nido.backnido.exception;

import lombok.Data;

import java.util.List;

@Data
public class CustomBindingException extends CustomBaseException{
    private List<String> fieldErrors;

    public CustomBindingException(String message, int codeError, List<String> fieldErrors) {
        super(message, codeError);
        this.fieldErrors = fieldErrors;
    }
}
