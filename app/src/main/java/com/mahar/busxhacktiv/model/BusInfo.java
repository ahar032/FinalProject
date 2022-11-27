package com.mahar.busxhacktiv.model;

public class BusInfo {
    String arrival,departure,busId,date,busName,plateNo,timeDeparture,timeArrival;

    public BusInfo() {
    }

    public BusInfo(String arrival, String departure, String busId, String date, String busName, String plateNo, String timeDeparture, String timeArrival) {
        this.arrival = arrival;
        this.departure = departure;
        this.busId = busId;
        this.date = date;
        this.busName = busName;
        this.plateNo = plateNo;
        this.timeDeparture = timeDeparture;
        this.timeArrival = timeArrival;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getTimeDeparture() {
        return timeDeparture;
    }

    public void setTimeDeparture(String timeDeparture) {
        this.timeDeparture = timeDeparture;
    }

    public String getTimeArrival() {
        return timeArrival;
    }

    public void setTimeArrival(String timeArrival) {
        this.timeArrival = timeArrival;
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
