package pe.edu.upeu.autoandes.security;
import tools.jackson.databind.ObjectMapper;
import jakarta.servlet.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import pe.edu.upeu.autoandes.dto.ErrorResponse;
import java.io.IOException;
import java.time.Instant;
@Component public class RestAccessDeniedHandler implements AccessDeniedHandler {
 private final ObjectMapper mapper;public RestAccessDeniedHandler(ObjectMapper m){mapper=m;}
 @Override public void handle(HttpServletRequest req,HttpServletResponse res,AccessDeniedException e)throws IOException{res.setStatus(403);res.setContentType("application/json");mapper.writeValue(res.getOutputStream(),new ErrorResponse(Instant.now(),403,"Forbidden","No tiene permiso para realizar esta operación",req.getRequestURI()));}
}
