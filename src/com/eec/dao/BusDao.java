package com.eec.dao;

import com.eec.entity.Bus;

import java.io.IOException;
import java.util.List;

public interface BusDao {
    public int add(String busCode,
                   String name,
                   String type,
                   String direction,
                   String date,
                   String period,
                   String time,
                   String endBookingTime,
                   int bookingCnt) throws IOException;

    public int add(Bus bus) throws IOException;

    public void delete(int index) throws IOException;

    public void delete(Bus bus) throws IOException;

    public List<Bus> getAll() throws IOException;

    public int setAll(List<Bus> list) throws IOException;
}
