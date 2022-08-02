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

    //hashcode and equals overrides
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Room)) {
            return false;
        }

        Room other = (Room) o;

        return this.hashCode() == other.hashCode();
    }

    @Override
    public int hashCode() {
        return this.roomNumber.hashCode();
    }

    @Override
    public String toString() {
        return "Room number: " + roomNumber + "\nPrice: " + price + "\nRoom Type: " + enumeration;
    }
}
