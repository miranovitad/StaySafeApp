package com.example.staysafeapp;

import java.io.Serializable;

public class HotlineItem implements Serializable {
    String idHotline, hosName, address, phone;

    public HotlineItem(String idHotline, String hosName, String address, String phone) {
        this.idHotline = idHotline;
        this.hosName = hosName;
        this.address = address;
        this.phone = phone;
    }

    public String getIdHotline() {
        return idHotline;
    }

    public String getHosName() {
        return hosName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone(){
        return phone;
    }

}
