package ru.yandex.demo.web;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import ru.yandex.demo.api.PetsApi;
import ru.yandex.demo.model.Cat;
import ru.yandex.demo.model.Dog;
import ru.yandex.demo.model.Pet;

@RestController
public class PetsApiController implements PetsApi {

    private final List<Cat> cats = new CopyOnWriteArrayList<>();
    private final List<Dog> dogs = new CopyOnWriteArrayList<>();

    @Override
    public ResponseEntity<List<Pet>> getPets() {
        List<Pet> all = new ArrayList<>(cats.size() + dogs.size());
        all.addAll(cats);
        all.addAll(dogs);
        return ResponseEntity.ok(all);
    }

    @Override
    public ResponseEntity<Pet> createPet(Pet pet) {
        if (pet instanceof Cat cat) {
            cats.add(cat);
        } else if (pet instanceof Dog dog) {
            dogs.add(dog);
        } else {
            throw new IllegalArgumentException("Unknown pet type: " + (pet != null ? pet.getClass().getSimpleName() : "null"));
        }
        return ResponseEntity.status(201).body(pet);
    }
}
