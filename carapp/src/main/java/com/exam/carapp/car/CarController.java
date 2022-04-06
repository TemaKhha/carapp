package com.exam.carapp.car;

import com.exam.carapp.car.model.Car;
import com.exam.carapp.car.service.*;
import com.exam.carapp.car.service.CarServiceImpl;
import com.exam.carapp.user.models.User;
import com.exam.carapp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping(value = "/car")
    public ResponseEntity<?> create(@RequestBody Car car) {
       carService.create(car);
       return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/car/{userid}")
    public ResponseEntity<List<Car>> read(@PathVariable(name = "userid") int userid) {
        final List<Car> carList = carService.getAllForUser(userid);

        return carList != null
                ? new ResponseEntity<>(carList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/car/{id}/{userId}")
    public ResponseEntity<?> reassign(@PathVariable(name = "id") int id, @PathVariable(name = "userId") int userId) {
        final boolean updated = carService.updateUser(userId, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping(value = "car/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @RequestBody Car car) {
        final boolean updated = carService.updateCar(id, car);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
