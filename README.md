# Cab Booking System

A scalable, reactive ride-hailing backend system built with Spring Boot + WebFlux.

## Architecture Overview

![Architecture](docs/architecture.png)

## Microservices

- **API Gateway**: Entry point, handles authentication and routing
- **Auth Service**: User authentication and authorization
- **Rider Service**: Rider management and ride requests
- **Driver Service**: Driver management and location updates
- **Trip Service**: Trip management and tracking
- **Notification Service**: Handles all system notifications
- **Analytics Service**: Reporting and data analysis

## Tech Stack

- **Framework**: Spring Boot 3.x + WebFlux (Reactive)
- **Database**: PostgreSQL
- **Cache**: Redis
- **Message Broker**: Apache Kafka
- **Security**: JWT + Spring Security
- **Documentation**: OpenAPI (Swagger)
- **Monitoring**: Prometheus + Grafana
- **Tracing**: OpenTelemetry
- **Containerization**: Docker + Kubernetes

## Prerequisites

- Java 17+
- Maven 3.8+
- Docker & Docker Compose
- Kubernetes (for production deployment)
- PostgreSQL 15+
- Redis 7+
- Apache Kafka 3+

## Quick Start

1. Clone the repository
```bash
git clone https://github.com/Dhruti313/cab-booking-system.git
cd cab-booking-system
```

2. Start infrastructure services
```bash
docker-compose -f infrastructure/docker/docker-compose.yml up -d
```

3. Build all services
```bash
./mvnw clean install
```

4. Run services locally
```bash
./mvnw spring-boot:run -pl rider-service
./mvnw spring-boot:run -pl driver-service
./mvnw spring-boot:run -pl trip-service
```

## Development

### Code Style
- Follow [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- Use Lombok for reducing boilerplate
- Write unit tests for all business logic
- Document public APIs using OpenAPI annotations

### Branch Strategy
- `main`: Production-ready code
- `develop`: Development branch
- `feature/*`: New features
- `bugfix/*`: Bug fixes
- `release/*`: Release preparation

### Commit Guidelines
Follow [Conventional Commits](https://www.conventionalcommits.org/):
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation
- `style`: Formatting
- `refactor`: Code restructuring
- `test`: Adding tests
- `chore`: Maintenance

## API Documentation

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI Spec: `http://localhost:8080/v3/api-docs`

## Deployment

### Local Development
```bash
docker-compose up -d
```

### Kubernetes
```bash
kubectl apply -f infrastructure/k8s/
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'feat: add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
