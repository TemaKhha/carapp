package com.exam.carapp.requests.byingRequests.model;

import org.springframework.http.HttpStatus;

public enum RequestError {
    NOT_ENOUGH_MONEY, CAR_NOT_FOUND, USER_NOT_FOUND, SUCCESS;

    public RequestErrorResponse getResponse() {
        return switch (this) {
            case NOT_ENOUGH_MONEY -> new RequestErrorResponse("User not found", 400);
            case CAR_NOT_FOUND -> new RequestErrorResponse("Car with this ID does not exist", 400);
            case USER_NOT_FOUND -> new RequestErrorResponse("You don't have enough money", 400);
            case SUCCESS -> new RequestErrorResponse("CREATED", 200);
        };
    }

    public HttpStatus getHttpCode() {
        return switch (this) {
            case NOT_ENOUGH_MONEY, CAR_NOT_FOUND, USER_NOT_FOUND -> HttpStatus.BAD_REQUEST;
            case SUCCESS -> HttpStatus.OK;
        };
    }
}
