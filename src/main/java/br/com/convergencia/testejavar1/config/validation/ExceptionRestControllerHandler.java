package br.com.convergencia.testejavar1.config.validation;

import br.com.convergencia.testejavar1.config.validation.dto.ExceptionDto;
import br.com.convergencia.testejavar1.gateway.exception.GatewayException;
import br.com.convergencia.testejavar1.service.exception.AuthException;
import br.com.convergencia.testejavar1.service.exception.CpfExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestControllerAdvice
public class ExceptionRestControllerHandler {

    private final Logger logger = LoggerFactory.getLogger(ExceptionRestControllerHandler.class);

    private static final String HANDLE_ERROR_TYPE_MESSAGE = "method=handle, error_type={}, message={}";

    private final MessageSource messageSource;

    public ExceptionRestControllerHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Collection<ExceptionDto> handle(MethodArgumentNotValidException exception) {
        List<ExceptionDto> errors = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        fieldErrors.forEach(e -> {
            String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            errors.add(new ExceptionDto(0, message, e.getField()));
        });

        return errors;
    }

    @ExceptionHandler(CpfExistsException.class)
    public ResponseEntity<ExceptionDto> handle(CpfExistsException e) {
        logger.error(HANDLE_ERROR_TYPE_MESSAGE, e.getType(), e.getType().getMessage(), e);
        return transcode(HttpStatus.BAD_REQUEST, e.getType(), e);
    }

    @ExceptionHandler(GatewayException.class)
    public ResponseEntity<ExceptionDto> handle(GatewayException e) {
        logger.error(HANDLE_ERROR_TYPE_MESSAGE, e.getType(), e.getType().getMessage(), e);
        return transcode(HttpStatus.BAD_GATEWAY, e.getType(), e);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ExceptionDto> handle(AuthException e ) {
        logger.error(HANDLE_ERROR_TYPE_MESSAGE, e.getType(), e.getType().getMessage(), e);
        return transcode(HttpStatus.UNAUTHORIZED, e.getType(), e);
    }

    private ResponseEntity<ExceptionDto> transcode(HttpStatus status, ExceptionTypeEnum type, Throwable throwable) {
        return ResponseEntity.status(status)
                .body(new ExceptionDto(type.getId(), type.getMessage(), throwable.getMessage()));
    }
}
