package com.exam.carapp.requests.byingRequests.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "buying")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BuyingRequest {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "buyIdSeq", sequenceName = "buying_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "buyIdSeq")
    private Integer id;

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "carId")
    private Integer carId;

    @Column(name = "price")
    private Integer price;

    @Column(name = "date")
    private Date date;

    @Column(name = "status")
    private String status;

    public BuyingRequest(Integer id, Integer userId, Integer carId, Integer price, Date date, String status) {
        this.id = id;
        this.userId = userId;
        this.carId = carId;
        this.price = price;
        this.date = date;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BuyingRequest() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
