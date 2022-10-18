package Limor.Almog.restApplication.repositories;

import Limor.Almog.restApplication.entities.OrderTvShows;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

/**
 * This class is a Data Access Layer (DAL).
 * extends CrudRepository
 */
public interface OrderTvShowRepo extends CrudRepository<OrderTvShows, Long> {

    List<OrderTvShows> findByPrice(Double price);

    List<OrderTvShows> findByOrderDate(String orderDate);

    List<OrderTvShows> findByCountOfProducts(int count);

    List<OrderTvShows> findByPriceAndOrderDate(Double price, String orderDate);
}