package ru.yandex.demo.web;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import ru.yandex.demo.api.VehiclesApi;
import ru.yandex.demo.model.Bike;
import ru.yandex.demo.model.BikeWrapper;
import ru.yandex.demo.model.Car;
import ru.yandex.demo.model.CarWrapper;
import ru.yandex.demo.model.Vehicle;

@RestController
public class VehiclesApiController implements VehiclesApi {

    private final List<Car> cars = new CopyOnWriteArrayList<>();
    private final List<Bike> bikes = new CopyOnWriteArrayList<>();

    @Override
    public ResponseEntity<List<Vehicle>> getVehicles() {
        List<Vehicle> all = new ArrayList<>(cars.size() + bikes.size());
        for (Car c : cars) {
            all.add(new CarWrapper("car", c));
        }
        for (Bike b : bikes) {
            all.add(new BikeWrapper("bike", b));
        }
        return ResponseEntity.ok(all);
    }

    @Override
    public ResponseEntity<Vehicle> createVehicle(Vehicle vehicle) {
        if (vehicle instanceof CarWrapper cw) {
            if (cw.getCar() != null) {
                cars.add(cw.getCar());
            }
        } else if (vehicle instanceof BikeWrapper bw) {
            if (bw.getBike() != null) {
                bikes.add(bw.getBike());
            }
        } else {
            throw new IllegalArgumentException("Unknown vehicle type: " + (vehicle != null ? vehicle.getType() : "null"));
        }
        return ResponseEntity.status(201).body(vehicle);
    }
}
