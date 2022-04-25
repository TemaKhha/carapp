package com.exam.carapp.membership;

import com.exam.carapp.requests.byingRequests.model.RequestError;
import com.exam.carapp.requests.byingRequests.model.RequestErrorResponse;
import com.exam.carapp.requests.serviceRequests.model.PriceDTO;
import com.exam.carapp.requests.serviceRequests.model.ServiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MembershipController {
    private final MembershipService membershipService;

    @Autowired
    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @CrossOrigin
    @PostMapping(value = "/membership")
    public ResponseEntity<RequestErrorResponse> create(@RequestBody Membership membership) {
        RequestError status = membershipService.create(membership);
        return new ResponseEntity<>(status.getResponse(), status.getHttpCode());
    }

    @CrossOrigin
    @GetMapping(value = "/membership/price")
    public ResponseEntity<PriceDTO> getPrice(@RequestParam String options, @RequestParam int carId) {
        PriceDTO priceDTO = membershipService.getPriceFor(options, carId);
        return priceDTO == null
                ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(priceDTO, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/membership")
    public ResponseEntity<List<Membership>> getAll() {
        List<Membership> list = membershipService.getAll();

        return list == null
                ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(list, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/membership/car/{id}")
    public ResponseEntity<List<Membership>> getAllForCar(@PathVariable(name = "id") int id) {
        List<Membership> list = membershipService.getForCarId(id);

        return list == null
                ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(list, HttpStatus.OK);
    }
}
