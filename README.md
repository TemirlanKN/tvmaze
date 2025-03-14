# TVMaze API Client

A RESTful web service built with Quarkus that integrates with the TVMaze API to manage TV series information and store it in a PostgreSQL database.

## 🚀 Features

- Fetch TV series information from TVMaze API
- Store TV series data in PostgreSQL database
- RESTful API endpoints for CRUD operations
- Swagger/OpenAPI documentation
- Database persistence with Spring JDBC

## 🛠️ Technologies

- **Backend Framework**: Quarkus 2.2.2.Final
- **Language**: Java 11
- **Database**: PostgreSQL
- **API Integration**: REST Client
- **Documentation**: OpenAPI/Swagger
- **Database Access**: Spring JDBC Template
- **Build Tool**: Maven

## 📌 API Endpoints

| Method | Endpoint                   | Description                     |
| ------ | -------------------------- | ------------------------------- |
| GET    | `/tv-maze/movie/show`      | Get movie information by title  |
| GET    | `/tv-maze/series/show`     | Get series information by title |
| GET    | `/tv-maze/database`        | List all stored movies          |
| POST   | `/tv-maze/database/insert` | Add new movie to database       |
| DELETE | `/tv-maze/database`        | Delete movie by title           |

## 🚦 Prerequisites

- JDK 11+
- Maven 3.8+
- PostgreSQL
- Docker (optional)

## ⚙️ Configuration

### Database Configuration

```properties
org.gs1.proxy.TvSeriesProxy/mp-rest/uri=http://api.tvmaze.com/
org.gs1.proxy.TvSeriesProxy/mp-rest/scope=javax.inject.Singleton

SqlUsername=postgres
SqlPassword=12345
SqlConnectionUrl=jdbc:postgresql://localhost:5432/postgres
```

## 🏃‍♂️ Running the Application

### Development Mode

```bash
./mvnw compile quarkus:dev
```

Access Dev UI at: http://localhost:8080/q/dev/

### Production Mode

```bash
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar
```

### Native Executable

```bash
./mvnw package -Pnative
```

## 🐳 Docker Support

Build native image:

```bash
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

## 📚 Project Structure

```
src/
├── main/
    ├── java/org/gs1/
    │   ├── model/
    │   │   ├── Episode.java
    │   │   └── TvSerie.java
    │   ├── proxy/
    │   │   ├── EpisodeProxy.java
    │   │   └── TvSeriesProxy.java
    │   └── TvSeriesResource.java
    └── resources/
        ├── application.properties
        └── META-INF/resources/
            └── index.html
```

## 🧪 Testing

Run tests:

```bash
./mvnw test
```

## 📖 API Documentation

Access Swagger UI at: http://localhost:8080/q/swagger-ui/

## 👥 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit changes
4. Push to branch
5. Create Pull Request

## 📄 License

[Add License Information]

## 📬 Contact

[Add Contact Information]
