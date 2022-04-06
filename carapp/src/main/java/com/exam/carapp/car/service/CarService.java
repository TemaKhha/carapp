package com.exam.carapp.car.service;

import com.exam.carapp.car.model.Car;

import java.util.List;

public interface CarService {

    void create(Car car);
    List<Car> getAllForUser(int userId);
    boolean updateUser(int userId, int carId);
    boolean updateCar(int carId, Car car);
    List<Car> getAllForAdmin();
}
