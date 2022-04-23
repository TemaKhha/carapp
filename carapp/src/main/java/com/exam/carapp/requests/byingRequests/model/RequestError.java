package com.exam.carapp.requests.byingRequests.model;

import org.springframework.http.HttpStatus;

public enum RequestError {
    NOT_ENOUGH_MONEY, CAR_NOT_FOUND, USER_NOT_FOUND, SUCCESS, ALREADY_EXISTS;

    public RequestErrorResponse getResponse() {
        return switch (this) {
            case NOT_ENOUGH_MONEY -> new RequestErrorResponse("К сожалению у вас не хватает денег на покупку этой машины", 400);
            case CAR_NOT_FOUND -> new RequestErrorResponse("Что-то пошло не так, такой машины не существует, возможно ее уже купили", 400);
            case USER_NOT_FOUND -> new RequestErrorResponse("Не можем найти ваш аккаунт", 400);
            case SUCCESS -> new RequestErrorResponse("Запрос успешно создан", 200);
            case ALREADY_EXISTS -> new RequestErrorResponse("У вас уже есть запрос на покупку этой машины", 200);
        };
    }

    public HttpStatus getHttpCode() {
        return switch (this) {
            case NOT_ENOUGH_MONEY, CAR_NOT_FOUND, USER_NOT_FOUND, ALREADY_EXISTS -> HttpStatus.CREATED;
            case SUCCESS -> HttpStatus.OK;
        };
    }
}
