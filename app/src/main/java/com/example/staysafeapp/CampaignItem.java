package com.example.staysafeapp;

import java.io.Serializable;

public class CampaignItem implements Serializable {

    String idCampaign, idUser, namaUser, phone, email, name, startDate, endDate, info, detail, target, bankAcc, bankNum;

    public CampaignItem(String idCampaign, String idUser, String namaUser, String phone, String email, String name, String
                        startDate, String endDate, String info, String detail, String target, String bankAcc, String bankNum) {
        this.idCampaign     = idCampaign;
        this.idUser         = idUser;
        this.namaUser       = namaUser;
        this.phone          = phone;
        this.email          = email;
        this.name           = name;
        this.startDate      = startDate;
        this.endDate        = endDate;
        this.info           = info;
        this.detail         = detail;
        this.target         = target;
        this.bankAcc        = bankAcc;
        this.bankNum        = bankNum;
    }

    public String getIdCampaign() {
        return idCampaign;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getStartDate(){
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getInfo() {
        return info;
    }

    public String getDetail() {
        return detail;
    }

    public String getTarget() {
        return target;
    }

    public String getBankAcc() {
        return bankAcc;
    }

    public String getBankNum() {
        return bankNum;
    }


}
