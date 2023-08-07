package marlon.leoner.avaliacao.tecnica.exceptions;

import lombok.Data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public abstract class AbstractException extends RuntimeException {

    private String id;

    public AbstractException(String message) {
        super(message);
        this.id = this.getClass().getSimpleName().replace("Exception", "");
    }
}
