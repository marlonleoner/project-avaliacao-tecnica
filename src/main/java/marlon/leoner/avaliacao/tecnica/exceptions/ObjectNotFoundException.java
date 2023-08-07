package marlon.leoner.avaliacao.tecnica.exceptions;

import org.springframework.http.HttpStatus;

public class ObjectNotFoundException extends AbstractException {
    public ObjectNotFoundException(String message) {
        super(message);
    }
}
