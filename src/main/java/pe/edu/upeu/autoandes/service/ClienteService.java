package pe.edu.upeu.autoandes.service;
import org.springframework.stereotype.Service;
import pe.edu.upeu.autoandes.repository.ClienteRepository;
import pe.edu.upeu.autoandes.entity.Cliente;
import java.util.List;
@Service public class ClienteService { private final ClienteRepository repo; public ClienteService(ClienteRepository r){repo=r;} public List<Cliente> listar(){return repo.findAll();} }
