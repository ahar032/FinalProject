package com.mahar.busxhacktiv.model;

import com.google.firebase.database.ServerValue;

import java.security.Timestamp;
import java.util.ArrayList;

public class Order {
    String amount,busId,orderId,tickets,userId;
    Long date;
    ArrayList<String> descripsi = new ArrayList<String>();
    public Order() {
    }

    public Order(String amount, String busId, String orderId, String tickets, String userId, Long date, ArrayList<String> descripsi) {
        this.amount = amount;
        this.busId = busId;
        this.orderId = orderId;
        this.tickets = tickets;
        this.userId = userId;
        this.date = date;
        this.descripsi = descripsi;
    }

    public Long getDate() {
        return date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTickets() {
        return tickets;
    }

    public void setTickets(String tickets) {
        this.tickets = tickets;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public ArrayList<String> getDescripsi() {
        return descripsi;
    }

    public void setDescripsi(ArrayList<String> descripsi) {
        this.descripsi = descripsi;
    }
}
