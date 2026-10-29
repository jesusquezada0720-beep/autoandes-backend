package pe.edu.upeu.autoandes.controller;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.autoandes.dto.*;
import pe.edu.upeu.autoandes.service.AuthService;
@RestController @RequestMapping("/api/v1/auth") public class AuthController {
 private final AuthService service;public AuthController(AuthService s){service=s;}
 @PostMapping("/login") public LoginResponse login(@Valid @RequestBody LoginRequest x){return service.login(x);}
 @GetMapping("/me") @SecurityRequirement(name="bearerAuth") public UsuarioActualResponse me(Authentication a){return service.me(a.getName());}
}
