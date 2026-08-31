# 🎬 API Movies

> Cada película cuenta una historia... esta API las guarda todas.

**API REST** construida con **Spring Boot** para gestionar un catálogo de películas, con búsqueda por título o género y un modelo de datos relacional que conecta cada película con su género, su reparto y su año de estreno. Desarrollada en **Java 21** con **Spring Data JPA**, y verificada siguiendo **TDD** con **JUnit 5, Mockito, Hamcrest**, con cobertura de tests medida con **JaCoCo**.

---

## 📑 Índice

- [Descripción](#-descripción)
- [Tecnologías](#-tecnologías)
- [Autora](#-autora)

---

## 📋 Descripción

**API Movies** es una API REST para gestionar un catálogo de películas: permite consultar, crear, actualizar y eliminar películas, además de buscarlas por título o género, siguiendo una arquitectura por capas (Controller → Service → Repository → Entity). 

Expone seis endpoints sobre el recurso `Movie`:

- **Obtener todas las películas** — lista completa del catálogo
- **Obtener una película por su id** — detalle de una película concreta
- **Añadir una película** — alta de un nuevo registro
- **Actualizar una película** — modificación de una película existente
- **Eliminar una película** — baja de una película
- **Buscar películas por título o género** — endpoint adicional de consulta (`findBy`)

Cada película está relacionada con tres entidades propias:

- **Género** — relación de muchos a muchos (N:M): una película puede tener varios géneros, y un género puede pertenecer a varias películas
- **Actor** — relación de muchos a muchos (N:M), una película puede tener varios actores, y un actor puede aparecer en varias películas
- **Año de estreno** — relación de muchos a uno (N:1): cada película pertenece a un único año, y un año puede agrupar varias películas

Género, actor y año exponen también su propio conjunto de endpoints (listar todas, obtener por id, crear y actualizar); además de servir como catálogo de referencia para construir y relacionar películas, son recursos totalmente gestionables por sí mismos.

El enunciado no especifica el tipo exacto de cada relación, así que lo definí a partir de cómo funciona en la vida real: género y actor son N:M (una película puede tener varios géneros/actores, y un género/actor puede aparecer en varias películas), resueltas con tablas intermedias (`movies_genres`, `movies_actors`); año de estreno es N:1 (cada película tiene un único año), con una relación `@ManyToOne` simple.

La petición para crear o actualizar una película recibe listas de ids de género y actor, y el id del año, en vez de objetos completos — el cliente solo manda referencias, y el servicio las resuelve contra sus repositorios antes de guardar. La respuesta sí devuelve los datos ya resueltos (nombres de género, año, actores) para que sea legible sin peticiones adicionales.

`Genre`, `Actor` y `ReleaseYear` no tienen DELETE ni un endpoint de búsqueda propio, así que los diseñé para `Movie` siguiendo las convenciones REST/Spring oficiales: el DELETE devuelve `204 No Content` y `404` si la película no existe; la búsqueda (`/movies/search?title=&genre=`) acepta ambos parámetros como opcionales.

[Volver al índice](#-índice)

---

## 🛠️ Tecnologías

- **[Java 21](https://www.oracle.com/java/technologies/downloads/)** — Lenguaje de programación del proyecto
- **[Spring Boot](https://spring.io/projects/spring-boot)** — Framework para construir la API REST
- **[Spring Web](https://docs.spring.io/spring-framework/reference/web.html)** — Exposición de los endpoints HTTP
- **[Spring Data JPA](https://spring.io/projects/spring-data-jpa)** — Acceso a datos y mapeo objeto-relacional
- **[H2 Database](https://www.h2database.com/)** — Base de datos en memoria para desarrollo y test
- **[Bean Validation](https://beanvalidation.org/)** — Validación de los datos de entrada
- **[Apache Maven](https://maven.apache.org/)** — Gestor de dependencias y construcción del proyecto
- **[JUnit 5](https://junit.org/junit5/)** — Framework de tests unitarios
- **[Mockito](https://site.mockito.org/)** — Mocks para los tests de servicio
- **[Hamcrest](https://hamcrest.org/JavaHamcrest/)** — Librería de matchers para aserciones legibles
- **[JaCoCo](https://www.jacoco.org/jacoco/)** — Medición de la cobertura de tests
- **[Git](https://git-scm.com/)** / **[GitHub](https://github.com/)** — Control de versiones y alojamiento del proyecto

---

## 👩‍💻 Autora

**[Jenny Sánchez Requejo](https://github.com/Jennydev-25)**

[Volver arriba](#-api-movies)
