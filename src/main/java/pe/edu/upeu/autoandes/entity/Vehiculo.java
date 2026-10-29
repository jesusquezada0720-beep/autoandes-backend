package pe.edu.upeu.autoandes.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
@Entity @Table(name="vehiculo") @Getter @Setter @NoArgsConstructor
public class Vehiculo {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @JdbcTypeCode(SqlTypes.NUMERIC) private Long id;
 @Column(nullable=false,unique=true,length=7) private String placa;
 @Column(nullable=false,length=40) private String marca;
 @Column(nullable=false,length=40) private String modelo;
 @Column(nullable=false) @JdbcTypeCode(SqlTypes.NUMERIC) private Integer anio;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="cliente_id",nullable=false) private Cliente cliente;
}
