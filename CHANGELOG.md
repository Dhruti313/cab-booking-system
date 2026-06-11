# Changelog

All notable changes to the Cab Booking System project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2026-06-11

### Added
- Initial release of the Cab Booking System microservices architecture
- **Rider Service**: Rider management, registration, and ride request handling
  - Rider registration and profile management
  - Route calculation using GraphHopper routing engine
  - RESTful API endpoints for rider operations
  - Comprehensive error handling
- **Driver Service**: Driver management and location tracking capabilities
- **Trip Service**: Trip management and lifecycle tracking
- **Infrastructure**: Complete containerization setup
  - Docker Compose for local development
  - Kubernetes manifests for production deployment
  - Helm charts for simplified K8s deployments
  - Prometheus configuration for monitoring
- **CI/CD Pipeline**: Automated build, test, and verification workflows
  - Maven-based builds with Java 17
  - SonarQube integration for code quality analysis
  - Automated test coverage reporting
  - Pull request validation
- **Tech Stack**:
  - Spring Boot 3.2.2 with WebFlux for reactive programming
  - PostgreSQL for persistent storage
  - Redis for caching
  - Apache Kafka for event streaming
  - OpenTelemetry for distributed tracing
  - Prometheus + Grafana for monitoring
  - JWT-based security
- **Documentation**:
  - Comprehensive README with architecture overview
  - API documentation via Swagger/OpenAPI
  - Development guidelines and branch strategy
  - Prerequisites and quick start guide
- **Code Quality**:
  - Unit test coverage with JUnit 5
  - Code coverage tracking via JaCoCo
  - Static analysis via SonarQube
  - Conventional Commits convention

### Features
- Microservices architecture with API Gateway pattern
- Reactive and non-blocking request handling
- Distributed tracing and monitoring capabilities
- Containerized deployment options
- Cloud-native design patterns
- Horizontal scalability support

### Technical Details
- Java 17+ runtime requirement
- Maven 3.8+ for dependency management
- Spring Cloud for distributed systems patterns
- Spring Data JPA for database access
- GraphHopper 3.0 for advanced routing

### Known Issues
- Trip Service and Driver Service are placeholders with minimal implementation
- Auth Service Kubernetes manifest included but service not fully implemented
- Analytics Service mentioned in architecture but not yet implemented

---

## Future Releases

### [1.1.0] - Planned
- Enhanced driver location tracking
- Real-time trip updates via WebSocket
- Advanced analytics dashboard
- Payment service integration
- SMS/Email notification service implementation

### [2.0.0] - Planned
- Multi-region deployment support
- Advanced load balancing
- Disaster recovery mechanisms
- Complete Auth Service implementation
- Analytics Service full implementation
