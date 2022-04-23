package com.exam.carapp.requests.byingRequests.service;

import com.exam.carapp.car.model.Car;
import com.exam.carapp.car.service.CarRepository;
import com.exam.carapp.requests.byingRequests.BuyingRequestsRepository;
import com.exam.carapp.requests.byingRequests.model.BuyingRequest;
import com.exam.carapp.requests.byingRequests.model.BuyingRequestWithCar;
import com.exam.carapp.requests.byingRequests.model.RequestError;
import com.exam.carapp.user.models.User;
import com.exam.carapp.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BuyingRequestsServiceImp implements BuyingRequestsService{

    private final BuyingRequestsRepository buyingRequestsRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    public BuyingRequestsServiceImp(BuyingRequestsRepository buyingRequestsRepository, UserRepository userRepository, CarRepository carRepository) {
        this.buyingRequestsRepository = buyingRequestsRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }

    @Override
    public RequestError createRequest(BuyingRequest request) {
        System.out.println(1);
        if (!userRepository.existsById(request.getUserId())) {
            return RequestError.USER_NOT_FOUND;
        }
        System.out.println(2);
        if (!carRepository.existsById(request.getCarId())) {
            return RequestError.CAR_NOT_FOUND;
        }
        System.out.println(3);
        request.setPrice(carRepository.getById(request.getCarId()).getPrice());
        System.out.println(4);
        if (!checkUserMoney(request)) {
            return RequestError.NOT_ENOUGH_MONEY;
        }

        List<BuyingRequest> list = buyingRequestsRepository.getByUserId(request.getUserId());
        for(BuyingRequest request1 : list) {
            if (request1.getCarId() == request.getCarId() && (request1.getStatus().equals("CREATED") || request1.getStatus().equals("IN_PROCESS"))) {
                return RequestError.ALREADY_EXISTS;
            }
        }
        System.out.println(5);
        request.setStatus("CREATED");
        request.setDate(new Date());
        System.out.println(6);
        buyingRequestsRepository.save(request);
        System.out.println(7);
        return RequestError.SUCCESS;
    }

    @Override
    public List<BuyingRequest> getAll() {
        return buyingRequestsRepository.findAll();
    }

    @Override
    public List<BuyingRequest> getByCarId(Integer carId) {
        return buyingRequestsRepository.getByCarId(carId);
    }

    @Override
    public List<BuyingRequestWithCar> getByUserId(Integer userId) {
        List<BuyingRequest> requests = buyingRequestsRepository.getByUserId(userId);
        List<BuyingRequestWithCar> response = new ArrayList<>();
        for (BuyingRequest request: requests) {
            Car car = carRepository.getById(request.getCarId());
            BuyingRequestWithCar responseEntity = new BuyingRequestWithCar(request, car);
            response.add(responseEntity);
        }
        return response;
    }

    @Override
    public boolean updateStatus(String status, Integer id) {
        BuyingRequest request = buyingRequestsRepository.getById(id);
        if (request == null) {
            return false;
        }
        if (!verifyStatus(status)) {
            return false;
        }
        request.setStatus(status);
        buyingRequestsRepository.save(request);
        if (status.equals("DONE")) {
            closeDeal(request);
        }
        return true;
    }

    boolean verifyStatus(String status) {
        return (status.equals("CREATED") || status.equals("IN_PROCESS") || status.equals("REJECTED") || status.equals("CANCELLED") || status.equals("DONE"));
    }

    boolean checkUserMoney(BuyingRequest request) {
        User user = userRepository.getById(request.getUserId());
        return request.getPrice() < user.getWallet();
    }

    void closeDeal(BuyingRequest request) {
        User user = userRepository.getById(request.getUserId());
        Car car = carRepository.getById(request.getCarId());
        user.setWallet(user.getWallet() - request.getPrice());
        car.setUserId(user.getId());
        userRepository.save(user);
        carRepository.save(car);
        List<BuyingRequest> list = buyingRequestsRepository.getByCarId(request.getCarId());
        for(BuyingRequest el: list) {
            el.setStatus("REJECTED");
            buyingRequestsRepository.save(el);
        }
    }

    @Override
    public BuyingRequest getById(Integer id) {
        return buyingRequestsRepository.getById(id);
    }
}
