package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {

    private static HotelResource one_instance = null;

    public Customer getCustomer(String email) throws Exception {
        try{
            return CustomerService.getInstance().getCustomer(email);
        } catch(Exception e) {
            throw new Exception("Email not found");
        }
    }

    //huh?
    public void createACustomer(String email, String firstName, String lastName) {
        try {
            CustomerService.getInstance().addCustomer(email, firstName,lastName);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public IRoom getRoom(String roomNumber) {
        try {
            return ReservationService.getInstance().getARoom(roomNumber);
        } catch(Exception e) {
            return null;
        }
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        try{
            Customer customer = getCustomer(customerEmail);
            return ReservationService.getInstance().reserveARoom(customer, room, checkInDate, checkOutDate);
        } catch(Exception e) {
            return null;
        }
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail) throws Exception{
        try {
            Customer customer = getCustomer(customerEmail);
            if (ReservationService.getInstance().getCustomersReservation(customer) == null) {
                throw new Exception("No reservations found");
            }
            return ReservationService.getInstance().getCustomersReservation(customer);
        } catch(Exception e) {
            return null;
        }
    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return ReservationService.getInstance().findRooms(checkIn,checkOut);
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
