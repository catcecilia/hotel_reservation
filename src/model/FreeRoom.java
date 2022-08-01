package model;

import model.Room;


public class FreeRoom extends Room{

    public FreeRoom(String roomNumber, RoomType enumeration) {
        //sets price to 0.0
        super(roomNumber, 0.0, enumeration);
    }


    @Override
    public String toString() {
        return "Room Number: " + roomNumber + "\nPrice: Free \nRoom Type: " + enumeration;
    }
}
