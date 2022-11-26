package com.mahar.busxhacktiv.model;

public class BusInfo {
    String arrival,departure,busId,date;

    public BusInfo() {
    }

    public BusInfo(String arrival, String departure, String busId, String date) {
        this.arrival = arrival;
        this.departure = departure;
        this.busId = busId;
        this.date = date;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
