package pe.edu.upeu.autoandes.exception;
import org.springframework.http.HttpStatus;
public class ReglaNegocioException extends RuntimeException {
 private final HttpStatus status;
 public ReglaNegocioException(HttpStatus status,String message){super(message);this.status=status;}
 public HttpStatus getStatus(){return status;}
 public static ReglaNegocioException noEncontrado(String m){return new ReglaNegocioException(HttpStatus.NOT_FOUND,m);}
 public static ReglaNegocioException conflicto(String m){return new ReglaNegocioException(HttpStatus.CONFLICT,m);}
}
