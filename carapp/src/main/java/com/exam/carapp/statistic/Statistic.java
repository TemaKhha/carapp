package com.exam.carapp.statistic;

public class Statistic {
    public Statistic() {
    }

    public StatInfo getBuy() {
        return buy;
    }

    public void setBuy(StatInfo buy) {
        this.buy = buy;
    }

    public StatInfo getRequests() {
        return requests;
    }

    public void setRequests(StatInfo requests) {
        this.requests = requests;
    }

    public StatInfo getService() {
        return service;
    }

    public void setService(StatInfo service) {
        this.service = service;
    }

    public Integer getMemberships() {
        return memberships;
    }

    public void setMemberships(Integer memberships) {
        this.memberships = memberships;
    }

    public Statistic(StatInfo buy, StatInfo requests, StatInfo service, Integer memberships) {
        this.buy = buy;
        this.requests = requests;
        this.service = service;
        this.memberships = memberships;
    }

    private StatInfo buy;
    private StatInfo requests;
    private StatInfo service;
    private Integer memberships;
}
