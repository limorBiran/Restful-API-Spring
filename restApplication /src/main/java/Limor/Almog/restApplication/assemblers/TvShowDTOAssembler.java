package Limor.Almog.restApplication.assemblers;

import Limor.Almog.restApplication.controllers.TvShowController;
import Limor.Almog.restApplication.dto.TvShowDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TvShowDTOAssembler implements SimpleRepresentationModelAssembler<TvShowDTO> {
    @Override
    public void addLinks(EntityModel<TvShowDTO> resource) {
        resource.add(linkTo(methodOn(TvShowController.class).getTvShowById(resource.getContent().getId())).withRel("single Tv show"));
        resource.add(linkTo(methodOn(TvShowController.class).getAllTvShows()).withRel("all Tv shows"));
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<TvShowDTO>> resources) {
        resources.add(linkTo(methodOn(TvShowController.class).getAllTvShows()).withSelfRel());
    }

    @Override
    public EntityModel<TvShowDTO> toModel(TvShowDTO tvShowDTO) {
        return EntityModel.of(tvShowDTO,
                linkTo(methodOn(TvShowController.class).getTvShowById(tvShowDTO.getId())).withSelfRel(),
                linkTo(methodOn(TvShowController.class).getAllTvShows()).withRel("back all Tv shows"));
    }
}

