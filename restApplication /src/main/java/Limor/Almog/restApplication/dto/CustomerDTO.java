package Limor.Almog.restApplication.dto;

import Limor.Almog.restApplication.entities.Customer;
import Limor.Almog.restApplication.entities.OrderTvShows;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;

import java.util.List;

@Value
@JsonIgnoreProperties({"email", "listOfOrders"})
@JsonPropertyOrder({"id", "firstName", "lastName","phoneNumber"})
public class CustomerDTO {
    @JsonIgnore
    Customer customer;

    public Long getId() { return this.customer.getId(); }

    public String getFirstName() { return this.customer.getFirstName(); }

    public String getLastName() { return this.customer.getLastName(); }

    public String getPhoneNumber() {
        return this.customer.getPhoneNumber();
    }

    public String getEmail() {
        return this.customer.getEmail();
    }

    public List<OrderTvShows> getListOfOrders(){
        return this.customer.getOrdersList();
    }
}
