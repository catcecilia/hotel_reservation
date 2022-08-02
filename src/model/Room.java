package model;

import model.RoomType;
import model.Reservation;
import model.IRoom;


public class Room implements IRoom{
    protected String roomNumber;
    protected Double price;
    protected RoomType enumeration;


    public String getRoomNumber(){
        return roomNumber;
    }
    public Double getRoomPrice(){
        return price;
    }
    public RoomType getRoomType(){
        return enumeration;
    }

    public boolean isFree() {
        return price == 0.0;
    }

    public Room (String roomNumber, Double price, RoomType enumeration){
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    @Override
    public String toString() {
        return "Room number: " + roomNumber + "\nPrice: " + price + "\nRoom Type: " + enumeration;
    }
}
