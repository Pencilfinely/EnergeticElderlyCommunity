package com.eec.entity;

import java.awt.color.ICC_ColorSpace;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Member {
    private int id;
    private String name;
    private String birth;
    private String gender;
    private String phone;
    private String IC;
    private String adress;
    private String type;
    private String contact;
    private String contactPhone;
    private String livingManagerUsername;
    private String livingManagerPassword;

    private String busCode;

    public String getBusCode() {
        return busCode;
    }

    public void setBusCode(String busCode) {
        this.busCode = busCode;
    }

    public String getLivingManagerUsername() {
        return livingManagerUsername;
    }

    public void setLivingManagerUsername(String livingManagerUsername) {
        this.livingManagerUsername = livingManagerUsername;
    }

    public String getLivingManagerPassword() {
        return livingManagerPassword;
    }

    public void setLivingManagerPassword(String livingManagerPassword) {
        this.livingManagerPassword = livingManagerPassword;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIC() {
        return IC;
    }

    public void setIC(String IC) {
        this.IC = IC;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Member(int id, String name, String birth, String gender, String phone, String IC,
                  String adress, String type, String contact, String contactPhone, String livingManagerUsername,
                  String livingManagerPassword, String busCode) {
        this.id = id;
        this.name = name;
        this.birth = birth;
        this.gender = gender;
        this.phone = phone;
        this.IC = IC;
        this.adress = adress;
        this.type = type;
        this.contact = contact;
        this.contactPhone = contactPhone;
        this.livingManagerUsername = livingManagerUsername;
        this.livingManagerPassword = livingManagerPassword;
        this.busCode = busCode;
    }

    public Member() {
    }
}
