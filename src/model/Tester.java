package model;

//import Customer class
import model.Customer;


public class Tester {
    public static void main(String[] args) {

        //testing if it works properly and passes
        Customer customer = new Customer("one", "two", "j@domain.com");
        System.out.println(customer);

        //this is failing
       try {
           Customer customer2 = new Customer("first", "second", "email");
       } catch (IllegalArgumentException exception) {
           //Display Customer class contents and if there is an error due to email not formatted properly
           System.out.println(exception);
       }



    }
}
