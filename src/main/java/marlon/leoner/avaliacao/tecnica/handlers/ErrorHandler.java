package marlon.leoner.avaliacao.tecnica.handlers;

import marlon.leoner.avaliacao.tecnica.domains.Error;

import marlon.leoner.avaliacao.tecnica.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Error handleGenericException(Exception ex) {
        return new Error("InternalError", ex.getMessage());
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public Error handleObjectNotFoundException(ObjectNotFoundException ex) {
        return new Error(ex.getClass().getSimpleName(), ex.getMessage());
    }

    @ExceptionHandler({
            ObjectAlreadyExistsException.class,
            InvalidParameterException.class,
            ErrorVoteSessionException.class
    })
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Error handleObjectAlreadyExists(AbstractException ex) {
        return new Error(ex.getId(), ex.getMessage());
    }
}