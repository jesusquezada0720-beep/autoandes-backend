package pe.edu.upeu.autoandes.exception;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import pe.edu.upeu.autoandes.dto.ErrorResponse;
import pe.edu.upeu.autoandes.dto.ValidationErrorResponse;
import java.time.Instant;
import java.util.*;
@RestControllerAdvice
public class GlobalExceptionHandler {
 private static final Logger log=LoggerFactory.getLogger(GlobalExceptionHandler.class);
 private ErrorResponse body(HttpStatus s,String m,String p){return new ErrorResponse(Instant.now(),s.value(),s.getReasonPhrase(),m,p);}
 @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<ValidationErrorResponse> valid(MethodArgumentNotValidException e,HttpServletRequest r){Map<String,String> fields=new LinkedHashMap<>();e.getBindingResult().getFieldErrors().forEach(x->fields.putIfAbsent(x.getField(),x.getDefaultMessage()));return ResponseEntity.badRequest().body(new ValidationErrorResponse(Instant.now(),400,"Bad Request","Datos inválidos",r.getRequestURI(),fields));}
 @ExceptionHandler({ConstraintViolationException.class,MethodArgumentTypeMismatchException.class}) ResponseEntity<ErrorResponse> bad(Exception e,HttpServletRequest r){return ResponseEntity.badRequest().body(body(HttpStatus.BAD_REQUEST,"Parámetros inválidos",r.getRequestURI()));}
 @ExceptionHandler(ReglaNegocioException.class) ResponseEntity<ErrorResponse> regla(ReglaNegocioException e,HttpServletRequest r){if(e.getStatus().is4xxClientError())log.warn("Regla de negocio rechazada: {} {}",r.getRequestURI(),e.getMessage());return ResponseEntity.status(e.getStatus()).body(body(e.getStatus(),e.getMessage(),r.getRequestURI()));}
 @ExceptionHandler(DataIntegrityViolationException.class) ResponseEntity<ErrorResponse> duplicado(DataIntegrityViolationException e,HttpServletRequest r){log.warn("Conflicto de integridad en {}",r.getRequestURI());return ResponseEntity.status(409).body(body(HttpStatus.CONFLICT,"El registro ya existe o está relacionado con otros datos",r.getRequestURI()));}
 @ExceptionHandler(AccessDeniedException.class) ResponseEntity<ErrorResponse> sinPermiso(AccessDeniedException e,HttpServletRequest r){return ResponseEntity.status(403).body(body(HttpStatus.FORBIDDEN,"No tiene permiso para realizar esta operación",r.getRequestURI()));}
 @ExceptionHandler(Exception.class) ResponseEntity<ErrorResponse> error(Exception e,HttpServletRequest r){log.error("Error no controlado en {}",r.getRequestURI(),e);return ResponseEntity.internalServerError().body(body(HttpStatus.INTERNAL_SERVER_ERROR,"Error interno del servidor",r.getRequestURI()));}
}
