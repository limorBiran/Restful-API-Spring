package Limor.Almog.restApplication.assemblers;

import Limor.Almog.restApplication.controllers.CustomerController;
import Limor.Almog.restApplication.entities.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerEntityAssembler extends SimpleIdentifiableRepresentationModelAssembler<Customer> {
    public CustomerEntityAssembler() {
        super(CustomerController.class);
    }
}
