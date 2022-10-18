package Limor.Almog.restApplication.assemblers;

import Limor.Almog.restApplication.controllers.OrderTvShowController;
import Limor.Almog.restApplication.dto.OrderTvShowsDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderTvShowsDTOAssembler implements SimpleRepresentationModelAssembler<OrderTvShowsDTO> {
    @Override
    public void addLinks(EntityModel<OrderTvShowsDTO> resource) {
        resource.add(linkTo(methodOn(OrderTvShowController.class).getOrderById(resource.getContent().getId())).withRel("single Order"));
        resource.add(linkTo(methodOn(OrderTvShowController.class).getAllOrders()).withRel("all Orders"));
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<OrderTvShowsDTO>> resources) {
        resources.add(linkTo(methodOn(OrderTvShowController.class).getAllOrders()).withSelfRel());
    }

    @Override
    public EntityModel<OrderTvShowsDTO> toModel(OrderTvShowsDTO orderTvShowsDTO) {
        return EntityModel.of(orderTvShowsDTO,
                linkTo(methodOn(OrderTvShowController.class).getOrderById(orderTvShowsDTO.getId())).withSelfRel(),
                linkTo(methodOn(OrderTvShowController.class).getAllOrders()).withRel("back all Orders"));
    }
}
