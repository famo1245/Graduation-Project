package meundi.graduationproject.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FriendControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<?> bindException(BindException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }


}
