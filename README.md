# AutoAndes Backend

API REST para la gestión de servicios, vehículos y órdenes del taller AutoAndes.

## Requisitos

- JDK 21.
- Oracle Free en `localhost:1522/FREEPDB1` y un usuario/esquema `AUTOANDES` con permisos para crear tablas y secuencias.
- La migración `V1__esquema_y_semilla.sql` crea el esquema del examen y sus datos iniciales. Hibernate usa `validate`; Flyway es quien aplica la migración.

## Ejecución

Desde la carpeta del proyecto:

```powershell
.\mvnw.cmd spring-boot:run
```

El perfil `dev` se activa por defecto y el backend escucha en `http://localhost:8080`. Se puede cambiar Oracle mediante `AUTOANDES_DB_URL`, `AUTOANDES_DB_USERNAME`, `AUTOANDES_DB_PASSWORD` y `AUTOANDES_DB_SCHEMA`. La clave JWT de desarrollo se puede cambiar con `AUTOANDES_JWT_SECRET`.

Swagger UI: `http://localhost:8080/swagger-ui.html`.

Para ejecutar pruebas, incluyendo Flyway V1 y la validación del esquema con Hibernate:

```powershell
.\mvnw.cmd clean test "-Dspring.flyway.enabled=true" "-Dspring.jpa.hibernate.ddl-auto=validate"
```

Las pruebas automatizadas usan H2 en modo Oracle; la conexión a una instancia Oracle real debe comprobarse en el entorno donde se ejecutará el proyecto.

La verificación local de Oracle se realizó contra `FREEPDB1`, esquema `AUTOANDES`: Flyway aplicó V1 y cargó los datos semilla.

## Colección de Postman

Importa `postman/AutoAndes.postman_collection.json` en Postman y ejecuta las carpetas en orden. La colección crea y elimina los registros temporales de servicios y vehículos. La orden de prueba se conserva como anulada.

La colección incluye ejemplos de respuesta y comprobaciones para `200`, `201`, `400`, `401`, `403`, `404` y `409`. Las variables de colección guardan los tokens de `admin`, `asesor` y `jefe` durante la ejecución.

## Usuarios de demostración

La aplicación inicializa estas cuentas si todavía no existen. Las contraseñas se almacenan con BCrypt.

| Usuario | Contraseña | Rol | Estado |
| --- | --- | --- | --- |
| `admin` | `Auto2026*` | ADMIN | Activo |
| `asesor` | `Auto2026*` | ASESOR | Activo |
| `jefe` | `Auto2026*` | JEFE_TALLER | Activo |
| `inactivo` | `Auto2026*` | ASESOR | Inactivo |

## API

Todas las rutas usan el prefijo `/api/v1`. Excepto `POST /auth/login`, requieren `Authorization: Bearer <token>`.

| Método y ruta | Acceso |
| --- | --- |
| `POST /auth/login`, `GET /auth/me` | Login público; datos del usuario autenticado |
| `GET /clientes` | Consultar clientes |
| `GET, POST /servicios`; `GET, PUT, DELETE /servicios/{id}`; `PATCH /servicios/{id}/estado` | Consultar o mantener servicios según permisos |
| `GET /vehiculos?clienteId=...`; `POST /vehiculos`; `GET, PUT, DELETE /vehiculos/{id}` | Consultar o mantener vehículos según permisos |
| `POST /ordenes`; `GET /ordenes?placa=&estado=&desde=&hasta=`; `GET /ordenes/{id}`; `PATCH /ordenes/{id}/anular` | Registrar, consultar o anular órdenes según permisos |
| `GET /reportes/ingresos-por-servicio?desde=AAAA-MM-DD&hasta=AAAA-MM-DD` | Reporte agregado según permisos |

La autorización se basa en los permisos asignados a los roles. La creación de órdenes calcula precios y totales en el backend y se ejecuta en una transacción.
