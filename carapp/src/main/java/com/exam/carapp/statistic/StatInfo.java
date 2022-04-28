package com.exam.carapp.statistic;

public class StatInfo {
    private Integer done;
    private Integer cancelled;
    private Integer rejected;
    private Integer profit;
    private Integer total;

    public StatInfo() {
    }

    public Integer getDone() {
        return done;
    }

    public void setDone(Integer done) {
        this.done = done;
    }

    public Integer getCancelled() {
        return cancelled;
    }

    public void setCancelled(Integer cancelled) {
        this.cancelled = cancelled;
    }

    public Integer getRejected() {
        return rejected;
    }

    public void setRejected(Integer rejected) {
        this.rejected = rejected;
    }

    public Integer getProfit() {
        return profit;
    }

    public void setProfit(Integer profit) {
        this.profit = profit;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public StatInfo(Integer done, Integer cancelled, Integer rejected, Integer profit, Integer total) {
        this.done = done;
        this.cancelled = cancelled;
        this.rejected = rejected;
        this.profit = profit;
        this.total = total;
    }
}
