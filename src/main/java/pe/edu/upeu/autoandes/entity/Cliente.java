package pe.edu.upeu.autoandes.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
@Entity @Table(name="cliente") @Getter @Setter @NoArgsConstructor
public class Cliente {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @JdbcTypeCode(SqlTypes.NUMERIC) private Long id;
 @Column(nullable=false,unique=true,length=11) private String documento;
 @Column(nullable=false,length=120) private String nombres;
 @Column(length=15) private String telefono;
}
