package com.example.staysafeapp;

import java.io.Serializable;

public class StatsItem implements Serializable {
    String provinsi, kasus_posi, kasus_semb, kasus_meni;

    public StatsItem(String provinsi, String kasus_posi, String kasus_semb, String kasus_meni) {
        this.provinsi = provinsi;
        this.kasus_posi = kasus_posi;
        this.kasus_semb = kasus_semb;
        this.kasus_meni = kasus_meni;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public String getKasus_posi() {
        return kasus_posi;
    }

    public String getKasus_semb() {
        return kasus_semb;
    }

    public String getKasus_meni(){
        return kasus_meni;
    }

}
