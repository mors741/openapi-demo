# OpenAPI Demo

Демо-проект: кодогенерация из OpenAPI (Java 17, Gradle, Spring Boot 3).

## Требования

- JDK 17+
- (опционально) Gradle — для командной строки; IDEA использует встроенный Gradle.

Для сборки из командной строки с выбранным JDK: скопируйте `gradle.properties.example` в `gradle.properties` и укажите `org.gradle.java.home=/path/to/jdk`.

## Структура

- `api/openapi.yaml` — спецификация OpenAPI 3.0
- Код генерируется в `build/generated/src/main/java` (пакеты `ru.yandex.demo.api`, `ru.yandex.demo.model`)
- Реализация API: `src/main/java/ru/yandex/demo/web/ItemsApiController.java`

## Сборка и запуск

```bash
# Генерация кода из OpenAPI (выполняется автоматически при compileJava)
./gradlew openApiGenerate

# Сборка
./gradlew build

# Запуск приложения
./gradlew bootRun
```

Приложение поднимается на порту **8080**.

## Примеры запросов

```bash
# Список элементов
curl -s http://localhost:8080/items

# Создать элемент
curl -s -X POST http://localhost:8080/items -H "Content-Type: application/json" -d '{"name":"Test"}'

# Получить по ID
curl -s http://localhost:8080/items/1
```

## Изменение API

1. Отредактируйте `api/openapi.yaml`.
2. Запустите `./gradlew openApiGenerate`.
3. Обновите реализацию в `ru.yandex.demo.web` под новые интерфейсы/модели.