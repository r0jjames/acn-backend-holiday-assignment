# Holiday Microservice

This project is a Spring Boot application that retrieves holiday information from the Nager.Date API.

## Requirements

- Java 17 or higher
- Maven

## How to Run

1. Clone the repository:
    ```bash
    git clone https://github.com/r0jjames/acn-backend-holiday-assignment.git
    cd acn-backend-holiday-assignment/holiday
    ```

2. Build the project:
    ```bash
    ./mvnw clean install
    ```

3. Run the application:
    ```bash
    ./mvnw spring-boot:run
    ```
4. The application uses OpenAPI (Swagger) for API documentation. Once the application is running, you can access the documentation UI at: http://localhost:8080/swagger-ui.html

## API Endpoints

- `GET /api/holidays/{countryCode}/last3` - Get the last 3 celebrated holidays for a given country.
- `GET /api/holidays/{year}/count` - Get the number of public holidays not falling on weekends for given country codes.
- `GET /api/holidays/{year}/common` - Get the common holidays for two countries in a given year.



