package ui;

import api.AdminResource;
import api.HotelResource;
import model.*;
import service.CustomerService;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AdminMenu {

    public static void runAdminMenu(Scanner input) {
        while (true) {

            System.out.println("Welcome to the Hotel Reservation Application " + "\n \n" +
                    "------------------------------------------- \n" +
                    "1. See all Customers \n" +
                    "2. See all Rooms \n" +
                    "3. See all Reservations \n" +
                    "4. Add a Room \n" +
                    "5. Back to Main Menu \n" +
                    "------------------------------------------- \n" +
                    "Please select a number for the menu option");

            String adminInputString = input.next();
            while (true) {
                //boolean is used to make sure that after all EXCEPT for invalid userinputs, there will be a break away from the while loop
                boolean invalidMenuOption = false;
                switch (adminInputString) {
                    case "1":
                        seeAllCustomers();
                        break;
                    case "2":
                        seeAllRooms();
                        break;
                    case "3":
                        seeAllReservations();
                        break;
                    case "4":
                        addARoom(input);
                        break;
                    case "5":
                        return;
                    default:
                        invalidMenuOption = true;
                        System.out.println("Please select a number for the menu option");
                        adminInputString = input.next();
                }
                if (!invalidMenuOption) {
                    break;
                }
            }

        }
    }

    private static void seeAllCustomers() {
        Collection<Customer> allCustomers = AdminResource.getInstance().getAllCustomers();

        for (Customer customer : allCustomers) {
            System.out.println(customer + "\n");
        }
    }

    private static void seeAllRooms() {
        Collection<IRoom> allRooms = AdminResource.getInstance().getAllRooms();

        for (IRoom room : allRooms) {
            System.out.println(room + "\n");
        }
    }

    private static void seeAllReservations(){
        AdminResource.getInstance().displayAllReservations();
    }
    private static void addARoom(Scanner input) {
        IRoom newRoom;
        String roomNumber;
        Double price;
        RoomType roomType;
        List<IRoom> listOfNewRooms = new LinkedList<>();
        while (true) {
            System.out.println("Enter room number");
            roomNumber = input.next();

            while (true) {
                System.out.println("Price");
                try {
                    price = Double.valueOf(input.next());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Value entered is not a number");
                }
            }

            while (true) {
                System.out.println("SINGLE or DOUBLE");
                String initialEnumSelection = input.next();

                //validate string
                if (initialEnumSelection.equalsIgnoreCase("SINGLE")) {
                    roomType = RoomType.SINGLE;
                    break;
                } else if (initialEnumSelection.equalsIgnoreCase("DOUBLE")) {
                    roomType = RoomType.DOUBLE;
                    break;
                }
            }

            if (price == 0.0) {
                newRoom = new FreeRoom(roomNumber, roomType);
            } else {
                newRoom = new Room(roomNumber, price, roomType);
            }

            Collection<IRoom> allRooms = AdminResource.getInstance().getAllRooms();
            boolean roomNumberExists = false;
            for (IRoom room : Stream.concat(allRooms.stream(), listOfNewRooms.stream()).collect(Collectors.toList())) {

                //uses hashcode
                if (room.equals(newRoom)) {
                    roomNumberExists = true;
                    break;
                }
            }

            if (!roomNumberExists) {
                listOfNewRooms.add(newRoom);
            } else {
                System.out.println("Room number " + roomNumber + " already exists \n");
            }

            System.out.println("Would you like to add another room? y / n");
            if (!getYesOrNo(input)) {
                AdminResource.getInstance().addRoom(listOfNewRooms);
                return;
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
}
