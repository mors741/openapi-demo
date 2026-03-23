package ru.yandex.demo.web;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import ru.yandex.demo.api.ShapesApi;
import ru.yandex.demo.model.Circle;
import ru.yandex.demo.model.CircleWrapper;
import ru.yandex.demo.model.Shape;
import ru.yandex.demo.model.ShapeType;
import ru.yandex.demo.model.Square;
import ru.yandex.demo.model.SquareWrapper;

@RestController
public class ShapesApiController implements ShapesApi {

    private final List<Circle> circles = new CopyOnWriteArrayList<>();
    private final List<Square> squares = new CopyOnWriteArrayList<>();

    @Override
    public ResponseEntity<List<Shape>> getShapes() {
        List<Shape> all = new ArrayList<>(circles.size() + squares.size());
        for (Circle c : circles) {
            all.add(new CircleWrapper(ShapeType.CIRCLE, c));
        }
        for (Square s : squares) {
            all.add(new SquareWrapper(ShapeType.SQUARE, s));
        }
        return ResponseEntity.ok(all);
    }

    @Override
    public ResponseEntity<Shape> createShape(Shape shape) {
        if (shape instanceof CircleWrapper cw) {
            circles.add(cw.getData());
        } else if (shape instanceof SquareWrapper sw) {
            squares.add(sw.getData());
        } else {
            throw new IllegalArgumentException("Unknown shape type: " + (shape != null ? shape.getType() : "null"));
        }
        return ResponseEntity.status(201).body(shape);
    }
}
