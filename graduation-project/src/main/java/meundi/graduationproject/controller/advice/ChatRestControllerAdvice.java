package meundi.graduationproject.controller.advice;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ChatRestControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<?> bindException(BindException e) {
        Map<String, Object> message = new HashMap<>();
        message.put("error", e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(message);
    }
}
