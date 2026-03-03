package ru.yandex.demo.web;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import ru.yandex.demo.api.ShapesApi;
import ru.yandex.demo.model.Circle;
import ru.yandex.demo.model.ShapeWrapper;
import ru.yandex.demo.model.Square;

@RestController
public class ShapesApiController implements ShapesApi {

    private final List<Circle> circles = new CopyOnWriteArrayList<>();
    private final List<Square> squares = new CopyOnWriteArrayList<>();

    @Override
    public ResponseEntity<List<ShapeWrapper>> getShapes() {
        List<ShapeWrapper> all = new ArrayList<>(circles.size() + squares.size());
        for (Circle c : circles) {
            ShapeWrapper w = new ShapeWrapper();
            w.setType(ShapeWrapper.TypeEnum.CIRCLE);
            w.setData(c);
            all.add(w);
        }
        for (Square s : squares) {
            ShapeWrapper w = new ShapeWrapper();
            w.setType(ShapeWrapper.TypeEnum.SQUARE);
            w.setData(s);
            all.add(w);
        }
        return ResponseEntity.ok(all);
    }

    @Override
    public ResponseEntity<ShapeWrapper> createShape(ShapeWrapper shapeWrapper) {
        if (shapeWrapper.getType() == ShapeWrapper.TypeEnum.CIRCLE) {
            Circle circle = (Circle) shapeWrapper.getData();
            circles.add(circle);
            ShapeWrapper response = new ShapeWrapper();
            response.setType(ShapeWrapper.TypeEnum.CIRCLE);
            response.setData(circle);
            return ResponseEntity.status(201).body(response);
        } else if (shapeWrapper.getType() == ShapeWrapper.TypeEnum.SQUARE) {
            Square square = (Square) shapeWrapper.getData();
            squares.add(square);
            ShapeWrapper response = new ShapeWrapper();
            response.setType(ShapeWrapper.TypeEnum.SQUARE);
            response.setData(square);
            return ResponseEntity.status(201).body(response);
        } else {
            throw new IllegalArgumentException("Unknown shape type: " + shapeWrapper.getType());
        }
    }
}
