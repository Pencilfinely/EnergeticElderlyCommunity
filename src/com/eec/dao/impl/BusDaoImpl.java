package com.eec.dao.impl;

import com.eec.dao.BusDao;
import com.eec.entity.Bus;
import com.eec.entity.Member;
import com.eec.utils.Tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BusDaoImpl implements BusDao {

    private static String filePath = "Bus.json";
    private static BusDaoImpl instance = null;

    public static BusDaoImpl getInstance(){
        if(instance == null){
            instance = new BusDaoImpl();
        }

        return instance;
    }

    private BusDaoImpl(){}

    @Override
    public int add(String busCode, String name, String type, String direction,
                   String date, String period, String time, String endBookingTime, int bookingCnt) throws IOException {
        List<Bus> list = Tools.getJsonToList(filePath, Bus.class);
        int newId = list.get(list.size() - 1).getId() + 1;
        list.add(new Bus(newId,busCode,name,type,direction,date,period,time,endBookingTime,bookingCnt));

        Tools.writeListToJSON(filePath,list);
        return 1;
    }

    @Override
    public int add(Bus bus) throws IOException {
        List<Bus> list = Tools.getJsonToList(filePath, Bus.class);
        int newId = list.get(list.size() - 1).getId() + 1;
        list.add(bus);

        Tools.writeListToJSON(filePath,list);
        return 1;
    }

    @Override
    public void delete(int index) throws IOException {
        List<Object> list = Tools.getJsonToList(filePath, Bus.class);
        list.remove(index);

        Tools.writeListToJSON(filePath,list);
    }

    @Override
    public void delete(Bus bus) throws IOException {
        List<Bus> list = Tools.getJsonToList(filePath, Bus.class);
        for (Bus b : list) {
            if (b.getBusCode().equals(bus.getBusCode())){
                list.remove(b);
                break;
            }
        }

        Tools.writeListToJSON(filePath,list);
    }

    @Override
    public List<Bus> getAll() throws IOException {
        File file = new File(filePath);
        Bus bus = new Bus(0,"","","","","","","","",0);
        if(!file.exists()){
            file.createNewFile();
            List<Bus> list = new ArrayList<>();
            list.add(bus);
            Tools.writeListToJSON(filePath,list);
        }
        List<Bus> list = Tools.getJsonToList(filePath, Bus.class);

        return list;
    }

    @Override
    public int setAll(List<Bus> list) throws IOException {
        Tools.writeListToJSON(filePath,list);
        return 1;
    }
}
