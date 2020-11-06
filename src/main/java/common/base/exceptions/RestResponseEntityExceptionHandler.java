package common.base.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.springframework.dao.DataAccessException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Object> handleDataAccessException(DataAccessException ex, WebRequest request) throws JsonProcessingException {
        return responseApiError(new ApiError(400,ex.getMostSpecificCause().getMessage(),""));
    }

    @ExceptionHandler(EntryDataValidationException.class)
    public ResponseEntity<Object> handleEntryDataValidationException(EntryDataValidationException ex, WebRequest request) throws JsonProcessingException {
        return responseApiError(ex.getApiError());
    }

    @ExceptionHandler(AutomaticResponseException.class)
    public ResponseEntity<Object> handleAutomaticResponseException(AutomaticResponseException ex, WebRequest request) throws JsonProcessingException {
        return responseApiError(ex.getApiError());
    }

    public ResponseEntity responseApiError(ApiError apiError){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            return ResponseEntity.status(apiError.getStatus()).body(objectMapper.writeValueAsString(apiError.getResponseMap()));
        }catch (JsonProcessingException e){
            ApiError apiErrorCasting = new ApiError(400, "Error while parsing the response", "");
            ObjectMapper objectMapper = new ObjectMapper();
            return ResponseEntity.status(apiError.getStatus()).body(apiErrorCasting);
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleConflict(Exception ex, WebRequest request){
        String bodyOfResponse = "Runtime exception in project execution";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
