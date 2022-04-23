package com.exam.carapp.requests.byingRequests;

import com.exam.carapp.requests.byingRequests.model.BuyingRequest;
import com.exam.carapp.requests.byingRequests.model.BuyingRequestWithCar;
import com.exam.carapp.requests.byingRequests.model.RequestError;
import com.exam.carapp.requests.byingRequests.model.RequestErrorResponse;
import com.exam.carapp.requests.byingRequests.service.BuyingRequestsService;
import com.exam.carapp.user.models.User;
import com.exam.carapp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BuyingRequestsController {

    private final BuyingRequestsService buyingRequestsService;

    @Autowired
    public BuyingRequestsController(BuyingRequestsService buyingRequestsService) {
        this.buyingRequestsService = buyingRequestsService;
    }

    @CrossOrigin
    @PostMapping(value = "/buy")
    public ResponseEntity<RequestErrorResponse> create(@RequestBody BuyingRequest request) {
        RequestError status = buyingRequestsService.createRequest(request);
        return new ResponseEntity<>(status.getResponse(), status.getHttpCode());
    }

    @CrossOrigin
    @PutMapping(value = "/buy/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable(name = "id") int id, @RequestParam String status) {
        System.out.println(status);
        boolean isUpdated = buyingRequestsService.updateStatus(status, id);
        return isUpdated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @CrossOrigin
    @GetMapping(value = "/buy")
    public ResponseEntity<List<BuyingRequest>> getAll() {
        List<BuyingRequest> list = buyingRequestsService.getAll();

        return list == null
                ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(list, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/buy/user/{id}")
    public ResponseEntity<List<BuyingRequestWithCar>> getAllForUser(@PathVariable(name = "id") int id) {
        List<BuyingRequestWithCar> list = buyingRequestsService.getByUserId(id);

        return list == null
                ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(list, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/buy/car/{id}")
    public ResponseEntity<List<BuyingRequest>> getAllForCar(@PathVariable(name = "id") int id) {
        List<BuyingRequest> list = buyingRequestsService.getByCarId(id);

        return list == null
                ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(list, HttpStatus.OK);
    }
}
