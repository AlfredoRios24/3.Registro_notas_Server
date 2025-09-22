📝 Registro de Notas - Server/Backend

Server de la aplicación Register Notes, desarrollada en Spring Boot (Java) y conectada a una base de datos MySQL.
Este proyecto forma parte de una arquitectura Fullstack, junto con un frontend en React + TypeScript.

🚀 Características principales

🔧 API REST construida con Spring Boot

💾 Conexión a MySQL local y despliegue en Render

🐳 Uso de Docker para facilitar la portabilidad y despliegue

📑 Implementación de operaciones CRUD (crear, leer, actualizar, eliminar notas)

✅ Pruebas unitarias e integrales para asegurar la calidad del código

🏗️ Arquitectura del Proyecto
register-notes-backend/ 

├── src/ 

│   ├── main/ 

│   │   ├── java/com/example/notes/   # Código fuente principal 

│   │   └── resources/                # Configuración (application.properties, etc.) 

│   └── test/                         # Pruebas unitarias e integrales 

├── Dockerfile                        # Configuración para Docker 

├── docker-compose.yml                 # Orquestación de servicios 

├── pom.xml                           # Dependencias Maven 

└── README.md                         # Documentación del proyecto 


⚙️ Tecnologías utilizadas

    - Lenguaje: Java 21
    
    - Framework Backend: Spring Boot
    
    - Base de Datos: MySQL (local y en Render)
    
    - Contenerización: Docker & Docker Compose
    
    - Gestión de dependencias: Maven
    
    - Testing: JUnit 5

📌 Objetivo del proyecto

El propósito de este backend es servir como API central para la aplicación Register Notes, ofreciendo endpoints RESTful que permitan:

      - Crear nuevas notas
      
      - Consultar notas existentes
      
      - Editar notas
      
      - Eliminar notas

Además, este proyecto busca consolidar conocimientos clave como:

      - Desarrollo backend con Spring Boot
      
      - Conexión a bases de datos relacionales
      
      - Buenas prácticas con pruebas unitarias e integrales
      
      - Despliegue en entornos cloud (Render)

🚀 Ejecución local con Docker

Clonar el repositorio

git clone https://github.com/tu-usuario/register-notes-backend.git
cd register-notes-backend

Levantar los contenedores con Docker Compose

docker-compose up --build

Acceder a la API en:

http://localhost:8080/api/notes

📂 Proyecto relacionado

👉 El frontend de este proyecto está disponible en: https://github.com/AlfredoRios24/3.Registro_notas_Client
