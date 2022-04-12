package com.exam.carapp.membership;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "memberships")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Membership {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "memIdSeq", sequenceName = "mem_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "memIdSeq")
    private Integer id;

    @Column(name = "options")
    private String options;

    @Column(name = "carId")
    private Integer carId;

    @Column(name = "price")
    private Integer price;

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

    public Membership(Integer id, String options, Integer carId, Integer price) {
        this.id = id;
        this.options = options;
        this.carId = carId;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }
}
