package com.exam.carapp.requests.serviceRequests.model;

import com.exam.carapp.car.model.Car;

public class ServiceRequestWithCar {

    private ServiceRequest serviceRequest;
    private Car car;

    public ServiceRequestWithCar() {
    }

    public ServiceRequest getServiceRequest() {
        return serviceRequest;
    }

    public void setServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequest = serviceRequest;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public ServiceRequestWithCar(ServiceRequest serviceRequest, Car car) {
        this.serviceRequest = serviceRequest;
        this.car = car;
    }
}
