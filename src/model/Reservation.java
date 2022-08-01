package model;

import model.Customer;
import java.util.Date;
import model.IRoom;


public class Reservation {
    public Customer customer;
    public IRoom room;
    public Date checkInDate;
    public Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        this.customer = customer;
        this.room = room;
        this.checkInDate =  checkInDate;
        this.checkOutDate = checkOutDate;
    }
    @Override
    public String toString() {
        return "Reservation for " + customer + "\nRoom: " + room + "\n Dates:" + checkInDate + " - " + checkOutDate;
    }

}
