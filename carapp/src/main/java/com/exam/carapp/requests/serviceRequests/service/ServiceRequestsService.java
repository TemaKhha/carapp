package com.exam.carapp.requests.serviceRequests.service;

import com.exam.carapp.requests.byingRequests.model.RequestError;
import com.exam.carapp.requests.serviceRequests.model.PriceDTO;
import com.exam.carapp.requests.serviceRequests.model.ServiceRequest;
import com.exam.carapp.requests.serviceRequests.model.ServiceRequestWithCar;

import java.util.List;

public interface ServiceRequestsService {
    List<ServiceRequestWithCar> getAll();
    RequestError create(ServiceRequest request);
    boolean updateStatus(Integer requestId, String status);
    List<ServiceRequestWithCar> getByUserId(Integer userId);
    List<ServiceRequest> getByCarId(Integer carId);
    ServiceRequest getById(Integer id);
    PriceDTO calculatePriceFor(String options, Integer carId);
}
