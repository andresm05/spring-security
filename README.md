## Spring Security Starter Project

Este proyecto tiene como objetivo proporcionar una **estructura base para implementar seguridad con Spring Security versión 3.4.4**, utilizando autenticación basada en **JWT** y **login con Google**. Además, incluye la documentación de la API expuesta mediante **Swagger** para facilitar el desarrollo y pruebas de los endpoints.

### 🔐 Características

*   Autenticación y autorización con **Spring Security 3.4.4**
    
*   Uso de **JSON Web Tokens (JWT)** para la gestión de sesiones
    
*   Inicio de sesión con **cuenta de Google** usando OAuth2
    
*   Documentación interactiva con **Swagger UI**
    
*   Configuración centralizada mediante un archivo .env
    

### 🚀 Tecnologías
--------------

*   Java 21+
    
*   Spring Boot 3.4.4
    
*   Spring Security
    
*   JWT
    
*   Swagger (SpringDoc OpenAPI)
    
*   OAuth2 (Login con Google)
    

### 📁 Estructura del archivo .env

En la raíz del proyecto, se debe crear un archivo .env con el siguiente contenido:
 ```bash
SPRING_DATASOURCE_URL=url_base_de_datos

SPRING_DATASOURCE_USERNAME=tu_usuario

SPRING_DATASOURCE_PASSWORD=tu_contraseña

JWT_SECRET=tu_secreto_jwt

SPRING_USER_GENERATOR=codigo_de_usuario

GOOGLE_CLIENT_ID=tu_client_id_google

GOOGLE_CLIENT_SECRET=tu_client_secret_google
```

### 🔧 Cómo obtener tu Google Client ID y Secret
--------------------------------------------

Para poder autenticar a los usuarios mediante Google, necesitas configurar un proyecto en Google Cloud Console. Sigue estos pasos:

1.  Accede a https://console.cloud.google.com/
    
2.  Crea un nuevo proyecto (o selecciona uno existente).
    
3.  Ve al menú lateral y selecciona **APIs y servicios > Credenciales**.
    
4.  Haz clic en **\+ Crear credenciales > ID de cliente de OAuth**.
    
5.  Selecciona **Aplicación web** como tipo de aplicación.
    
6.  En **URIs autorizados de redireccionamiento**, agrega la siguiente URL (ajústala según tu entorno):
    
 ```bash
http://localhost:8080/login/oauth2/code/google
```

### 🔑 Ruta para iniciar sesión con Google

Para redirigir al usuario al flujo de autenticación de Google, puedes usar el siguiente endpoint:

 ```bash
POST /oauth2/authorization/google
```

#### ⚠️ Reemplaza google si más adelante deseas usar otro proveedor de OAuth2 (como GitHub, Facebook, etc.).

### 📄 Documentación con Swagger

Una vez levantado el servidor, puedes acceder a la documentación de la API en:

 ```bash
http://localhost:8080/swagger-ui.html
```

### ▶️ Ejecución

Asegúrate de tener tu archivo .env listo y luego ejecuta el proyecto con:

 ```bash
./mvnw spring-boot:run
```