package pe.edu.upeu.autoandes.security;
import java.util.*;
public final class PermisosPorRol {
 private PermisosPorRol(){}
 private static final Map<String,Set<Permiso>> MAP=Map.of(
  "ADMIN",Set.of(Permiso.SERVICIO_LEER,Permiso.SERVICIO_ESCRIBIR,Permiso.VEHICULO_LEER,Permiso.VEHICULO_ESCRIBIR,Permiso.ORDEN_LEER,Permiso.ORDEN_REGISTRAR,Permiso.ORDEN_ANULAR,Permiso.REPORTE_VER),
  "ASESOR",Set.of(Permiso.SERVICIO_LEER,Permiso.VEHICULO_LEER,Permiso.VEHICULO_ESCRIBIR,Permiso.ORDEN_LEER,Permiso.ORDEN_REGISTRAR),
  "JEFE_TALLER",Set.of(Permiso.SERVICIO_LEER,Permiso.SERVICIO_ESCRIBIR,Permiso.VEHICULO_LEER,Permiso.ORDEN_LEER,Permiso.ORDEN_ANULAR,Permiso.REPORTE_VER));
 public static Set<String> deRoles(Collection<String> roles){Set<String> p=new HashSet<>();roles.forEach(r->MAP.getOrDefault(r,Set.of()).forEach(permiso->p.add(permiso.name())));return p;}
}
