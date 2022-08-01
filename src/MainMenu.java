import java.util.Scanner;

public class MainMenu {
    public static void runMainMenu(){
        System.out.println("Welcome to the Hotel Reservation Application " +
                "\n \n" +
                "------------------------------------------- \n" +
                "1. Find and reserve a room \n" +
                "2. See my reservations \n" +
                "3. Create an Account \n" +
                "4. Admin \n" +
                "5. Exit \n"+
                "------------------------------------------- \n" +
                "Please select a number for the menu option");
    }



    public static void main(String[] args) {
        //run the main menu
        runMainMenu();

        //scanner is added for user's decisions
        Scanner userInput = new Scanner(System.in);
        String userInputString = userInput.next();


        while(userInput != null){


            if( (userInputString.equals("1")) || (userInputString.equals("2")) || (userInputString.equals("3")) || (userInputString.equals("4")) || (userInputString.equals("5"))) {
                if(userInputString.equals("1")){
                    System.out.println("poop");
                    break;
                } else if (userInputString.equals("2")) {
                    System.out.println("pee");
                    break;
                } else if (userInputString.equals("3"))
                {
                    System.out.println("pee pee poop poop");
                    break;
                } else if (userInputString.equals("4")){
                    System.out.println("poop poop pee pee");
                    break;
                } else if (userInputString.equals("5")){

                    break;
                }
                userInput.close();
            } else {
                System.out.println("Please select a number for the menu option");
                userInputString = userInput.next();
            }
        }
    }




}
