package Limor.Almog.restApplication.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "OrderTvShows")
public class OrderTvShows {
    @Id
    @GeneratedValue
    private Long id;
    private Double price;
    private String orderDate;
    private int countOfProducts;
    @ManyToOne
    private Customer customer;
    @JoinTable(
            name = "tv_show",
            joinColumns = {@JoinColumn(name = "orderId")},
            inverseJoinColumns = {@JoinColumn(name = "tvShowId")})
    @ManyToMany
    private List<TvShow> tvShowList = new ArrayList<>();

    public OrderTvShows(Double price, String orderDate, Customer customer, List<TvShow> tvShowList, int countOfProducts) {
        this.price = price;
        this.orderDate = orderDate;
        this.customer = customer;
        this.tvShowList = tvShowList;
        this.countOfProducts = countOfProducts;
    }
}
