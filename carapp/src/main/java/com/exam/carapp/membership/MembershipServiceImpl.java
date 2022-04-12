package com.exam.carapp.membership;

import com.exam.carapp.car.model.Car;
import com.exam.carapp.car.service.CarRepository;
import com.exam.carapp.requests.byingRequests.model.RequestError;
import com.exam.carapp.requests.serviceRequests.model.PriceDTO;
import com.exam.carapp.user.models.User;
import com.exam.carapp.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipServiceImpl implements MembershipService{

    private final MembershipRepository membershipRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;

    public MembershipServiceImpl(MembershipRepository membershipRepository, CarRepository carRepository, UserRepository userRepository) {
        this.membershipRepository = membershipRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

    @Override
    public RequestError create(Membership membership) {
        Car car = carRepository.getById(membership.getCarId());
        if ( car == null) { return RequestError.CAR_NOT_FOUND;}
        User user = userRepository.getById(car.getUserId());
        if (user == null) { return RequestError.USER_NOT_FOUND;}
        Integer price = calculatePriceFor(membership.getOptions(), car.getId());
        if (price > user.getWallet()) { return RequestError.NOT_ENOUGH_MONEY;}
        membership.setPrice(price);
        membershipRepository.save(membership);
        user.setWallet(user.getWallet() - price);
        userRepository.save(user);
        return RequestError.SUCCESS;
    }

    @Override
    public List<Membership> getAll() {
        return membershipRepository.findAll();
    }

    @Override
    public List<Membership> getForCarId(Integer carId) {
        return membershipRepository.getByCarId(carId);
    }

    @Override
    public PriceDTO getPriceFor(String options, Integer carId) {
        if (!carRepository.existsById(carId)) { return null; }

        return new PriceDTO(calculatePriceFor(options, carId));
    }

    public Integer calculatePriceFor(String options, Integer carId) {
        String [] optionsWithPrice = options.split(";");
        Integer totalPrice = 0;
        Integer carPrice = carRepository.getById(carId).getPrice();
        if (carPrice == null) {
            return 0;
        }
        Integer priceMultiplier = getPriceMultiplier(carPrice);
        for(int i = 0; i < optionsWithPrice.length; i++) {
            String option = optionsWithPrice[i].split(",")[0];
            String stringPercent = optionsWithPrice[i].split(",")[1];
            Integer percent = Integer.parseInt(stringPercent);
            if (percent == null) {
                continue;
            }
            totalPrice += getPriceForOptionWithPercent(option, percent);
        }
        totalPrice = totalPrice * priceMultiplier;
        return totalPrice;
    }

    public Integer getPriceForOptionWithPercent(String option, Integer percent) {
        Integer total = 0;
        if (option.equals("REPAIR")) {
            total = 500000;
        } else if (option.equals("CLEAN")) {
            total = 100000;
        } else if (option.equals("HARD_CLEAN")) {
            total = 300000;
        } else if (option.equals("TO")) {
            total = 50000;
        } else if (option.equals("HARD_REPAIR")) {
            total = 700000;
        } else if (option.equals("DIAGNOSTIC")) {
            total = 30000;
        } else {
            total = 0;
        }

        total = total * percent / 100;
        return total;
    }

    public Integer getPriceMultiplier(Integer carPrice) {
        if (carPrice > 10000000) {
            return 5;
        } else  if (carPrice > 5000000) {
            return 4;
        } else if (carPrice > 3500000) {
            return 3;
        } else if (carPrice > 2000000) {
            return 2;
        } else {
            return 1;
        }
    }
}
