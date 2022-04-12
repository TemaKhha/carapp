package com.exam.carapp.car.model;

import com.exam.carapp.membership.Membership;
import com.exam.carapp.requests.byingRequests.model.BuyingRequest;
import com.exam.carapp.requests.serviceRequests.model.ServiceRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cars")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Car {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "carsIdSeq", sequenceName = "cars_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "carsIdSeq")
    private Integer id;

    @Column(name = "model")
    private String model;

    @Column(name = "brand")
    private String brand;

    @Column(name = "price")
    private Integer price;

    @Column(name = "color")
    private String color;

    @Column(name = "year")
    private Integer year;

    @Column(name = "maxSpeed")
    private Integer maxSpeed;

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "image")
    private String image;

    public List<BuyingRequest> getBuyingRequests() {
        return buyingRequests;
    }

    public void setBuyingRequests(List<BuyingRequest> buyingRequests) {
        this.buyingRequests = buyingRequests;
    }

    public List<ServiceRequest> getServiceRequests() {
        return serviceRequests;
    }

    public void setServiceRequests(List<ServiceRequest> serviceRequests) {
        this.serviceRequests = serviceRequests;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "carId")
    private List<BuyingRequest> buyingRequests = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "carId")
    private List<Membership> memberships = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "carId")
    private List<ServiceRequest> serviceRequests = new ArrayList<>();

    public Car(Integer id, String model, String brand, Integer price, String color, Integer year, Integer maxSpeed, Integer userId, String image, List<BuyingRequest> buyingRequests, List<ServiceRequest> serviceRequests) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.price = price;
        this.color = color;
        this.year = year;
        this.maxSpeed = maxSpeed;
        this.userId = userId;
        this.image = image;
        this.buyingRequests = buyingRequests;
        this.serviceRequests = serviceRequests;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Car(){}

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Integer maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
}
