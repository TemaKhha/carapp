package com.exam.carapp.car.service;

import com.exam.carapp.car.model.Car;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService{

    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public void create(Car car) {
        car.setUserId(1);
        carRepository.save(car);
    }

    @Override
    public List<Car> getAllForUser(int userId) {
        return carRepository.findByUserId(userId);
    }

    @Override
    public boolean updateUser(int userId, int carId) {
       Car car = carRepository.getById(carId);
       if (car != null) {
           car.setUserId(userId);
           carRepository.save(car);
           return true;
       }

        return false;
    }

    @Override
    public boolean updateCar(int carId, Car car) {
        if (carRepository.existsById(carId)) {
            car.setId(carId);
            carRepository.save(car);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Car> getAllForAdmin() {
        return getAllForUser(1);
    }
}
