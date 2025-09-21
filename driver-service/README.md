# Driver Service

This microservice handles all driver-related operations in the cab booking system.

## Features

- Driver registration and profile management
- Vehicle management
- Real-time location tracking
- Ride acceptance and completion
- Driver earnings and analytics
- Rating system

## Tech Stack

- Spring Boot WebFlux
- PostgreSQL
- Redis (for location tracking and caching)
- Kafka (for event streaming)
- OpenAPI (Swagger) for documentation

## API Endpoints

### Driver Management
- `POST /api/v1/drivers` - Register new driver
- `GET /api/v1/drivers/{id}` - Get driver profile
- `PUT /api/v1/drivers/{id}` - Update driver profile
- `DELETE /api/v1/drivers/{id}` - Deactivate driver account

### Vehicle Management
- `POST /api/v1/drivers/{id}/vehicles` - Add vehicle
- `PUT /api/v1/drivers/{id}/vehicles/{vehicleId}` - Update vehicle
- `GET /api/v1/drivers/{id}/vehicles` - List vehicles

### Location & Availability
- `POST /api/v1/drivers/{id}/location` - Update location
- `PUT /api/v1/drivers/{id}/availability` - Update availability
- `GET /api/v1/drivers/nearby` - Find nearby drivers

## Setup

1. Environment Variables
```properties
SPRING_PROFILES_ACTIVE=dev
DB_HOST=localhost
DB_PORT=5432
DB_NAME=cabapp
DB_USERNAME=cabapp
DB_PASSWORD=cabapp123
REDIS_HOST=localhost
REDIS_PORT=6379
KAFKA_BOOTSTRAP_SERVERS=localhost:9092
```

2. Build
```bash
mvn clean install
```

3. Run
```bash
mvn spring-boot:run
```

## Testing

```bash
# Unit tests
mvn test

# Integration tests
mvn verify
```

## Database Schema

```sql
CREATE TABLE drivers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20) UNIQUE NOT NULL,
    status VARCHAR(20) NOT NULL,
    rating DECIMAL(3,2),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE vehicles (
    id BIGSERIAL PRIMARY KEY,
    driver_id BIGINT REFERENCES drivers(id),
    type VARCHAR(50) NOT NULL,
    make VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    year INTEGER NOT NULL,
    plate_number VARCHAR(20) UNIQUE NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE driver_locations (
    driver_id BIGINT PRIMARY KEY REFERENCES drivers(id),
    location POINT NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
```

## Event Topics

### Produces
- `driver.location`
- `driver.status`
- `ride.accepted`
- `ride.completed`

### Consumes
- `ride.requested`
- `ride.cancelled`

## Metrics

The service exposes the following metrics at `/actuator/prometheus`:
- `active_drivers_gauge`
- `driver_earnings_total`
- `ride_acceptance_ratio`
- `driver_rating_histogram`
- `driver_location_updates_total`
