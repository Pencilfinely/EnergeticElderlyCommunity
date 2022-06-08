package com.eec.entity;

public class Bus {
    private int id;
    private String busCode;
    private String name;
    private String type;
    private String direction;
    private String date;
    private String period;
    private String time;
    private String endBookingTime;
    private int bookingCnt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBusCode() {
        return busCode;
    }

    public void setBusCode(String busCode) {
        this.busCode = busCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEndBookingTime() {
        return endBookingTime;
    }

    public void setEndBookingTime(String endBookingTime) {
        this.endBookingTime = endBookingTime;
    }

    public int getBookingCnt() {
        return bookingCnt;
    }

    public void setBookingCnt(int bookingCnt) {
        this.bookingCnt = bookingCnt;
    }

    public Bus(int id, String busCode, String name, String type, String direction, String date, String period, String time, String endBookingTime, int bookingCnt) {
        this.id = id;
        this.busCode = busCode;
        this.name = name;
        this.type = type;
        this.direction = direction;
        this.date = date;
        this.period = period;
        this.time = time;
        this.endBookingTime = endBookingTime;
        this.bookingCnt = bookingCnt;
    }

    public Bus() {
    }
}
