package com.exam.carapp.requests.serviceRequests.model;

public class PriceDTO {
    private Integer price;

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public PriceDTO(Integer price) {
        this.price = price;
    }
}
