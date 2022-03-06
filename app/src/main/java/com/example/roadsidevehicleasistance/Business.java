package com.example.roadsidevehicleasistance;

public class Business {

    private String businessname,mechanicname,services,available,address,area;
    private int mobile;

    public Business(){

    }

    public Business(String Businessname, String Mechanicname, String services, String available, String address, String area, int mobile) {
        this.businessname = businessname;
      this.mechanicname = mechanicname;
        this.services = services;
        this.available = available;
        this.address = address;
        this.area = area;
        this.mobile = mobile;
    }

    public String getBusinessname() {   
        return businessname;
    }

    public String getMechanicname() {
        return mechanicname;
    }

    public String getServices() {
        return services;
    }

    public String getAvailable() {
        return available;
    }

    public String getAddress() {
        return address;
    }

    public String getArea() {
        return area;
    }

    public int getMobile() {
        return mobile;
    }
}
