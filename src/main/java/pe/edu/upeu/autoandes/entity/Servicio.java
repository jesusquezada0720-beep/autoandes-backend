package pe.edu.upeu.autoandes.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.math.BigDecimal;
@Entity @Table(name="servicio") @Getter @Setter @NoArgsConstructor
public class Servicio {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @JdbcTypeCode(SqlTypes.NUMERIC) private Long id;
 @Column(nullable=false,unique=true,length=7) private String codigo;
 @Column(nullable=false,length=80) private String nombre;
 @Column(nullable=false,precision=10,scale=2) private BigDecimal precio;
 @Column(name="duracion_min",nullable=false,precision=4) @JdbcTypeCode(SqlTypes.NUMERIC) private Integer duracionMin;
 @Column(nullable=false) @JdbcTypeCode(SqlTypes.NUMERIC) private Integer activo=1;
}
