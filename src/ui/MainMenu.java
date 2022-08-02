package ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;


public class MainMenu {
    public static void runMainMenu() {
        //scanner is added for user's decisions
        Scanner input = new Scanner(System.in);

        while (true) {

            System.out.println("Welcome to the Hotel Reservation Application " +
                    "\n \n" +
                    "------------------------------------------- \n" +
                    "1. Find and reserve a room \n" +
                    "2. See my reservations \n" +
                    "3. Create an Account \n" +
                    "4. Admin \n" +
                    "5. Exit \n" +
                    "------------------------------------------- \n" +
                    "Please select a number for the menu option");

            String userInputString = input.next();

            while (true){
                if (userInputString.equals("1")) {
                    findAndReserveARoom(input);
                    break;
                } else if (userInputString.equals("2")) {
                    seeMyReservations(input);
                    break;
                } else if (userInputString.equals("3")) {
                    try{
                        createAnAccount(input);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                } else if (userInputString.equals("4")) {
                    AdminMenu.runAdminMenu(input);
                    break;
                } else if (userInputString.equals("5")) {
                    System.out.println("Good bye.");
                    break;
                } else {
                    System.out.println("Please select a number for the menu option");
                    userInputString = input.next();
                }
            }
            if (userInputString.equals("5")){
                break;
            }
        }
    }

    public static void findAndReserveARoom(Scanner input){
        Date checkIn, checkOut;

        System.out.println("Enter Check In Date mm/dd/yyyy example 08/01/2022");
        while (true) {
            String inputOne = input.next();

            try {
                checkIn = new SimpleDateFormat("MM/dd/yyyy").parse(inputOne);
                break;
            } catch (ParseException e) {
                System.out.println("Enter Check In Date mm/dd/yyyy example 08/01/2022");
            }
        }

        System.out.println("Enter Check Out Date mm/dd/yyyy example 08/05/2022");
        while (true) {
            String inputTwo = input.next();

            try {
                checkOut = new SimpleDateFormat("MM/dd/yyyy").parse(inputTwo);
                break;
            } catch (ParseException e) {
                System.out.println("Enter Check Out Date mm/dd/yyyy example 08/05/2022");
            }
        }

        Collection<IRoom> potentialRooms = HotelResource.getInstance().findARoom(checkIn,checkOut);
        if (potentialRooms.isEmpty()) {
            System.out.println("No rooms available for that date range, checking one week later \n");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(checkIn);
            calendar.add(Calendar.DAY_OF_MONTH, 7);
            checkIn = calendar.getTime();
            calendar.setTime(checkOut);
            calendar.add(Calendar.DAY_OF_MONTH, 7);
            checkOut = calendar.getTime();
            potentialRooms = HotelResource.getInstance().findARoom(checkIn, checkOut);
            if (potentialRooms.isEmpty()) {
                System.out.println("No available rooms \n");
                return;
            }
        }

        for(IRoom room: potentialRooms){
            System.out.println(room);
        }
        System.out.println("Would you like to book a room? y/n");
        if (getYesOrNo(input)) {
            while (true) {
                System.out.println("Do you have an account with us? y/n");
                if (getYesOrNo(input)) {
                    System.out.println("Enter email: format name@domain.com");
                    String customerEmail = input.next();
                    Customer customer;
                    try {
                        customer = HotelResource.getInstance().getCustomer(customerEmail);
                    } catch (Exception e) {
                        System.out.println("Email not found \n");
                        return;
                    }
                    selectARoomAndReserve(input, potentialRooms, customer.email, checkIn, checkOut);
                    return;
                } else {
                    Customer customer = createAnAccount(input);
                    if (customer != null) {
                        selectARoomAndReserve(input, potentialRooms, customer.email, checkIn, checkOut);
                        return;
                    }
                }
            }
        } else {
            return;
        }
    }

    private static void selectARoomAndReserve(Scanner input, Collection<IRoom> potentialRooms, String customerEmail, Date checkIn, Date checkOut) {
        while (true) {
            System.out.println("What room number would you like to reserve?");
            String roomNumber = input.next();
            try {
                IRoom room = HotelResource.getInstance().getRoom(roomNumber);
                if (potentialRooms.contains(room)) {
                    Reservation reservation = HotelResource.getInstance().bookARoom(customerEmail, room, checkIn, checkOut);
                    System.out.println(reservation);
                    return;
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Room number is invalid \n");
            }
        }
    }

    public static void seeMyReservations(Scanner input){
        System.out.println("Do you have an account with us? y/n");
        if (getYesOrNo(input)) {
            System.out.println("Enter email: format name@domain.com");
            String customerEmail = input.next();
            Customer customer;
            try {
                customer = HotelResource.getInstance().getCustomer(customerEmail);

            } catch (Exception e) {
                System.out.println("Account not found");
                return;
            }
            Collection<Reservation> reservations = null;
            try {
                reservations = HotelResource.getInstance().getCustomersReservations(customerEmail);
                if (reservations.isEmpty()) {
                    System.out.println("There are no reservations on file");
                }
            } catch (Exception e) {
                System.out.println("There are no reservations on file");
            }
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        } else {
            return;
        }
    }

    public static Customer createAnAccount(Scanner input) {
        while (true) {
            System.out.println("Enter email format: name@domain.com");
            String email = input.next();
            System.out.println("First Name");
            String firstName = input.next();
            System.out.println("Last Name");
            String lastName = input.next();

            try {
                HotelResource.getInstance().createACustomer(email, firstName, lastName);
                try {
                    return HotelResource.getInstance().getCustomer(email);
                } catch (Exception e) {
                    // Should never happen
                    return null;
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static boolean getYesOrNo(Scanner input) {
        while (true) {
            String answer = input.next();
            if(answer.equalsIgnoreCase("y")) {
                return true;
            } else if (answer.equalsIgnoreCase("n")){
                return false;
            } else {
                System.out.println("Please enter Yes or No y/n");
            }

        }
    }

    public static void main(String[] args) {
        //run the main menu
        runMainMenu();
    }




}
