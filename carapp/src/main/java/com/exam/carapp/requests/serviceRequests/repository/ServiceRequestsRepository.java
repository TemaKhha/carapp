package com.exam.carapp.requests.serviceRequests.repository;

import com.exam.carapp.requests.serviceRequests.model.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRequestsRepository extends JpaRepository<ServiceRequest, Integer> {
    List<ServiceRequest> getByUserId(Integer userId);
    List<ServiceRequest> getByCarId(Integer carId);
}
