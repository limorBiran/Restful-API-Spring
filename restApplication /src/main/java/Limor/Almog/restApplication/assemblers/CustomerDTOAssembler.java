package Limor.Almog.restApplication.assemblers;

import Limor.Almog.restApplication.controllers.CustomerController;
import Limor.Almog.restApplication.dto.CustomerDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerDTOAssembler implements SimpleRepresentationModelAssembler<CustomerDTO> {
    @Override
    public void addLinks(EntityModel<CustomerDTO> resource) {
        resource.add(linkTo(methodOn(CustomerController.class).getCustomerById(resource.getContent().getId())).withRel("single customer"));
        resource.add(linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("all customers"));
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<CustomerDTO>> resources) {
        resources.add(linkTo(methodOn(CustomerController.class).getAllCustomers()).withSelfRel());
    }

    @Override
    public EntityModel<CustomerDTO> toModel(CustomerDTO customerDTO) {
        return EntityModel.of(customerDTO,
                linkTo(methodOn(CustomerController.class).getCustomerById(customerDTO.getId())).withSelfRel(),
                linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("back all customers"));
    }
}
