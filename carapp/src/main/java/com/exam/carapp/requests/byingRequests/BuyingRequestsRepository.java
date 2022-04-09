package com.exam.carapp.requests.byingRequests;

import com.exam.carapp.requests.byingRequests.model.BuyingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuyingRequestsRepository extends JpaRepository<BuyingRequest, Integer> {
    List<BuyingRequest> getByCarId(Integer carId);
    List<BuyingRequest> getByUserId(Integer userId);
}
