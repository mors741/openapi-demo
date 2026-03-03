package ru.yandex.demo.web;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import ru.yandex.demo.api.FruitsApi;
import ru.yandex.demo.model.Apple;
import ru.yandex.demo.model.Banana;
import ru.yandex.demo.model.Fruit;

@RestController
public class FruitsApiController implements FruitsApi {

    private final List<Apple> apples = new CopyOnWriteArrayList<>();
    private final List<Banana> bananas = new CopyOnWriteArrayList<>();

    @Override
    public ResponseEntity<List<Fruit>> getFruits() {
        List<Fruit> all = new ArrayList<>(apples.size() + bananas.size());
        for (Apple a : apples) {
            Fruit f = new Fruit();
            f.setType("apple");
            f.setApple(a);
            all.add(f);
        }
        for (Banana b : bananas) {
            Fruit f = new Fruit();
            f.setType("banana");
            f.setBanana(b);
            all.add(f);
        }
        return ResponseEntity.ok(all);
    }

    @Override
    public ResponseEntity<Fruit> createFruit(Fruit fruit) {
        if (fruit.getApple() != null) {
            apples.add(fruit.getApple());
        }
        if (fruit.getBanana() != null) {
            bananas.add(fruit.getBanana());
        }
        if (fruit.getApple() == null && fruit.getBanana() == null) {
            throw new IllegalArgumentException("Fruit must have apple or banana set");
        }
        return ResponseEntity.status(201).body(fruit);
    }
}
