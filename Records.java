package com.example.da3_javafx;
public class Records {
    private int id;
    private String houseno;
    private String address;
    private int rooms;
    private int rent;

    public Records(int id, String houseno, String address, int rooms, int rent) {
        this.id = id;
        this.houseno = houseno;
        this.address = address;
        this.rooms = rooms;
        this.rent = rent;
    }

    public int getId() {
        return id;
    }

    public String getHouseNo() {
        return houseno;
    }

    public String getAddress() {
        return address;
    }

    public int getRooms() {
        return rooms;
    }

    public int getRent() {
        return rent;
    }

}
