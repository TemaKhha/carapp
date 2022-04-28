package com.exam.carapp.statistic;

import com.exam.carapp.requests.serviceRequests.model.PriceDTO;
import com.exam.carapp.requests.serviceRequests.service.ServiceRequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticController {

    private final StatService service;

    @Autowired
    public StatisticController(StatService service) {
        this.service = service;
    }

    @CrossOrigin
    @GetMapping(value = "/stat")
    public ResponseEntity<Statistic> get() {
        Statistic data = service.getData();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
