# TRABAJO INTEGRADOR
## Especialización en BackEnd II
## Certified Tech Developer
## Digital House
### 2023

***

[Contexto de Negocio](https://docs.google.com/document/d/13rEnC2SEKxOZO9ZIiwQra24-jMTwps4Kjy2KjR0m1ls/edit?usp=sharing)

***

## Entrega Parcial
1. [Ejercicio 1](#Ejercicio-1)
2. [Ejercicio 2](#Ejercicio-2)
3. [Ejercicio 3](#Ejercicio-3)
4. [Ejercicio 4](#Ejercicio-4)
5. [Ejercicio 5](#Ejercicio-5)
6. [Ejercicio 6](#Ejercicio-6)
7. [Keycloak Export](#Keycloak-Export)

***

## Ejercicio-1

[Enunciado](https://docs.google.com/document/d/1laU_Mh-e1fmb8xMKlDdbQit_ur5jt50mniWQTp19bB0/edit?usp=sharing)

A partir del [Docker Compose](/docker-compose.yml) se levanta un contenedor con la imagen de Keycloak, dicha instancia queda expuesta en el puerto 8082; y como primer paso, se procede a crear el reino "`DigitalMedia`".
Posteriormente, se procede a realizar la exportación de configuración de Keycloak en un archivo `JSON`; sin embargo; para este punto, dicho archivo está compuesto únicamente por el nombre del reino creado y las configuraciones que vienen por defecto en su creación.

- *Keycloak*: http://localhost:8082/
    - Username: *admin*
    - Password: *admin*

**P.D.** La instancia de Keycloak cuenta con su respectiva Base de Datos, que corresponde a un contenedor de la imagen `postgres`.

## Ejercicio-2

[Enunciado](https://docs.google.com/document/d/1ONc1LwF2qFb4iSzCBvC6l1hR4U2tCQqrbAFjKMh2Di8/edit?usp=sharing)

Desde la consola de administración de Keycloak se crean los siguientes clientes:
 - microservicios (acceso confidencial)
 - internal (acceso público)
 - api-gateway (acceso confidencial)

Así mismo, se crean los siguientes grupos:
 - client
 - admin
 - provider

Por otra parte se crea un scope llamado "internal", y un mapper para cada uno de los clientes de tipo "group membership" llamado groups.

Y finalmente, se crean tres usuarios con sus respectivas contraseñas, asociado a cada uno de los grupos creados previamente.

 - users:
    - client (Grupo: 'client')
    - admin (Grupo: 'admin')
    - provider (Grupo: 'provider')

## Ejercicio-3

[Enunciado 1](https://docs.google.com/document/d/1cUOwkp1eLdrEcDxK8RUPKti-ONppsHb_0HFmNSLBuhM/edit?usp=sharing)
[Enunciado 2](https://docs.google.com/document/d/1FI5KU55GCFfNLWcYjvj-I38Ns4t_0P8ShWWK5UNcyYI/edit?usp=sharing)

Teniendo el cuenta el diagrama de este ejercicio, tenemos que: ![Diagrama](/Images/Ejercicio_3.png)

 - Se crea el Microservicio de [Eureka Server](/EurekaServer/) con su respectiva [configuración](/EurekaServer\src\main\resources\application.yml).
    - *Eureka Server*: http://localhost:8761/
 - Se agrega la dependencia de `spring-boot-starter-oauth2-resource-server` en el Microservicio dado de [Películas](/movies-api/). Por lo cual, se agrega el [Paquete de Seguridad](/movies-api\src\main\java\com\digitalmedia\movies\security) que contiene las configuraciones correspondientes a la autenticación y autorización de usuarios. Así que, en su [archivo de configuración](/movies-api/src/main/resources/application.yml) se agregan las propiedades necesarias para que se registre como cliente de Eureka, y a su vez las propiedades de configuración de Keycloak para que utilice las credenciales del cliente "microservicios" previamente creado. Finalmente, está API cuenta con su respectiva Base de Datos, la cual es creada a traves del [Docker Compose](/docker-compose.yml), y en este caso corresponde a un contenedor de MongoDB.
 - Se agrega la dependencia de `spring-boot-starter-oauth2-resource-server` en el Microservicio dado de [Facturación](/ms-bills/). Por lo cual, se agrega el [Paquete de Seguridad](/ms-bills/src/main/java/com/msbills/security/) que contiene las configuraciones correspondientes a la autenticación y autorización de usuarios. Así que, en su [archivo de configuración](/ms-bills\src\main\resources\application.yml) se agregan las propiedades necesarias para que se registre como cliente de Eureka, y a su vez las propiedades de configuración de Keycloak para que utilice las credenciales del cliente "microservicios" previamente creado. Finalmente, está API cuenta con su respectiva Base de Datos, la cual es creada a traves del [Docker Compose](/docker-compose.yml), y en este caso corresponde a un contenedor de MySQL.
    - **P.D.** La API de Facturación en su base venía con la configuración a la base de datos, haciendo uso de H2; sin embargo, con el fin de aprovechar el Docker Compose, realicé el cambio a MySQL, por lo que, así como se crea el contenedor con dicha imagen, se agrega el [archivo de "inicialización"](/init.sql) con la sintaxis para la creación de la tabla e inserción de datos. Se tuvo que realizar un pequeño ajuste a la sintaxis al pasar de H2 a MySQL, siendo el más notable el de cambiar la función "RANDOM_UUID()" por "MD5(UUID())". Cabe destacar que en el [POM](/ms-bills/pom.xml) la dependencia de H2 está comentada.

## Ejercicio-4

[Enunciado 1](https://docs.google.com/document/d/1rqEBtOEJFPjucSVxoCklbl1qp3SUDnDDf9ZtxzoNuvc/edit?usp=sharing)
[Enunciado 2](https://docs.google.com/document/d/1EcD1p2jJIuRnGFmeLFG3C7pQZeIL0ZnphILVQNaPoVA/edit?usp=sharing)

A partir del Microservicio base de [gateway-api](/gateway-api/) se realizan las siguientes modificaciones:
 - Se agrega la dependencia `spring-boot-starter-oauth2-client`.
 - Se agrega el [Paquete de Seguridad](/gateway-api/src/main/java/com/digitalmedia/gateway/Security/), el cual contiene la clase de configuración para la autenticación y autorización de usuarios.
 - Se agregan las propiedades necesarias al [archivo de configuración](/gateway-api/src/main/resources/application.yml), para que:
    - Se registre como cliente de Eureka (aunque evitamos que se muestre en el dashboard de Eureka).
    - A traves de la dependencia de Seguridad haga uso de las credenciales del cliente "api-gateway" previamente creado, así como también sepa la URI del proveedor y la URI de redirección.
    - Haga uso del filtro "TokenRelay".
    - Mapee las URL de nuestros microservicios (routes).
 - *gateway-api*: http://localhost:9090/

**P.D.** A pesar de que el [Diagrama del Ejercicio 3](/Images/Ejercicio_3.png) muestra que los tres microservicios reciben solicitudes a través del Gateway, la route correspondiente al Microservicio de Usuarios está comentada (línea 23), debido a que en un ejercicio posterior se hace uso de Feign entre el Microservicio de Facturación y el Microservicio de Usuarios.

### GBAC

Teniendo en cuenta que queremos restringir el acceso a ciertos recursos de nuestra solución, a partir del mecanismo de control basado en GRUPOS, se crea un método que se encarga de extraer los grupos a los cuales pertenece un usuario. Para esto se debieron realizaron ciertos pasos:

 - En primer lugar, desde la consola de Keycloak se debe habilitar que el grupo al cual pertenece un usuario aparezca en el Token. Esto lo hicimos en el Ejercicio 2 cuando creamos los respectivos mappers. En la consola se ve: ![groups](/Images/groups.png)
 - Se crea un método similar al que teníamos de base para la AUDIENCIA, en este caso llamado `extractGroups` (línea 76), con la única diferencia de que al recorrer el JWT agregamos el prefijo "GROUP_" y además de ello utilizamos la función `replace()` para eliminar cierto símbolo, ya que el valor del arreglo agrega el carácter "/" al nombre del grupo, tal como se ve en la siguiente imagen: ![groups_token](/Images/groups_token.png).
 - Con el método previamente creado, lo invocamos en el método `extractResourceRoles` (línea 29), con el objetivo de obtener la información necesaria del JWT, que posteriormente se va a validar en los diferentes endpoints de nuestros microservicios. Dado que estos métodos hacen parte de la clase [`KeyCloakJwtAuthenticationConverter`](/movies-api/src/main/java/com/digitalmedia/movies/security/KeyCloakJwtAuthenticationConverter.java) del paquete de Seguridad; está modificación se realiza en todos los tres microservicios de nuestra solución.

Ya habiendo configurado la extracción de grupos, podemos hacer uso de ellos en nuestros Controllers. Para el caso de Películas, se establecen los diferentes `@PreAuthorize` en cada método, siguiendo las reglas del enunciado. [*MoviesController*](/movies-api/src/main/java/com/digitalmedia/movies/controller/MoviesController.java)

## Ejercicio-5

[Enunciado 1](https://docs.google.com/document/d/1rWQbWa6qABI2i2ae5nAARV4CM24ZUvMYzuWSunHzCGA/edit?usp=sharing)
[Enunciado 2](https://docs.google.com/document/d/16B-CGlpglVqtzRZeFiMz0eOubgY23SGtxk3hHQTpvzo/edit?usp=sharing)

A partir del Microservicio base de Usuario se realizan las siguientes modificaciones:
 - Se agregan las dependencias de `spring-boot-starter-oauth2-resource-server` y `keycloak-admin-client`.
 - Se agrega igualmente el [Paquete de Seguridad](/users-service/src/main/java/com/digitalmedia/users/security/) tal como se hizo con Películas y Facturación.
 - Se agrega el Paquete de Configuración de Keycloak, con su respectiva clase. [*KeycloakClientConfiguration*](/users-service/src/main/java/com/digitalmedia/users/configuration/KeycloakClientConfiguration.java).
 - En el Paquete de Repositorio, se crea la clase [KeycloakRepository](/users-service/src/main/java/com/digitalmedia/users/repository/KeycloakRepository.java), la cual nos va a permitir haciendo uso de la [Admin REST API](https://www.keycloak.org/docs-api/18.0/rest-api/) de Keycloak gestionar usuarios.
 - En la [Configuración de la Aplicación](/users-service/src/main/resources/application.yml) se establecen las propiedades necesarias para la Seguridad con las credenciales del cliente "microservicios", el registro como cliente de Eureka y la conexión con la Base de Datos, que en este caso tal como con el Microservicio de Películas es con MongoDB.
    - **P.D.** Teniendo en cuenta que `users-service`también hace uso de MongoDB, se realiza una pequeña modificación en la creación del contenedor de MongoDB en el Docker Compose, para que utilice como volumen, un archivo Javascript que contiene la creación de dos bases de datos: una para Películas y otra para Usuarios.
 
Ahora, teniendo en cuenta el enunciado, el cual pide crear un método que liste los usuarios no administradores, se realizó lo siguiente:
 - Se crea un método en el Repositorio de Keycloak, llamado `findUsersNoAdmin` (línea 48). Dicho método consiste en realizar un filtro a la lista de usuarios obtenidos en la línea 50. Este filtro trae aquellos usuarios, cuyo *GRUPO* no coincida con la palabra "***admin***"; adicionalmente, con la lista ya filtrada se realiza un mapeo a un objeto específico (`User`), por lo cual invoca al método que llamé `toNoAdminUser` (línea 63) para que realice la "transformación" y, finalmente retornar ahora sí una lista de tipo User. El método `toNoAdminUser` es un método privado, que recibe como argumento un objeto de tipo `UserRepresentation` y lo retorna como un objeto `User` a través de un constructor compuesto solo de *usuario* y *email*, tal como el enunciado lo solicita.
 - Se crea el método en el [UserController](/users-service\src\main\java\com\digitalmedia\users\controller\UserController.java) llamado `findUsersNoAdmin` (línea 49) el cual valida que dicha solicitud solo pueda ser realizada por usuarios autenticados y que pertenezcan al grupo "**admin**".

Dicho esto, continuando con el segundo enunciado, se nos pide realizar una nueva modificación, está vez para ver los detalles de un usuario dado el *username*.
 - Se crea el método `findByUsername` en el Repositorio de Keycloak (línea 24). Este método consiste en obtener la lista de usuarios de nuestro reino, cuya búsqueda coincida con el *username* que recibe como argumento. Ahora bien, cabe resaltar lo siguiente: el método de búsqueda de Keycloak retorna una LISTA. Sin embargo, aquí se presenta cierta ambiguedad. Este método parece no discriminar el ***Username*** del ***First Name***, y es tal vés por esa razón que retorna una lista con todas la coincidencias. La ambiguedad radica en que sí bien un usuario debe tener un Username único, sí el First Name coincide con el Username buscado, igualmente el método lo va a retonar. Por conveniencia del ejercicio, descarto está hipótesis, y asumo que la lista retornada solo va a contener un elemento (un único usuario coincidente con la búsqueda). Igualmente realizo el mapeo para retornar una lista de tipo `User`.
  - Se crea el método en el [UserController](/users-service\src\main\java\com\digitalmedia\users\controller\UserController.java) llamado `findByUsername` (línea 22) el cual valida que dicha solicitud solo pueda ser realizada por usuarios autenticados y que pertenezcan al grupo "**admin**".
  - **P.D.** Para poder realizar estas acciones, otra de las configuraciones adicionales que se debe realizar es que el usuario ***admin*** tenga los permisos necesarios para la gestión de usuarios. Es por ello, que desde la consola de Keycloak se habilitaron las opciones de: `manage-users`, `query-users`, `view-users`.
  ![admin](/Images/admin.png)


## Ejercicio-6

[Enunciado 1](https://docs.google.com/document/d/1vJSRngP-q4O_cA2mpZVhcnwAmwoaqdOPlPslWpzsQKA/edit?usp=sharing)
[Enunciado 2](https://docs.google.com/document/d/12mwwvpODIX2Q7XxL1JI48vzw2TKQXARtHV9sZD-lBSM/edit?usp=sharing)

Debido a que el servicio de Facturación va a hacer uso del Servicio de Usuarios, se realiza la configuración de Feign con OAuth en *ms-bills*.
Por consiguiente:
 - Se agregan tres dependencias al [POM](/ms-bills/pom.xml): `spring-boot-starter-oauth2-client`, `spring-boot-starter-security` y `spring-cloud-starter-openfeign`.
 - Se agrega el Paquete [Feign](/ms-bills/src/main/java/com/msbills/configuration/feign/) al paquete de Configuración, que contiene las clases de configuración. En este caso, vamos a reenviar el token que llega desde el gateway, por lo que se crea la clase [FeignInterceptor](/ms-bills/src/main/java/com/msbills/configuration/feign/FeignInterceptor.java).
 - En la [configuración de la aplicación](/ms-bills\src\main\resources\application.yml), especifico la URI del Token de nuestro proveedor Keycloak (línea 24).

Por ende, ya configurado Feign, se hace uso de él. De acuerdo al Enunciado 2 debemos permitir que los usuarios *cliente* visualicen sus facturas. Así que, teniendo esto en cuenta se realizaron las siguientes modificaciones:
 - Previamente, se había definido un método que buscaba a Usuarios por **username**. Sin embargo, esta solicitud solo la pueden realizar usuarios del grupo "**admin**". Es por ello, que recurro a la idea de crear un método "público" que me retorne la misma información, pero solo con aquellos datos que me pide el enunciado.
 Entonces, lo primero fue crear un método en el [Repositorio de Keycloak](/users-service/src/main/java/com/digitalmedia/users/repository/KeycloakRepository.java) (users-service) llamado `findByUsernamePublic` (línea 30). Este método en lo único que difiere es en su nombre, y que además retorna un objeto User, pero su mapeo acude al método `toPublicUser`, en el que el constructor está compuesto solo de los atributos que me interesa mostrar de acuerdo al enunciado.
 En segundo lugar, en el [UserController](/users-service\src\main\java\com\digitalmedia\users\controller\UserController.java), creo un método `findByUsernamePublic` el cual no tiene ningun tipo de Autorización, pero que sí requiere de Autenticación debido a la configuración de seguridad.
 **P.D.** Este método retorna una Lista de tipo `User`.
 - Ahora bien, ya teniendo definido el método "público" en el microservicio de Usuarios, se procede con las configuraciones adicionales de Feign.
    - Creo una clase DTO llamada [BillUserResponse](/ms-bills\src\main\java\com\msbills\models\BillUserResponse.java). Está clase contiene la estructura que deseo mostrar cuando el cliente solicite ver sus facturas.
    - Defino la interfaz de Feign [FeignRepository](/ms-bills\src\main\java\com\msbills\repositories\FeignRepository.java). Aquí estoy indicando que el cliente Feign ("users-service") trae como resultado una Lista de tipo User, que corresponde a la clase anidada definida previamente en `BillUserResponse`. Además se indica la configuración a usar, que corresponde al [FeignInterceptor](/ms-bills/src/main/java/com/msbills/configuration/feign/FeignInterceptor.java) previamente creado.
    - Inyecto la interfaz de Feign en el [BillService](/ms-bills\src\main\java\com\msbills\service\BillService.java) y creo el método `detailBill`. Este método retorna el objeto de tipo BillUserResponse.
    Siguiendo la lógica de que el método de Feign solo me va a trar un elemento -un único usuario-, el siguiente paso es obtener el nombre del usuario para saber si dicho usuario posee facturas. En caso de que dicha búsqueda no returné `null`, creo mi objeto de tipo `BillUserResponse`, a partir de la información extraída tanto por Feign como por el método interno de buscar factura por cliente.
    - Por último, en [BillController](/ms-bills\src\main\java\com\msbills\controller\BillController.java), está el método `detailBill` (línea 48), que hace uso de la lógica previamente definida en el Service. Igualmente que con *users-service*, este método no tiene ningun tipo de Autorización, pero sí requiere de Autenticación debido a la configuración de seguridad.
 - En conclusión, en el navegador obtenemos la siguiente respuesta: ![Respuesta](/Images/BillUserResponse.png)

## Keycloak-Export

En conclusión, habiendo realizado los ejercicios propuestos, se realiza la exportación para generar el archivo JSON desde la consola de Keycloak. Sin embargo; hay un obstáculo y es que Keycloak no exporta los usuarios creados. Así que, para intentar "remediar" está situación, planteo las siguientes alternativas:
> [***JSON***](/realm-export.json)

 - Podemos guardar los usuarios "manualmente" a través de Postman, enviando un petición POST al endpoint del [UserController](/users-service\src\main\java\com\digitalmedia\users\controller\UserController.java) que está en el método creado para este fin, llamado `saveUser` (línea 58). Este método consiste en recibir en el cuerpo un objeto de tipo `User`, y a traves del repositorio de Mongo almacenarlo en la Base de Datos. En la colección de Postman, esta petición es llamada "Save MongoUser". Ahora bien, esto no resuelve en sí nuestro problema, ya que solo estamos guardando los usuarios en la Base de Datos de users-service; más no lo estamos persistiendo en Keycloak.
 - Es por ello, que se me ocurrió hacer uso del Repositorio de Keycloak. Es decir, hacer uso de las funciones que nos provee la Admin REST API. En el [KeycloakRepository](/users-service/src/main/java/com/digitalmedia/users/repository/KeycloakRepository.java) creé un método llamado `createUser` (línea 26) que recibe como argumento un objetivo de tipo User, el cual posteriormente voy a solicitar como cuerpo en el Controller. Este método setea los atributos de User en un objeto de tipo UserRepresentation, y posteriormente setea una contraseña -en este caso, todo usuario creado siempre va a tener la misma contraseña ("digitalmedia"), para finalmente crear el usuario en nuestro reino de Keycloak. Ya desde Postman, realizamos la solicitud POST al endpoint que definí en el [UserController](/users-service\src\main\java\com\digitalmedia\users\controller\UserController.java) (línea 72); si obtenemos como respuesta un código 201, significa que nuestro usuario se creo correctamente y lo podemos ya visualizar desde la consola de Keycloak.
   - **P.D.** Al crear usuarios desde el repositorio de Keycloak, no le estamos asociando un grupo; por lo cual, estando ya en la consola de Keycloak, podemos asignarlo.