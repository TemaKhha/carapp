package com.exam.carapp.requests.serviceRequests.service;

import com.exam.carapp.requests.byingRequests.model.RequestError;
import com.exam.carapp.requests.serviceRequests.model.PriceDTO;
import com.exam.carapp.requests.serviceRequests.model.ServiceRequest;

import java.util.List;

public interface ServiceRequestsService {
    List<ServiceRequest> getAll();
    RequestError create(ServiceRequest request);
    boolean updateStatus(Integer requestId, String status);
    List<ServiceRequest> getByUserId(Integer userId);
    List<ServiceRequest> getByCarId(Integer carId);
    ServiceRequest getById(Integer id);
    PriceDTO calculatePriceFor(String options, Integer carId);
}
