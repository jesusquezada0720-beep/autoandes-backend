package pe.edu.upeu.autoandes.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.math.BigDecimal;
@Entity @Table(name="detalle_orden") @Getter @Setter @NoArgsConstructor
public class DetalleOrden {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @JdbcTypeCode(SqlTypes.NUMERIC) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="orden_id",nullable=false) private OrdenServicio orden;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="servicio_id",nullable=false) private Servicio servicio;
 @Column(nullable=false) @JdbcTypeCode(SqlTypes.NUMERIC) private Integer cantidad;
 @Column(name="precio_unitario",nullable=false,precision=10,scale=2) private BigDecimal precioUnitario;
 @Column(nullable=false,precision=10,scale=2) private BigDecimal subtotal;
}
