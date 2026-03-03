package ru.yandex.demo.web;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import ru.yandex.demo.api.ItemsApi;
import ru.yandex.demo.model.Item;
import ru.yandex.demo.model.ItemCreate;

@RestController
public class ItemsApiController implements ItemsApi {

    private final Map<String, Item> store = new ConcurrentHashMap<>();
    private final AtomicLong idSeq = new AtomicLong(1);

    @Override
    public ResponseEntity<List<Item>> getItems() {
        return ResponseEntity.ok(List.copyOf(store.values()));
    }

    @Override
    public ResponseEntity<Item> createItem(ItemCreate itemCreate) {
        String id = String.valueOf(idSeq.getAndIncrement());
        Item item = new Item();
        item.setId(id);
        item.setName(itemCreate.getName());
        store.put(id, item);
        return ResponseEntity.status(201).body(item);
    }

    @Override
    public ResponseEntity<Item> getItemById(String id) {
        return ResponseEntity.of(Optional.ofNullable(store.get(id)));
    }
}
