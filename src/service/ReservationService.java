package service;

//ReservationService contains at least one of each: public, private, default

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {

    //declaration of variables
    private static ReservationService one_instance = null;

    //creates HashMap collection of all rooms with the key being a string value (roomId), and IRoom being values
    private static HashMap<String, IRoom> allRooms = new HashMap<String, IRoom>();

    //list of reservations
    List<Reservation> listOfReservations = new LinkedList<Reservation>();

    //Hashmap where key is roomID, collection of reservations are the values
    private static HashMap<String, List<Reservation>> allReservations = new HashMap<String, List<Reservation>>();


    //method to add room to hotel application
    public void addRoom(IRoom room) throws Exception {
        try {
            allRooms.put(numberCheck(room.getRoomNumber()), room);
        } catch(Exception e) {
            System.out.print(e.getMessage());
        }
    }

    //gets all rooms
    public Collection<IRoom> getAllRooms() {
        return allRooms.values();
    }

    //method that finds room by room number and returns the details of room number, price, & type
    public IRoom getARoom(String roomId) throws Exception {
        if (allRooms.containsKey(roomId)){
            return allRooms.get(roomId);
        }

        //Throws exception if not found
        throw new Exception("Room not found \n");
    }


    //method that creates a new Reservation object that is ready to be set to a key
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        //creates object that is going to be added in the listofreservations within allReservations
        Reservation newReservation = new Reservation(customer, room, checkInDate, checkOutDate);

        //variable list of reservations is created for that room after providing key (room number)
        List<Reservation> roomReservationList = allReservations.get(room.getRoomNumber());

        //checking to see if there is a value for the list
        if (roomReservationList == null){
            roomReservationList = new LinkedList<>();
            roomReservationList.add(newReservation);
            //adds list to HashMap since there was none prior
            allReservations.put(room.getRoomNumber(), roomReservationList);
        } else {
            roomReservationList.add(newReservation);
        }

        return newReservation;
    }


    //method that finds room based on date availability
    public List<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        List<IRoom> availableRooms = new LinkedList<>();

        //check every room in repository
        allRooms.forEach( (key, value) -> {

            //create an overlap checker
            boolean isOverlap = false;

            //create list of reservations
            List<Reservation> reservations = allReservations.get(key);

            if (reservations == null) {
                availableRooms.add(value);
                return;
            }

            for (Reservation reservation: reservations) {
                //determine if checkout date is in between already reserved checkin & checkout dates OR checkin date is in between already reserved checkin & checkout dates
                if (
                        ((checkOutDate.compareTo(reservation.checkInDate) >= 0) && (checkOutDate.compareTo(reservation.checkOutDate) <= 0)) // New checkout date is inside an existing reservation
                        || ((checkInDate.compareTo(reservation.checkInDate) >=0 ) && (checkInDate.compareTo(reservation.checkOutDate) <= 0)) // New checkin date is inside an existing reservation
                        || ((checkInDate.compareTo(reservation.checkInDate) <= 0) && (checkOutDate.compareTo(reservation.checkOutDate) >= 0)) // New reservation completely overlaps an existing reservation
                ) {
                    //if overlap is found, break for loop and assign true value for overlap checker
                    isOverlap = true;
                    break;
                }
            }

            //if there is no overlap, add the IRoom object into collection of list
            if (!isOverlap) {
                availableRooms.add(value);
            }
        });
        return availableRooms;
    }


    //method that finds reservation using Customer's name
    public List<Reservation> getCustomersReservation(Customer customer){
        List<Reservation> reservationSearchResultByName = new LinkedList<Reservation>();

        allReservations.forEach( (key, value) -> {

            for (Reservation reservation: value) {

                //determine if customer is found in each reservation and add to the result list
                if (reservation.customer.equals(customer)){
                    reservationSearchResultByName.add((Reservation) value);
                }
            }
        });

        return reservationSearchResultByName;
    }

    //method that makes allReservations collection be represented as a list and printed out
    public void printAllReservation(){
        if (allReservations.isEmpty()) {
            System.out.println("No reservations in the system \n");
        } else {
            allReservations.forEach((key, value) ->
                System.out.println(value.toString().replace("[","").replace("]","").replace(", ","")));
        }
    }

    // default modifier method to check if the roomNumber ID is an actual number
    String numberCheck(String roomNumber) throws Exception{
        String reGex = "(0-9)";
        while(true){
            if(roomNumber.matches(reGex)) {
                break;
            } else {
                //throws an exception when it's not a number
                throw new Exception("Unable to add room(s), room number " + roomNumber + " is not a number \n");
            }
        }
        return roomNumber;

    }

    //static reference
    public static ReservationService getInstance() {
        if (one_instance == null) {
            one_instance = new ReservationService();
        }

        return one_instance;
    }

}
