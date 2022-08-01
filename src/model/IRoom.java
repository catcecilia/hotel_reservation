package model;

import model.RoomType;
import model.Room;
import model.FreeRoom;
import model.Reservation;
import model.Customer;

public interface IRoom {
    public String getRoomNumber();
    public Double getRoomPrice();
    public RoomType getRoomType();
    public boolean isFree();

}
