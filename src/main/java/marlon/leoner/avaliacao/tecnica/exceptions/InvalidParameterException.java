package marlon.leoner.avaliacao.tecnica.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidParameterException extends AbstractException {
    public InvalidParameterException(String message) {
        super(message);
    }
}
