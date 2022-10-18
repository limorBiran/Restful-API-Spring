package Limor.Almog.restApplication.exceptions;


public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(Long id) {
        super("Customer with this id: " + id + " do not exist");
    }

    public CustomerNotFoundException(String str) {
        super("Customer with this: " + str + " do not exist");
    }
}
