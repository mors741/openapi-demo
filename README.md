# OpenAPI Demo

Demo project: code generation from OpenAPI (Java 17, Gradle, Spring Boot 3).

## Requirements

- JDK 17+
- (optional) Gradle — for command line; IDEA uses the built-in Gradle.

To build from the command line with a specific JDK: copy `gradle.properties.example` to `gradle.properties` and set `org.gradle.java.home=/path/to/jdk`.

## Structure

- `api/openapi.yaml` — OpenAPI 3.0 specification
- Generated code goes to `build/generated/src/main/java` (packages `ru.yandex.demo.api`, `ru.yandex.demo.model`)
- API implementation: `src/main/java/ru/yandex/demo/web/ItemsApiController.java`

## Build and run

```bash
# Generate code from OpenAPI (runs automatically on compileJava)
./gradlew openApiGenerate

# Build
./gradlew build

# Run the application
./gradlew bootRun
```

The application runs on port **8080**.

## Example requests

```bash
# List items
curl -s http://localhost:8080/items

# Create item
curl -s -X POST http://localhost:8080/items -H "Content-Type: application/json" -d '{"name":"Test"}'

# Get by ID
curl -s http://localhost:8080/items/1
```

### Pets (Cat / Dog)

`type` лежит внутри сущностей.

```bash
# List pets
curl -s http://localhost:8080/pets

# Save a cat
curl -s -X POST http://localhost:8080/pets \
  -H "Content-Type: application/json" \
  -d '{
  "type": "cat",
  "name": "Murzik",
  "whiskersLength": 5.2,
  "indoor": true
}'

# Save a dog
curl -s -X POST http://localhost:8080/pets \
  -H "Content-Type: application/json" \
  -d '{
  "type": "dog",
  "name": "Sharik",
  "breed": "Labrador",
  "barkVolume": "medium"
}'
```

### Shapes (Circle / Square)

`type` лежит снаружи сущностей. Сами сущности лежат в общем поле `data`.

```bash
# List shapes
curl -s http://localhost:8080/shapes

# Create a circle
curl -s -X POST http://localhost:8080/shapes \
  -H "Content-Type: application/json" \
  -d '{
  "type": "circle",
  "data": {
    "shapeType": "circle",
    "name": "Sun",
    "radius": 1.5
  }
}'

# Create a square
curl -s -X POST http://localhost:8080/shapes \
  -H "Content-Type: application/json" \
  -d '{
  "type": "square",
  "data": {
    "shapeType": "square",
    "name": "Window",
    "side": 2.0
  }
}'
```

### Vehicles (Car / Bike)

Вложенные сущности передаются в отдельных полях: `car` для машины, `bike` для велосипеда.

```bash
# List vehicles
curl -s http://localhost:8080/vehicles

# Create a car
curl -s -X POST http://localhost:8080/vehicles \
  -H "Content-Type: application/json" \
  -d '{
  "type": "car",
  "car": {
    "name": "Toyota Camry",
    "brand": "Toyota",
    "horsepower": 181
  }
}'

# Create a bike
curl -s -X POST http://localhost:8080/vehicles \
  -H "Content-Type: application/json" \
  -d '{
  "type": "bike",
  "bike": {
    "name": "Mountain Pro",
    "gears": 21,
    "electric": false
  }
}'
```

### Fruits (Apple / Banana)

Одна верхнеуровневая сущность `Fruit` с полем `type` и двумя опциональными полями `apple` и `banana` (без oneOf).

```bash
# List fruits
curl -s http://localhost:8080/fruits

# Create an apple
curl -s -X POST http://localhost:8080/fruits \
  -H "Content-Type: application/json" \
  -d '{
  "type": "apple",
  "apple": {
    "name": "Granny Smith",
    "color": "green",
    "weightGrams": 180
  }
}'

# Create a banana
curl -s -X POST http://localhost:8080/fruits \
  -H "Content-Type: application/json" \
  -d '{
  "type": "banana",
  "banana": {
    "name": "Cavendish",
    "lengthCm": 18.5,
    "ripe": true
  }
}'
```

## Changing the API

1. Edit `api/openapi.yaml`.
2. Run `./gradlew openApiGenerate`.
3. Update the implementation in `ru.yandex.demo.web` to match the new interfaces/models.
