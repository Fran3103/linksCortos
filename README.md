# Links Cortos - API

**Links Cortos** es un servicio REST que genera URLs cortas a partir de URLs largas con una sola petición HTTP. Ideal para compartir enlaces en redes, correos o integrarlo en tus propias aplicaciones.

---

## 🔧 Tecnologías

* **Java 17**
* **Spring Boot**
* **Spring Web**
* **Spring Data JPA**
* **H2 (desarrollo)** / **PostgreSQL, MySQL, etc. (producción)**
* **Maven**

---

## ⚙️ Configuración

1. Clona el repositorio:

   ```bash
   git clone https://github.com/Fran3103/linksCortos-Backend.git
   cd linksCortos-Backend
   ```

2. Define la URL base en tu `application.properties` o en tus perfiles:

   ```properties
   # application-local.properties
   spring.profiles.active=local
   app.base-url=http://localhost:8080

   spring.datasource.url=jdbc:h2:mem:acortalinks;DB_CLOSE_DELAY=-1
   spring.datasource.driver-class-name=org.h2.Driver
   spring.datasource.username=sa
   spring.datasource.password=
   spring.jpa.hibernate.ddl-auto=update
   ```

   ```properties
   # application-prod.properties
   app.base-url=https://linkscortos-1.onrender.com

   # Configura tu base de datos productiva aquí
   spring.datasource.url=jdbc:postgresql://...:5432/links
   spring.datasource.username=usuario
   spring.datasource.password=contraseña
   spring.jpa.hibernate.ddl-auto=validate
   ```

3. Ejecuta el servicio:

   ```bash
   mvn clean package
   mvn spring-boot:run -Dspring.profiles.active=local
   ```

---

## 🚀 Endpoints

### POST `/api/shorten`

Genera un enlace corto.

* **URL**: `/api/shorten`

* **Method**: `POST`

* **Headers**:

  * `Content-Type: application/json`

* **Body**:

  ```json
  {
    "url": "https://ejemplo.com/enlace-largo"
  }
  ```

* **Respuesta (200 OK)**:

  ```json
  {
    "id": 42,
    "urlOriginal": "https://ejemplo.com/enlace-largo",
    "code": "hG12Sa",
    "localDateTime": "2025-05-16T12:00:00",
    "clickCount": 0,
    "urlShort": "https://linkscortos-1.onrender.com/hG12Sa"
  }
  ```

### GET `/api/{code}`

Redirige al enlace original e incrementa el contador de clics.

* **URL**: `/api/{code}`

* **Method**: `GET`

* **Path Param**:

  * `code` (string): código alfanumérico generado.

* **Comportamiento**: devuelve un `302 Redirect` a `urlOriginal`.

---

## 📦 Uso con `curl`

```bash
curl -X POST http://localhost:8080/api/shorten \
  -H "Content-Type: application/json" \
  -d '{ "url": "https://openai.com" }'
```

Para probar redirección:

```bash
curl -i http://localhost:8080/api/hG12Sa
```

---

## 📄 Licencia

© 2025 Franco Aguirre · Proyecto con fines educativos y demostrativos.
