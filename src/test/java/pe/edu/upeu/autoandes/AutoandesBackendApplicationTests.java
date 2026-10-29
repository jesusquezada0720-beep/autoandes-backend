package pe.edu.upeu.autoandes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import pe.edu.upeu.autoandes.entity.Rol;
import pe.edu.upeu.autoandes.entity.Usuario;
import pe.edu.upeu.autoandes.entity.Cliente;
import pe.edu.upeu.autoandes.repository.ClienteRepository;
import pe.edu.upeu.autoandes.repository.RolRepository;
import pe.edu.upeu.autoandes.repository.UsuarioRepository;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class AutoandesBackendApplicationTests {
    @Autowired MockMvc mvc;
    @Autowired UsuarioRepository usuarios;
    @Autowired RolRepository roles;
    @Autowired ClienteRepository clientes;
    @Autowired PasswordEncoder encoder;

    @BeforeEach
    void prepararDatosDeSeguridad() {
        usuarios.deleteAll();
        roles.deleteAll();
        Rol admin = crearRol("ADMIN");
        Rol asesor = crearRol("ASESOR");
        crearUsuario("admin-test", admin, true);
        crearUsuario("asesor-test", asesor, true);
        crearUsuario("inactivo-test", asesor, false);
    }

    @Test
    void exigeJwtYPermiteAccesoConTokenValido() throws Exception {
        mvc.perform(get("/api/v1/servicios")).andExpect(status().isUnauthorized());
        String token = tokenDe("admin-test");
        mvc.perform(get("/api/v1/servicios").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
        mvc.perform(get("/api/v1/auth/me").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.username").value("admin-test"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.permisos").isArray())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.token").doesNotExist());
        mvc.perform(get("/api/v1/servicios").header("Authorization", "Bearer token-invalido"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void bloqueaUsuariosInactivosYRespetaPermisosDeRol() throws Exception {
        mvc.perform(post("/api/v1/auth/login").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"inactivo-test\",\"password\":\"Auto2026*\"}"))
                .andExpect(status().isUnauthorized());
        String token = tokenDe("asesor-test");
        mvc.perform(post("/api/v1/servicios").header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"codigo\":\"SRV-009\",\"nombre\":\"Servicio nuevo\",\"precio\":20,\"duracionMin\":30}"))
                .andExpect(status().isForbidden());
    }

    @Test
    void registraVehiculoYDevuelveDatosDelCliente() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setDocumento("T-" + java.util.UUID.randomUUID().toString().substring(0, 9));
        cliente.setNombres("Cliente para prueba");
        cliente.setTelefono("999000000");
        Long clienteId = clientes.save(cliente).getId();
        String token = tokenDe("admin-test");
        mvc.perform(post("/api/v1/vehiculos").header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"placa\":\"TST-999\",\"marca\":\"Toyota\",\"modelo\":\"Yaris\",\"anio\":2022,\"clienteId\":" + clienteId + "}"))
                .andExpect(status().isCreated())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.clienteNombre").value("Cliente para prueba"));
        mvc.perform(get("/api/v1/vehiculos").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    private String tokenDe(String username) throws Exception {
        String response = mvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"" + username + "\",\"password\":\"Auto2026*\"}"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        Matcher matcher = Pattern.compile("\\\"token\\\"\\s*:\\s*\\\"([^\\\"]+)\\\"").matcher(response);
        assertTrue(matcher.find(), "La respuesta debe incluir un token JWT");
        return matcher.group(1);
    }

    private Rol crearRol(String nombre) {
        Rol rol = new Rol();
        rol.setNombre(nombre);
        return roles.save(rol);
    }

    private void crearUsuario(String username, Rol rol, boolean activo) {
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(encoder.encode("Auto2026*"));
        usuario.setNombreCompleto("Cuenta de prueba " + username);
        usuario.setActivo(activo ? 1 : 0);
        usuario.getRoles().add(rol);
        usuarios.save(usuario);
    }
}
