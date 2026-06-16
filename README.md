# MS Inventario — SuperNOVA

Microservicio de gestión de inventario para el sistema SuperNOVA.
Desarrollado con Spring Boot y conectado a MariaDB mediante JPA/Hibernate.

## Tecnologías utilizadas

- Java 17
- Spring Boot 3.4.5
- Spring Data JPA / Hibernate
- MariaDB
- Lombok
- JUnit 5 y Mockito
- JaCoCo — cobertura de pruebas
- Springdoc OpenAPI (Swagger)
- Docker

## Puerto : http://localhost:8081/

## Documentación de endpoints (Swagger) 

http://localhost:8081/swagger-ui/index.html

## Funcionalidades

- Productos — CRUD completo
- Stock — registro por producto y bodega
- Ventas — registro con descuento automático de stock
- Movimientos de stock — entradas, salidas y ventas
- Pedidos a proveedores
- Categorías y proveedores

## Estructura del proyecto
src/

─ main/java/BackendSuperNova/ms_inventario/

─ controller/     # Endpoints REST

─ service/        # Lógica de negocio

─ model/          # Entidades JPA

─ repository/     # Interfaces JpaRepository

─ config/         # Carga inicial de datos

─ test/               # Pruebas unitarias con Mockito

## Requisitos previos

- Java 17
- Maven
- MariaDB corriendo en puerto 3307

## Ejecutar en local

```bash
./mvnw spring-boot:run
```

## Ejecutar pruebas unitarias

```bash
./mvnw clean test
```

## Generar reporte de cobertura JaCoCo

```bash
./mvnw clean test
```

El reporte queda en:target/site/jacoco/index.html
## Cobertura actual

- Cobertura total: 83%
- Herramienta: JaCoCo 0.8.12

# MS Clientes — SuperNOVA

## Puerto http://localhost:8082/

## Documentación de endpoints (Swagger) 
http://localhost:8082/swagger-ui/index.html

## Funcionalidades

- Solicitudes de clientes — consultas, reclamos y sugerencias
- Bodegas — gestión de ubicaciones del supermercado

## Estructura del proyecto 
src/

─ main/java/BackendSuperNova/ms_cliente/

─ controller/     # Endpoints REST

─ service/        # Lógica de negocio

─ model/          # Entidades JPA

─ repository/     # Interfaces JpaRepository

─ config/         # Carga inicial de datos  

─ test/           # Pruebas unitarias

# La ejecucion es exactamente la misma que el         MS-inventario 
