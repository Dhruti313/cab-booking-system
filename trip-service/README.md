# Trip Service

This microservice handles all trip-related operations in the cab booking system.

## Features

- Trip creation and management
- Real-time trip tracking
- Fare calculation
- Route optimization
- Trip history and analytics
- Rating system integration

## Tech Stack

- Spring Boot WebFlux
- PostgreSQL
- Redis (for active trip tracking)
- Kafka (for event streaming)
- OpenAPI (Swagger) for documentation

## API Endpoints

### Trip Management
- `POST /api/v1/trips` - Create new trip
- `GET /api/v1/trips/{id}` - Get trip details
- `PUT /api/v1/trips/{id}/start` - Start trip
- `PUT /api/v1/trips/{id}/complete` - Complete trip
- `PUT /api/v1/trips/{id}/cancel` - Cancel trip

### Trip Tracking
- `GET /api/v1/trips/{id}/location` - Get current location (WebSocket)
- `GET /api/v1/trips/{id}/eta` - Get estimated arrival time
- `GET /api/v1/trips/active` - List active trips

### Trip History
- `GET /api/v1/trips/history/rider/{riderId}` - Get rider's trip history
- `GET /api/v1/trips/history/driver/{driverId}` - Get driver's trip history

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
CREATE TABLE trips (
    id BIGSERIAL PRIMARY KEY,
    rider_id BIGINT NOT NULL,
    driver_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL,
    pickup_location POINT NOT NULL,
    dropoff_location POINT NOT NULL,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    distance DECIMAL(10,2),
    fare DECIMAL(10,2),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE trip_waypoints (
    id BIGSERIAL PRIMARY KEY,
    trip_id BIGINT REFERENCES trips(id),
    location POINT NOT NULL,
    timestamp TIMESTAMP NOT NULL
);

CREATE TABLE trip_ratings (
    id BIGSERIAL PRIMARY KEY,
    trip_id BIGINT REFERENCES trips(id),
    rider_rating INTEGER,
    driver_rating INTEGER,
    rider_feedback TEXT,
    driver_feedback TEXT,
    created_at TIMESTAMP NOT NULL
);
```

## Event Topics

### Produces
- `trip.created`
- `trip.started`
- `trip.completed`
- `trip.cancelled`
- `trip.location`

### Consumes
- `driver.location`
- `rider.location`
- `payment.completed`

## Metrics

The service exposes the following metrics at `/actuator/prometheus`:
- `active_trips_gauge`
- `trip_duration_histogram`
- `trip_distance_histogram`
- `trip_fare_histogram`
- `trip_cancellation_ratio`
- `trip_completion_total`
