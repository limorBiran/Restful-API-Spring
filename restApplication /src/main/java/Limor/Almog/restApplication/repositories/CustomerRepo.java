package Limor.Almog.restApplication.repositories;

import Limor.Almog.restApplication.entities.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * This class is a Data Access Layer (DAL).
 * extends CrudRepository
 */
public interface CustomerRepo extends CrudRepository<Customer, Long> {

    List<Customer> findByFirstName(String firstName);

    List<Customer> findByLastName(String firstName);

    Optional<Customer> findByPhoneNumber(String firstName);

    List<Customer> findByEmail(String email);
}
