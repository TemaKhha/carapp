package com.exam.carapp.requests.serviceRequests;

import com.exam.carapp.requests.byingRequests.model.BuyingRequest;
import com.exam.carapp.requests.byingRequests.model.RequestError;
import com.exam.carapp.requests.byingRequests.model.RequestErrorResponse;
import com.exam.carapp.requests.byingRequests.service.BuyingRequestsService;
import com.exam.carapp.requests.serviceRequests.model.PriceDTO;
import com.exam.carapp.requests.serviceRequests.model.ServiceRequest;
import com.exam.carapp.requests.serviceRequests.service.ServiceRequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ServiceRequestController {

    private final ServiceRequestsService serviceRequestsService;

    @Autowired
    public ServiceRequestController(ServiceRequestsService serviceRequestsService) {
        this.serviceRequestsService = serviceRequestsService;
    }

    @PostMapping(value = "/service")
    public ResponseEntity<RequestErrorResponse> create(@RequestBody ServiceRequest request) {
        RequestError status = serviceRequestsService.create(request);
        return new ResponseEntity<>(status.getResponse(), status.getHttpCode());
    }

    @PutMapping(value = "/service/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable(name = "id") int id, @RequestParam String status) {
        System.out.println(status);
        boolean isUpdated = serviceRequestsService.updateStatus(id, status);
        return isUpdated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(value = "/service")
    public ResponseEntity<List<ServiceRequest>> getAll() {
        List<ServiceRequest> list = serviceRequestsService.getAll();

        return list == null
                ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/service/price")
    public ResponseEntity<PriceDTO> getPrice(@RequestParam String options, @RequestParam int carId) {
        PriceDTO priceDTO = serviceRequestsService.calculatePriceFor(options, carId);
        return priceDTO == null
                ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(priceDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/service/user/{id}")
    public ResponseEntity<List<ServiceRequest>> getAllForUser(@PathVariable(name = "id") int id) {
        List<ServiceRequest> list = serviceRequestsService.getByUserId(id);

        return list == null
                ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/service/car/{id}")
    public ResponseEntity<List<ServiceRequest>> getAllForCar(@PathVariable(name = "id") int id) {
        List<ServiceRequest> list = serviceRequestsService.getByCarId(id);

        return list == null
                ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(list, HttpStatus.OK);
    }
}
