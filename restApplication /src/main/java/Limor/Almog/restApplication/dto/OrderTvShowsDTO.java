package Limor.Almog.restApplication.dto;

import Limor.Almog.restApplication.entities.Customer;
import Limor.Almog.restApplication.entities.OrderTvShows;
import Limor.Almog.restApplication.entities.TvShow;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;

import java.util.List;

@Value
@JsonIgnoreProperties({"tvShowList", "customer"})
@JsonPropertyOrder({"id", "price", "countOfProducts","orderDate"})
public class OrderTvShowsDTO {
    @JsonIgnore
    OrderTvShows orderTvShows;

    public Long getId() { return this.orderTvShows.getId(); }

    public Double getPrice() { return this.orderTvShows.getPrice(); }

    public int getCountOfProducts() { return this.orderTvShows.getCountOfProducts(); }

    public String getOrderDate() { return this.orderTvShows.getOrderDate(); }

    public List<TvShow> getTvShowList() { return this.orderTvShows.getTvShowList(); }

    public Customer getCustomer() { return this.orderTvShows.getCustomer(); }
}
