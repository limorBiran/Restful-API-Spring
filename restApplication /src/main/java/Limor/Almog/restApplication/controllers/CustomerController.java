package Limor.Almog.restApplication.controllers;

import Limor.Almog.restApplication.entities.OrderTvShows;
import Limor.Almog.restApplication.repositories.CustomerRepo;
import Limor.Almog.restApplication.assemblers.CustomerDTOAssembler;
import Limor.Almog.restApplication.assemblers.CustomerEntityAssembler;
import Limor.Almog.restApplication.dto.CustomerDTO;
import Limor.Almog.restApplication.entities.Customer;
import Limor.Almog.restApplication.exceptions.CustomerNotFoundException;
import Limor.Almog.restApplication.repositories.OrderTvShowRepo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@EnableAsync
public class CustomerController {
    private final CustomerRepo customerRepo;
    private final OrderTvShowRepo orderTvShowRepo;
    public final CustomerEntityAssembler customerEntityAssembler;
    private final CustomerDTOAssembler customerDTOAssembler;

    public CustomerController(CustomerRepo customerRepo, OrderTvShowRepo orderTvShowRepo, CustomerEntityAssembler customerEntityAssembler, CustomerDTOAssembler customerDTOAssembler) {
        this.customerRepo = customerRepo;
        this.orderTvShowRepo = orderTvShowRepo;
        this.customerEntityAssembler = customerEntityAssembler;
        this.customerDTOAssembler = customerDTOAssembler;
    }

