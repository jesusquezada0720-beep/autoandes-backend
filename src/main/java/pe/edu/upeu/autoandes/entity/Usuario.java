package pe.edu.upeu.autoandes.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.util.HashSet;
import java.util.Set;
@Entity @Table(name="usuarios") @Getter @Setter @NoArgsConstructor
public class Usuario {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @JdbcTypeCode(SqlTypes.NUMERIC) private Long id;
 @Column(nullable=false,unique=true,length=60) private String username;
 @Column(nullable=false,length=100) private String password;
 @Column(name="nombrecompleto",nullable=false,length=120) private String nombreCompleto;
 @Column(nullable=false) @JdbcTypeCode(SqlTypes.NUMERIC) private Integer activo=1;
 @ManyToMany(fetch=FetchType.EAGER) @JoinTable(name="usuario_roles",joinColumns=@JoinColumn(name="usuario_id"),inverseJoinColumns=@JoinColumn(name="rol_id")) private Set<Rol> roles=new HashSet<>();
}
