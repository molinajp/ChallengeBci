# Proyecto Individual BCI - Juan Pablo Molina



## Descripción del Proyecto

Este proyecto es una API REST con Spring Boot y gradle para la creación y logueo de usuarios, con seguridad a través de JWT. Consta de 2 endpoints (url base localhost:8080/api/v1) los cuales sirven para registrar un usuario (/sign-up), y loguearse con el token JWT generado por el endpoint anterior (/login). Para ver los contratos de entrada y salida de cada endpoint se agrega el archivo *API - Open API.yaml* con la documentación correspondiente.

Este proyecto tiene las siguientes dependencias:
- Actuator
- Spring Data JPA
- Validation
- Spring Web
- Java JWT
- DevTools
- Base de datos H2
- Lombok
- Spock

## Ejecución del proyecto

**Aclaracion: Es necesario tener instalada la versión 8 o superior de java para ejecutar el programa.**

Para ejecutar la aplicación hay que descargar el código y correr un gradle build en el proyecto. Esto generará un nuevo archivo .jar en la carpeta target con los últimos cambios. Luego, se debe ejecutar en una terminal los siguientes comandos:

```
cd <directorio-donde-se-generó-el-jar>
java -jar <nombre-del-archivo.jar>
```

También se dejan archivos *Dockerfile* y *docker-compose.yaml* para ejecutar el proyecto (Es necesario tener instalado Docker).

```
cd <directorio-base-donde-está-el-proyecto>
docker compose up
```

## Diagramas del proyecto

### Diagramas de componentes:

- Teléfonos:

###### PhoneEntity

| Campo       | Tipo de Dato | ¿Es obligatorio? | ¿Tiene restricción de formato? | ¿Es persistido a la BD? |
|-------------|--------------|------------------|--------------------------------|-------------------------|
| id          | long         | Si               | No                             | Si                      |
| number      | long         | No               | No                             | Si                      |
| cityCode    | int          | No               | No                             | Si                      |
| countryCode | String       | No               | No                             | Si                      |

###### Tabla de BD PHONES

| Nombre Columna | Tipo de Dato           | ¿Es obligatorio? | ¿Tiene restricción de formato? | ¿Es clave foránea? |
|----------------|------------------------|------------------|--------------------------------|--------------------|
| ID             | BIGINT                 | Si               | No                             | No                 |
| NUMBER         | INTEGER                | No               | No                             | No                 |
| CITY\_CODE     | CHARACTER VARYING(255) | No               | No                             | No                 |
| COUNTRY\_CODE  | BIGINT                 | No               | No                             | No                 |
| USER\_ID       | CHARACTER VARYING(255) | No               | No                             | Si                 |

- Usuarios:

###### UserEntity

| Campo     | Tipo de Dato     | ¿Es obligatorio? | ¿Tiene restricción de formato?                                                                        | ¿Es persistido a la BD? |
|-----------|------------------|------------------|-------------------------------------------------------------------------------------------------------|-------------------------|
| id        | UUID             | No               | Debe ser formato UUID                                                                                 | Si                      |
| created   | Date             | No               | No                                                                                                    | Si                      |
| lastLogin | Date             | No               | No                                                                                                    | Si                      |
| name      | String           | No               | No                                                                                                    | Si                      |
| email     | String           | Si               | Debe ser formato de mail válido                                                                       | Si                      |
| password  | String           | Si               | Debe tener 2 números, 1 mayúscula, entre<br>8 y 12 caracteres de largo y sin caracteres<br>especiales | Si                      |
| phones    | Set<PhoneEntity> | No               | Cada elemento de la colección debe<br>respetar el formato de la tabla PhoneEntity                     | Si (en la tabla PHONES) |
| token     | String           | No               | Debe ser un token JWT                                                                                 | No                      |
| isActive  | boolean          | No               | No                                                                                                    | Si                      |

###### Tabla de BD USERS

| Nombre Columna | Tipo de Dato           | ¿Es obligatorio? | ¿Tiene restricción de formato? | ¿Es clave foránea? |
|----------------|------------------------|------------------|--------------------------------|--------------------|
| ID             | CHARACTER VARYING(255) | Si               | No                             | No                 |
| CREATED        | TIMESTAMP              | No               | No                             | No                 |
| LAST\_LOGIN    | TIMESTAMP              | No               | No                             | No                 |
| NAME           | CHARACTER VARYING(255) | No               | No                             | No                 |
| EMAIL          | CHARACTER VARYING(255) | No               | No                             | No                 |
| PASSWORD       | CHARACTER VARYING(255) | No               | No                             | No                 |
| IS\_ACTIVE     | BOOLEAN                | No               | No                             | No                 |

### Diagramas de secuencia:

- Endpoint /sign_up:

![Diagrama de secuencia UML para /sign_up](/src/main/resources/uml-images/Sign_up-UML.svg)

- Endpoint /login:

![Diagrama de secuencia UML para /login](/src/main/resources/uml-images/Login-UML.svg)