    /**
     * Get method - find the all costumers in the DB.
     * @return List<Customers>
     */
    @GetMapping("/customers")
    @Operation(summary = "Get all customers")
    public ResponseEntity<CollectionModel<EntityModel<Customer>>> getAllCustomers() {
        List<Customer> customersList = (List<Customer>) customerRepo.findAll();
        List<EntityModel<Customer>> entityModelList = customersList.stream().map(customer -> EntityModel.of(customer)).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(entityModelList, linkTo(methodOn(CustomerController.class).getAllCustomers()).withSelfRel()));
    }

    /**
     * Get method - find costumer by id
     * @param id
     * @return Customer
     */
    @GetMapping("/customers/{id}")
    @Operation(summary = "Get customer by id")
    public ResponseEntity<EntityModel<Customer>> getCustomerById(@PathVariable Long id) {
        return customerRepo.findById(id)
                .map(customerEntityAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    /**
     * Get method - show the all costumers' information from the DB.
     * @return List<CustomerDTO>
     */
    @GetMapping("/customers/info")
    @Operation(summary = "Get all customers' information")
    public ResponseEntity<CollectionModel<EntityModel<CustomerDTO>>> getAllCustomersInfo() {
        return ResponseEntity.ok(
                customerDTOAssembler.toCollectionModel(
                        StreamSupport.stream(customerRepo.findAll().spliterator(),
                                        false)
                                .map(CustomerDTO::new)
                                .collect(Collectors.toList())));
    }

    /**
     * Get method - show the costumers' information by id from the DB.
     * @param id
     * @return CustomerDTO
     */
    @GetMapping("/customers/{id}/info")
    @Operation(summary = "Get customer information by id")
    public ResponseEntity<EntityModel<CustomerDTO>> getCustomerInfoById(@PathVariable Long id) {
        return customerRepo.findById(id)
                .map(CustomerDTO::new)
                .map(customerDTOAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get method - find customers by first name
     * @param firstName
     * @return List<Customer>
     */
    @GetMapping("/customers/firstName/{firstName}")
    @Operation(summary = "Get customers by first name")
    public ResponseEntity<CollectionModel<EntityModel<Customer>>> getCustomerByFirstName(@PathVariable String firstName) {
        List<Customer> customerList = customerRepo.findByFirstName(firstName);
        List<EntityModel<Customer>> entityModelList = customerList.stream().map(customer -> EntityModel.of(customer)).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(entityModelList,
                linkTo(methodOn(CustomerController.class).getCustomerByFirstName(firstName)).withSelfRel(),
                linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("back to all customers")));
    }

    /**
     * Get method - find customers by last name
     * @param lastName
     * @return List<Customer>
     */
    @GetMapping("/customers/lastName/{lastName}")
    @Operation(summary = "Get customers by last name")
    public ResponseEntity<CollectionModel<EntityModel<Customer>>> getCustomerByLastName(@PathVariable String lastName) {
        List<Customer> customerList = customerRepo.findByLastName(lastName);
        List<EntityModel<Customer>> entityModelList = customerList.stream().map(customer -> EntityModel.of(customer)).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(entityModelList,
                linkTo(methodOn(CustomerController.class).getCustomerByLastName(lastName)).withSelfRel(),
                linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("back to all customers")));
    }

    /**
     * Get method - find customers by phone number
     * @param phoneNumber
     * @return Customer
     */
    @GetMapping("/customers/phoneNumber/{phoneNumber}")
    @Operation(summary = "Get customer by phone number")
    public ResponseEntity<EntityModel<Customer>> getCustomerByPhoneNumber(@PathVariable String phoneNumber) {
        Customer customer = customerRepo.findByPhoneNumber(phoneNumber).orElseThrow(() -> new CustomerNotFoundException(phoneNumber));
        return ResponseEntity.ok(EntityModel.of(customer,
                linkTo(methodOn(CustomerController.class).getCustomerByPhoneNumber(phoneNumber)).withSelfRel(),
                linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("back to all customers")));
    }

    /**
     * Get method - find customers by email
     * @param email
     * @return List<Customer>
     */
    @GetMapping("/customers/email/{email}")
    @Operation(summary = "Get customers by email")
    public ResponseEntity<CollectionModel<EntityModel<Customer>>> getCustomerByEmail(@PathVariable String email) {
        List<Customer> customersList = customerRepo.findByEmail(email);
        List<EntityModel<Customer>> entityModelList = customersList.stream().map(customer -> EntityModel.of(customer)).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(entityModelList,
                linkTo(methodOn(CustomerController.class).getCustomerByEmail(email)).withSelfRel(),
                linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("back to all customers")));
    }

    /**
     * Get method - find customers by full name
     * @param firstName
     * @param lastName
     * @return List<Customer>
     */
    @GetMapping("/customers/fullName")
    @Operation(summary = "Get customers by full name")
    @ResponseBody
    ResponseEntity<CollectionModel<EntityModel<Customer>>> getCustomersByFullName(@RequestParam String firstName, @RequestParam String lastName) {
        List<Customer> customersList = (List<Customer>) customerRepo.findAll();
        List<EntityModel<Customer>> entityModelList = new ArrayList<>();
        for (Customer customer : customersList) {
            if (customer.getFirstName().equals(firstName) && customer.getLastName().equals(lastName)) {
                entityModelList.add(EntityModel.of(customer));
            }
        }
        return ResponseEntity.ok(CollectionModel.of(entityModelList,
                linkTo(methodOn(CustomerController.class).getAllCustomers()).withSelfRel()));
    }

    /**
     * Get method - find customers by first name and email
     * @param firstName
     * @param email
     * @return List<Customer>
     */
    @GetMapping("/customers/details")
    @Operation(summary = "Get customers by first name and email")
    @ResponseBody
    ResponseEntity<CollectionModel<EntityModel<Customer>>> getCustomersByFirstNameAndEmail(@RequestParam String firstName, @RequestParam String email) {
        List<Customer> customersList = (List<Customer>) customerRepo.findAll();
        List<EntityModel<Customer>> entityModelList = new ArrayList<>();
        for (Customer customer : customersList) {
            if (customer.getFirstName().equals(firstName) && customer.getEmail().equals(email)) {
                entityModelList.add(EntityModel.of(customer));
            }
        }
        return ResponseEntity.ok(CollectionModel.of(entityModelList,
                linkTo(methodOn(CustomerController.class).getAllCustomers()).withSelfRel()));
    }

    /**
     * Get method - find all customers that paid more tha average price per product(tv show)
     * sum all orders costumers' price - and divided in orders' count of product (avg per product)
     * @return List<Customer>
     */
    @GetMapping("/customers/paidMoreThanAveragePricePerProduct")
    @Operation(summary = "Get customers that paid more tha average price per product")
    @ResponseBody
    ResponseEntity<CollectionModel<EntityModel<Customer>>> getAllCustomersThatPaidMoreThanAveragePricePerProduct() {
        Double totalPriceOfAllOrders = 0.0;
        int count = 0;
        List<Customer> customers = (List<Customer>) customerRepo.findAll();
        List<OrderTvShows> ordersTvShows = (List<OrderTvShows>) orderTvShowRepo.findAll();

        List<EntityModel<Customer>> entityModelList = new ArrayList<>();

        for (OrderTvShows orderTvShows : ordersTvShows){
            totalPriceOfAllOrders += orderTvShows.getPrice();
            count += orderTvShows.getCountOfProducts();
        }

        Double countDouble = (double) count;
        double avgOfPrices = totalPriceOfAllOrders/countDouble;
        for (Customer customer : customers) {
            totalPriceOfAllOrders = 0.0;
            count = 0;
            for (OrderTvShows orderTvShows : customer.getOrdersList()) {
                totalPriceOfAllOrders += orderTvShows.getPrice();
                count += orderTvShows.getCountOfProducts();
            }
            if (totalPriceOfAllOrders/(double)count >= avgOfPrices) {
                entityModelList.add(EntityModel.of(customer));
            }
        }
        return ResponseEntity.ok(CollectionModel.of(entityModelList,
                linkTo(methodOn(CustomerController.class).getAllCustomersInfo()).withSelfRel()));
    }

    /**
     * Delete method - delete customer by id.
     * @param id
     */
    @DeleteMapping("/customer/{id}")
    @Operation(summary = "Delete customer by id")
    void deleteCustomerById(@PathVariable Long id) {
        customerRepo.deleteById(id);
    }
}

