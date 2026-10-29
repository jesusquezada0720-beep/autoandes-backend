package pe.edu.upeu.autoandes.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import pe.edu.upeu.autoandes.enums.EstadoOrden;
@Entity @Table(name="orden_servicio") @Getter @Setter @NoArgsConstructor
public class OrdenServicio {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @JdbcTypeCode(SqlTypes.NUMERIC) private Long id;
 @Column(nullable=false,unique=true,length=9) private String numero;
 @Column(nullable=false) private LocalDateTime fecha;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="vehiculo_id",nullable=false) private Vehiculo vehiculo;
 @Column(nullable=false,precision=7) @JdbcTypeCode(SqlTypes.NUMERIC) private Long kilometraje;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=12) private EstadoOrden estado;
 @Column(nullable=false,precision=10,scale=2) private BigDecimal total;
 @Column(name="usuario_registro",nullable=false,length=30) private String usuarioRegistro;
 @Column(name="motivo_anulacion",length=200) private String motivoAnulacion;
 @OneToMany(mappedBy="orden",cascade=CascadeType.ALL,orphanRemoval=true) private List<DetalleOrden> detalles=new ArrayList<>();
}
