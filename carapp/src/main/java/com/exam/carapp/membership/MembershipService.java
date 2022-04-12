package com.exam.carapp.membership;

import com.exam.carapp.requests.byingRequests.model.RequestError;
import com.exam.carapp.requests.serviceRequests.model.PriceDTO;

import java.util.List;

public interface MembershipService {
    RequestError create(Membership membership);
    List<Membership> getAll();
    List<Membership> getForCarId(Integer carId);
    PriceDTO getPriceFor(String options, Integer carId);
}
