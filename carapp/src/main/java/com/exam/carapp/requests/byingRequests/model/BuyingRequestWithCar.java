package com.exam.carapp.requests.byingRequests.model;

import com.exam.carapp.car.model.Car;

import java.util.List;

public class BuyingRequestWithCar {
    private BuyingRequest request;
    private Car car;

    public BuyingRequestWithCar() {
    }

    public BuyingRequestWithCar(BuyingRequest request, Car car) {
        this.request = request;
        this.car = car;
    }

    public BuyingRequest getRequest() {
        return request;
    }

    public void setRequest(BuyingRequest request) {
        this.request = request;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
