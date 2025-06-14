# GRUPO N°7

Repositorio conformado por: **Gabriel Vidal**.

Dentro de este repositorio se podra encontrar todo lo referido a las clases de `FullStack I`

### Spring Boot API REST

[Codigo para realizar una api rest en SPRING BOOT](bibliotecaduoc/)

### Trabajo Prueba 1

Caso de `ConnectForo SPA`
[Proyecto para la Primera Prueba](ConnectForo/)

# ConnectForo - Plataforma de Foros

## Descripción

ConnectForo es una plataforma de foros desarrollada utilizando una arquitectura de microservicios. El proyecto está diseñado para proporcionar una experiencia robusta y escalable para la gestión de foros, usuarios, categorías y contenido.

## Microservicios

El proyecto está compuesto por los siguientes microservicios:

- **microservicio-registro**: Gestión del registro de nuevos usuarios
- **microservicio-login**: Autenticación y gestión de sesiones
- **microservicio-roles**: Administración de roles y permisos
- **microservicio-usuarios**: Gestión de usuarios y perfiles
- **microservicio-soporte**: Sistema de soporte y tickets
- **microservicio-reportes**: Generación de reportes y estadísticas
- **microservicio-post**: Gestión de publicaciones
- **microservicio-interacciones**: Manejo de interacciones (likes, compartir, etc.)
- **microservicio-foros**: Administración de foros y sus contenidos
- **microservicio-comentarios**: Gestión de comentarios
- **microservicio-categorias**: Organización de categorías
- **microservicio-anuncios**: Sistema de anuncios y publicidad

## Tecnologías

- Java
- Spring Boot
- MySQL

## Requisitos

- Java 17 o superior
- Maven
- MySQL 8.0 o superior
- Docker (opcional)

## Configuración

Cada microservicio tiene su propia configuración en su respectivo directorio. Los archivos de configuración se encuentran en:

```
microservicios/[nombre-microservicio]/src/main/resources/application.properties
```

## Ejecución

Para ejecutar cada microservicio individualmente:

1. Navegar al directorio del microservicio:

```bash
cd microservicios/[nombre-microservicio]
```

2. Compilar el proyecto:

```bash
mvn clean install
```

3. Ejecutar el microservicio:

```bash
mvn spring-boot:run
```

## Estructura del Proyecto

```
microservicios/
├── microservicio-registro/
├── microservicio-login/
├── microservicio-roles/
├── microservicio-usuarios/
├── microservicio-soporte/
├── microservicio-reportes/
├── microservicio-post/
├── microservicio-interacciones/
├── microservicio-foros/
├── microservicio-comentarios/
├── microservicio-categorias/
└── microservicio-anuncios/
```
