package nido.backnido.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomBaseException extends RuntimeException{

    private int codeError;

    public CustomBaseException(String message, int codeError) {
        super(message, null, true, false);
        this.codeError = codeError;
    }
}
