package Limor.Almog.restApplication.exceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(long id) {
        super("Order with this id: " + id + " do not exist");
    }
}
