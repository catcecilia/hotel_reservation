package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class HotelResource {

    private static HotelResource one_instance = null;

    public Customer getCustomer(String email){
        try{
            return CustomerService.getInstance().getCustomer(email);
        } catch(Exception e) {
            return null;
        }
    }

    //huh?
    public void createACustomer(String email, String firstName, String lastName){
        try {
            CustomerService.getInstance().addCustomer(email, firstName,lastName);
        } catch(Exception e) {}
    }

    public IRoom getRoom(String roomNumber){
        try {
            return ReservationService.getInstance().getARoom(roomNumber);
        } catch(Exception e){
            return null;
        }
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate){
        try{
            Customer customer = getCustomer(customerEmail);
            return ReservationService.getInstance().reserveARoom(customer, room, checkInDate, checkOutDate);
        } catch(Exception e) {
            return null;
        }
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail){
        try {
            Customer customer = getCustomer(customerEmail);
            return ReservationService.getInstance().getCustomersReservation(customer);
        } catch(Exception e){
            return null;
        }
    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut){
        try {
            return ReservationService.getInstance().findRooms(checkIn,checkOut);
        } catch(Exception e) {
            return null;
        }
    }

    private HotelResource() {}

    //static reference to check if this is the first time an instance is being created
    public static HotelResource getInstance() {
        if (one_instance == null) {
            one_instance = new HotelResource();
        }

        return one_instance;
    }
}
