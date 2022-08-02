package model;

import java.util.regex.Pattern;
public class Customer {

    //declaring variable strings for first name, last name, and email address for customer
    public String firstName;
    public String lastName;
    public String email;

    public Customer(String firstName, String lastName, String email) {
        //creation of validation format is: name@domain.com
        final String emailFormat = "^(.+)@(.+).com$";
        Pattern pattern = Pattern.compile(emailFormat);

        /* validating if email is formatted correctly, throws exception if not.
        * if validation passes, this particular instance will be part of Customer collection   */

        if (!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format. It must match name@domain.com. \n");
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer name is: " + firstName + " " + lastName + "\nEmail is: " + email;
    }
}
