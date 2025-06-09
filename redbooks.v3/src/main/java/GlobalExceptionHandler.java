//import com.org.kodvix.redbooks.exception.ResourceNotFoundException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.MissingServletRequestParameterException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex) {
//        Map<String, Object> errorDetails = new HashMap<>();
//        errorDetails.put("status", HttpStatus.NOT_FOUND.value());
//        errorDetails.put("error", "Not Found");
//        errorDetails.put("message", ex.getMessage());
//        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
//        Map<String, Object> errorDetails = new HashMap<>();
//        errorDetails.put("status", HttpStatus.BAD_REQUEST.value());
//        errorDetails.put("error", "Bad Request");
//        errorDetails.put("message", ex.getMessage());
//        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    public ResponseEntity<Map<String, Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
//        Map<String, Object> errorDetails = new HashMap<>();
//        errorDetails.put("status", HttpStatus.BAD_REQUEST.value());
//        errorDetails.put("error", "Bad Request");
//        errorDetails.put("message", String.format("Invalid value '%s' for parameter '%s'. Expected type: %s",
//                ex.getValue(), ex.getName(), ex.getRequiredType().getSimpleName()));
//        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(MissingServletRequestParameterException.class)
//    public ResponseEntity<Map<String, Object>> handleMissingParams(MissingServletRequestParameterException ex) {
//        Map<String, Object> errorDetails = new HashMap<>();
//        errorDetails.put("status", HttpStatus.BAD_REQUEST.value());
//        errorDetails.put("error", "Bad Request");
//        errorDetails.put("message", "Missing required parameter: " + ex.getParameterName());
//        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
//        Map<String, Object> errorDetails = new HashMap<>();
//        errorDetails.put("status", HttpStatus.BAD_REQUEST.value());
//        errorDetails.put("error", "Validation Failed");
//
//        StringBuilder messages = new StringBuilder();
//        ex.getBindingResult().getFieldErrors().forEach(error -> {
//            messages.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
//        });
//        errorDetails.put("message", messages.toString());
//
//        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
//        Map<String, Object> errorDetails = new HashMap<>();
//        errorDetails.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
//        errorDetails.put("error", "Internal Server Error");
//        errorDetails.put("message", ex.getMessage());
//        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}
