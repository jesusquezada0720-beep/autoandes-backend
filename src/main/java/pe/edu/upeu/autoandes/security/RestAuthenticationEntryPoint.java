package pe.edu.upeu.autoandes.security;
import tools.jackson.databind.ObjectMapper;
import jakarta.servlet.http.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import pe.edu.upeu.autoandes.dto.ErrorResponse;
import java.io.IOException;
import java.time.Instant;
@Component public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
 private final ObjectMapper mapper;public RestAuthenticationEntryPoint(ObjectMapper m){mapper=m;}
 @Override public void commence(HttpServletRequest req,HttpServletResponse res,AuthenticationException e)throws IOException{res.setStatus(401);res.setContentType("application/json");mapper.writeValue(res.getOutputStream(),new ErrorResponse(Instant.now(),401,"Unauthorized","No autenticado",req.getRequestURI()));}
}
