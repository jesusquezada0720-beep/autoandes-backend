package pe.edu.upeu.autoandes.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
@Entity @Table(name="roles") @Getter @Setter @NoArgsConstructor
public class Rol {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @JdbcTypeCode(SqlTypes.NUMERIC) private Long id;
 @Column(nullable=false,unique=true,length=40) private String nombre;
}
