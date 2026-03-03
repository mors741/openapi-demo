package ru.yandex.demo.web;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import ru.yandex.demo.api.PetsApi;
import ru.yandex.demo.model.Pet;

@RestController
public class PetsApiController implements PetsApi {

    private final List<Pet> store = new CopyOnWriteArrayList<>();

    @Override
    public ResponseEntity<List<Pet>> getPets() {
        return ResponseEntity.ok(new ArrayList<>(store));
    }

    @Override
    public ResponseEntity<Pet> createPet(Pet pet) {
        store.add(pet);
        return ResponseEntity.status(201).body(pet);
    }
}
