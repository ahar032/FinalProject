package com.mahar.busxhacktiv.model;

import java.util.ArrayList;

public class Order {
    String amount,busId,orderId,tickets;
    ArrayList<String> descripsi = new ArrayList<String>();

    public Order() {
    }

    public Order(String amount, String busId, String orderId, ArrayList<String> descripsi, String tickets) {
        this.amount = amount;
        this.busId = busId;
        this.orderId = orderId;
        this.descripsi = descripsi;
        this.tickets=tickets;
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
