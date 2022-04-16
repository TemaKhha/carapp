package com.exam.carapp.requests.serviceRequests.service;

import com.exam.carapp.car.model.Car;
import com.exam.carapp.car.service.CarRepository;
import com.exam.carapp.membership.Membership;
import com.exam.carapp.membership.MembershipRepository;
import com.exam.carapp.requests.byingRequests.BuyingRequestsRepository;
import com.exam.carapp.requests.byingRequests.model.BuyingRequest;
import com.exam.carapp.requests.byingRequests.model.RequestError;
import com.exam.carapp.requests.serviceRequests.model.PriceDTO;
import com.exam.carapp.requests.serviceRequests.model.ServiceRequest;
import com.exam.carapp.requests.serviceRequests.repository.ServiceRequestsRepository;
import com.exam.carapp.user.models.User;
import com.exam.carapp.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceRequestsServiceImp implements ServiceRequestsService {

    private final ServiceRequestsRepository serviceRequestsRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final MembershipRepository membershipRepository;

    public ServiceRequestsServiceImp(ServiceRequestsRepository serviceRequestsRepository, UserRepository userRepository, CarRepository carRepository, MembershipRepository membershipRepository) {
        this.serviceRequestsRepository = serviceRequestsRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.membershipRepository = membershipRepository;
    }

    @Override
    public List<ServiceRequest> getAll() {
        return serviceRequestsRepository.findAll();
    }

    @Override
    public ServiceRequest getById(Integer id) {
        return serviceRequestsRepository.getById(id);
    }

    @Override
    public RequestError create(ServiceRequest request) {
        if (!userRepository.existsById(request.getUserId())) {
            return RequestError.USER_NOT_FOUND;
        } else if (!carRepository.existsById(request.getCarId())) {
            return RequestError.CAR_NOT_FOUND;
        } else if (userRepository.getById(request.getUserId()).getWallet() < getPriceForService(request.getCarId(), request.getOptions().split(","))) {
            return RequestError.NOT_ENOUGH_MONEY;
        }
        request.setDate(new Date());
        request.setPrice(getPriceForService(request.getCarId(), request.getOptions().split(",")));
        request.setStatus("CREATED");
        serviceRequestsRepository.save(request);
        return RequestError.SUCCESS;
    }

    @Override
    public boolean updateStatus(Integer requestId, String status) {
        ServiceRequest request = serviceRequestsRepository.getById(requestId);
        if (request == null || !verifyStatus(status)) {
            return false;
        }

        request.setStatus(status);

        serviceRequestsRepository.save(request);
        if (request.getStatus().equals("DONE")) {
            closeDeal(request);
        }
        return true;
    }

    void closeDeal(ServiceRequest request) {
        User user = userRepository.getById(request.getUserId());
        Car car = carRepository.getById(request.getCarId());
        user.setWallet(user.getWallet() - request.getPrice());
        car.setUserId(user.getId());
        userRepository.save(user);
        carRepository.save(car);
    }

    boolean verifyStatus(String status) {
        return (status.equals("CREATE") || status.equals("IN_PROCESS") || status.equals("REJECTED") || status.equals("CANCELLED") || status.equals("DONE"));
    }

    @Override
    public List<ServiceRequest> getByUserId(Integer userId) {
        return serviceRequestsRepository.getByUserId(userId);
    }

    @Override
    public List<ServiceRequest> getByCarId(Integer carId) {
        return serviceRequestsRepository.getByCarId(carId);
    }

    @Override
    public PriceDTO calculatePriceFor(String options, Integer carId) {
        String [] array = options.split(",");
        if (!carRepository.existsById(carId)) {
            return null;
        }
        return new PriceDTO(getPriceForService(carId, array));
    }

    private Integer getPriceForService(Integer carId, String [] options) {
        Integer price = getPriceFor(options, carId);
        Car car = carRepository.getById(carId);

        if (car.getPrice() > 10000000) {
            price = price * 4;
        } else if (car.getPrice() > 5000000) {
            price = price * 3;
        } else if (car.getPrice() > 3000000) {
            price = price * 2;
        } else if (car.getPrice() > 2000000) {
            price = price * 15 / 10;
        }

        return price;
    }

    private Integer getPriceFor(String [] options, Integer carId) {
        Integer price = 0;
        List<Membership> membership = membershipRepository.getByCarId(carId);
        for(int i = 0; i < options.length; i++) {
            Integer priceWithoutSale = priceForOption(options[i]);
            if (membership.isEmpty()) {
                price += priceWithoutSale;
            } else {
                String membershipOptions = membership.get(0).getOptions();
                if (membershipOptions.contains(options[i])) {
                    price += priceWithoutSale * getDiscount(options[i], membershipOptions) / 100;
                } else {
                    price += priceWithoutSale;
                }
            }

        }
        return price;
    }

    private Integer getDiscount(String options, String membershipOptions) {
        String [] optionsWithSaleSize = membershipOptions.split(";");
        for(int i = 0; i < optionsWithSaleSize.length; i++) {
            if (optionsWithSaleSize[i].split(",")[0].equals(options)) {
                String strSale = optionsWithSaleSize[i].split(",")[1];
                Integer sale = Integer.parseInt(strSale);
                if (sale == null) {
                    return 100;
                } else {
                    return 100 - sale;
                }
            }
        }
        return 100;
    }
    private Integer priceForOption(String option) {
        if (option.equals("REPAIR")) {
            return 100000;
        } else if (option.equals("CLEAN")) {
            return 20000;
        } else if (option.equals("HARD_CLEAN")) {
            return 60000;
        } else if (option.equals("TO")) {
            return 50000;
        } else if (option.equals("HARD_REPAIR")) {
            return 300000;
        } else if (option.equals("DIAGNOSTIC")) {
            return 10000;
        } else {
            return 0;
        }
    }
}
