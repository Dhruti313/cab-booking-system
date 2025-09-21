# Rider Service

This microservice handles all rider-related operations in the cab booking system.

## Features

- Rider registration and profile management
- Ride request handling
- Ride history and status tracking
- Rider rating system
- Payment integration

## Tech Stack

- Spring Boot WebFlux
- PostgreSQL
- Redis (for caching)
- Kafka (for event streaming)
- OpenAPI (Swagger) for documentation

## API Endpoints

### Rider Management
- `POST /api/v1/riders` - Register new rider
- `GET /api/v1/riders/{id}` - Get rider profile
- `PUT /api/v1/riders/{id}` - Update rider profile
- `DELETE /api/v1/riders/{id}` - Delete rider account

### Ride Operations
- `POST /api/v1/rides` - Request new ride
- `GET /api/v1/rides/{id}` - Get ride details
- `PUT /api/v1/rides/{id}/cancel` - Cancel ride
- `GET /api/v1/rides/{id}/track` - Track ride (WebSocket)

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
CREATE TABLE riders (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20) UNIQUE NOT NULL,
    status VARCHAR(20) NOT NULL,
    rating DECIMAL(3,2),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE rides (
    id BIGSERIAL PRIMARY KEY,
    rider_id BIGINT REFERENCES riders(id),
    driver_id BIGINT,
    status VARCHAR(20) NOT NULL,
    pickup_location POINT NOT NULL,
    dropoff_location POINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
```

## Event Topics

### Produces
- `ride.requested`
- `ride.cancelled`
- `rider.location`

### Consumes
- `ride.assigned`
- `ride.completed`
- `driver.location`

## Metrics

The service exposes the following metrics at `/actuator/prometheus`:
- `ride_requests_total`
- `active_rides_gauge`
- `ride_duration_seconds`
- `rider_rating_histogram`
