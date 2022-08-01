package service;

import model.Customer;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class CustomerService {

   //create one single instance of CustomerService
    private static CustomerService one_instance = null;

    //declaration of Repository of Customers
    private static List<Customer> repositoryOfCustomers = new LinkedList<>();

    // method that adds Customer to Collection
    public void addCustomer(String email, String firstName, String lastName){

        //creates object Customer with the values
        Customer customer = new Customer(firstName, lastName, email);

        //adds this customer to the collection of customers
        repositoryOfCustomers.add(customer);
    }

    //method that gets Customer's first and last name after searching customer email
    public Customer getCustomer(String customerEmail) throws Exception {

            //iterating in the collection
            for (Customer customer : repositoryOfCustomers){

                //if customer's email contains the email being searched, return customer object
                if (customer.email.equals(customerEmail)) {
                    return customer;
                }
            }

            throw new Exception("Customer not found");
    }

    //method that gets all customers within the collection
    public Collection<Customer> getAllCustomers(){

        return repositoryOfCustomers;
    }

    //constructor is private due to singleton design
    private CustomerService() {}

    //static reference to check if this is the first time an instance is being created
    public static CustomerService getInstance() {
        if (one_instance == null) {
            one_instance = new CustomerService();
        }

        return one_instance;
    }
}
