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
                if (adminInputString.equals("1")) {
                    seeAllCustomers();
                    break;
                } else if (adminInputString.equals("2")) {
                    seeAllRooms();
                    break;
                } else if (adminInputString.equals("3")) {
                    seeAllReservations();
                    break;
                } else if (adminInputString.equals("4")) {
                    addARoom(input);
                    break;
                } else if (adminInputString.equals("5")) {
                    return;
                } else {
                    System.out.println("Please select a number for the menu option");
                    adminInputString = input.next();
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
            while (true) {
                System.out.println("Enter room number");
                roomNumber = input.next();

                Collection<IRoom> allRooms = AdminResource.getInstance().getAllRooms();
                boolean roomNumberExists = false;
                for (IRoom room : Stream.concat(allRooms.stream(), listOfNewRooms.stream()).collect(Collectors.toList())) {
                    if (room.getRoomNumber().equals(roomNumber)) {
                        System.out.println("Room number already exists \n");
                        roomNumberExists = true;
                        break;
                    }
                }
                if (!roomNumberExists) {
                    break;
                }
            }

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

            if (HotelResource.getInstance().getRoom(roomNumber) == null) {
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
            if(answer.equals("y") || answer.equals("Y")) {
                return true;
            } else if (answer.equals("n") || answer.equals("N")){
                return false;
            } else {
                System.out.println("Please enter Yes or No y/n");
            }

        }
    }
}
