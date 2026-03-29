# Oasis Application

## Descripción
Proyecto Oasis. Asignatura Ingenieria del software 2. Este proyecto es una aplicación de alquileres en que permite registrarse a los usuarios como inquilinos o propietarios. Los propietarios pueden dar de alta inmuebles para ser alquilados. Los inquilinos pueden buscar alojamientos, pudiendo aplicar filtros avanzados, y posteriormente reservar propiedades completando el pago en la aplicación.

## Estructura del Proyecto

├───src
│   ├───main
│   │   ├───java
│   │   │   └───es
│   │   │       └───uclm
│   │   │           └───OasisProject
│   │   │               ├───domain
│   │   │               │   ├───controllers
│   │   │               │   └───entities
│   │   │               ├───persistence
│   │   │               └───presentation
│   │   └───resources
│   │       ├───static
│   │       │   └───css
│   │       └───templates
│   └───test
│       └───java
│           └───es
│               └───uclm
│                   └───OasisProject

## Instalación
1. Clona el repositorio:
```sh
git clone https://github.com/Carloslc17/Oasis.git
```

2. Navega al directorio del proyecto:
```sh
cd Oasis
```

3. Construye el proyecto con Maven:
```sh
mvn clean install
```

## Ejecución

Para ejecutar la aplicación, usa el siguiente comando:
```sh
mvn spring-boot:run
```

## Pruebas

Para ejecutar las pruebas, usa el siguiente comando:
```sh
mvn test
```

## Funcionalidades

### Propietarios

- Registro y autenticación.
- Alta de inmuebles para alquiler.
- Gestión de las propiedades publicadas.

### Inquilinos

- Registro y autenticación.
- Búsqueda de alojamientos con filtros avanzados.
- Reserva de propiedades.
- Pago integrado en la aplicación.

## Tecnologías Utilizadas

- Java 21
- Spring Boot
- Maven
- Apache Derby
- Spring Data JPA
- Thymeleaf
- CSS

## Contribuciones

Las contribuciones son bienvenidas. Por favor, abre un issue o envía un pull request para discutir cualquier cambio que desees realizar.

## Licencia

Este proyecto es un trabajo universitario y no debe ser copiado, modificado, distribuido o utilizado de ninguna manera sin el permiso expreso de los autores. El uso no autorizado de este código puede resultar en acciones legales.

Para obtener permiso para usar este código, por favor contacta a los autores.
