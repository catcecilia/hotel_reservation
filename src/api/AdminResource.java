package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class AdminResource {

    private static AdminResource one_instance = null;

    public Customer getCustomer(String email){
        try {
            return CustomerService.getInstance().getCustomer(email);
        } catch(Exception e) {
            return null;
        }
    }

    public void addRoom(List<IRoom> rooms){
        try {
            for(IRoom room: rooms){
                ReservationService.getInstance().addRoom(room);
            }
        } catch(Exception e) {}
    }

    public Collection<IRoom> getAllRooms(){
        try {
            return ReservationService.getInstance().getAllRooms();
        } catch(Exception e) {
            return null;
        }
    }

    public Collection<Customer> getAllCustomers(){
        try {
            return CustomerService.getInstance().getAllCustomers();
        } catch(Exception e) {
            return null;
        }
    }


    public void displayAllReservations(){
        try{
            ReservationService.getInstance().printAllReservation();
        } catch(Exception e){}
    }

    //static reference to check if this is the first time an instance is being created
    public static AdminResource getInstance() {
        if (one_instance == null) {
            one_instance = new AdminResource();
        }

        return one_instance;
    }
}
