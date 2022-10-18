package Limor.Almog.restApplication.controllers;

import Limor.Almog.restApplication.dto.TvShowDTO;
import Limor.Almog.restApplication.exceptions.TvShowNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import Limor.Almog.restApplication.repositories.TvShowRepo;
import Limor.Almog.restApplication.assemblers.TvShowDTOAssembler;
import Limor.Almog.restApplication.assemblers.TvShowEntityAssembler;
import Limor.Almog.restApplication.entities.TvShow;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@EnableAsync
public class TvShowController {
    private final TvShowRepo tvShowRepo;
    private final TvShowEntityAssembler tvShowEntityAssembler;
    private final TvShowDTOAssembler tvShowDTOAssembler;

    public TvShowController(TvShowRepo tvShowRepo, TvShowEntityAssembler tvShowEntityAssembler, TvShowDTOAssembler tvShowDTOAssembler) {
        this.tvShowRepo = tvShowRepo;
        this.tvShowEntityAssembler = tvShowEntityAssembler;
        this.tvShowDTOAssembler = tvShowDTOAssembler;
    }

    /**
     * Get method - find all Tv shows in the DB.
     * @return List<TvShow>
     */
    @GetMapping("/tvShows")
    @Operation(summary = "Get all Tv shows")
    public ResponseEntity<CollectionModel<EntityModel<TvShow>>> getAllTvShows() {
        List<TvShow> tvShowsList = (List<TvShow>) tvShowRepo.findAll();
        List<EntityModel<TvShow>> entityModelList = tvShowsList.stream().map(tvShow -> EntityModel.of(tvShow)).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(entityModelList, linkTo(methodOn(TvShowController.class).getAllTvShows()).withSelfRel()));
    }

    /**
     * Get method - Get tv show by id
     * @param id
     * @return TvShow
     */
    @GetMapping("/tvShows/{id}")
    @Operation(summary = "Get tv show by id")
    public ResponseEntity<EntityModel<TvShow>> getTvShowById(@PathVariable Long id) {
        return tvShowRepo.findById(id)
                .map(tvShowEntityAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElseThrow(() ->
                new TvShowNotFoundException(id));
    }

    /**
     * Get method - Get tv show by name
     * @param name
     * @return TvShow
     */
    @GetMapping("/tvShows/name/{name}")
    @Operation(summary = "Get tv show by name")
    public ResponseEntity<EntityModel<TvShow>> getTvShowByName(@PathVariable String name) {
        TvShow tvShow = tvShowRepo.findByName(name).orElseThrow(() ->
                new TvShowNotFoundException(name));
        return ResponseEntity.ok(EntityModel.of(tvShow,
                linkTo(methodOn(TvShowController.class).getTvShowByName(name)).withSelfRel(),
                linkTo(methodOn(TvShowController.class).getAllTvShows()).withRel("back to all TV shows")));
    }

    /**
     * Get method - Get tv show by type
     * @param type
     * @return List<TvShow>
     */
    @GetMapping("/tvShows/type/{type}")
    @Operation(summary = "Get tv shows by type")
    public ResponseEntity<CollectionModel<EntityModel<TvShow>>> getTvShowsByType(@PathVariable String type) {
        List<TvShow> tvShowsList = tvShowRepo.findByType(type);
        List<EntityModel<TvShow>> entityModelList = tvShowsList.stream().map(tvShow -> EntityModel.of(tvShow)).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(entityModelList,
                linkTo(methodOn(TvShowController.class).getTvShowsByType(type)).withSelfRel(),
                linkTo(methodOn(TvShowController.class).getAllTvShows()).withRel("back to all TV shows")));
    }

    /**
     * Get method - Get tv show by language
     * @param language
     * @return List<TvShow>
     */
    @GetMapping("/tvShows/language/{language}")
    @Operation(summary = "Get tv shows by language")
    public ResponseEntity<CollectionModel<EntityModel<TvShow>>> getTvShowsByLanguage(@PathVariable String language) {
        List<TvShow> tvShowsList = tvShowRepo.findByLanguage(language);
        List<EntityModel<TvShow>> entityModelList = tvShowsList.stream().map(tvShow -> EntityModel.of(tvShow)).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(entityModelList,
                linkTo(methodOn(TvShowController.class).getTvShowsByLanguage(language)).withSelfRel(),
                linkTo(methodOn(TvShowController.class).getAllTvShows()).withRel("back to all TV shows")));
    }

    /**
     * Get method - Get all tv shows information
     * @return List<TvShow>
     */
    @GetMapping("/tvShows/info")
    @Operation(summary = "Get all tv shows information")
    public ResponseEntity<CollectionModel<EntityModel<TvShowDTO>>> getAllTvShowsInfo() {
        return ResponseEntity.ok(
                tvShowDTOAssembler.toCollectionModel(
                        StreamSupport.stream(tvShowRepo.findAll().spliterator(),
                                false)
                                .map(TvShowDTO::new)
                                .collect(Collectors.toList())));
    }

    /**
     * Get method - Get tv shows information by id
     * @return TvShow
     */
    @GetMapping("/tvShows/{id}/info")
    @Operation(summary = "Get tv shows information by id")
    public ResponseEntity<EntityModel<TvShowDTO>> getTvShowInfoById(@PathVariable Long id) {
        return tvShowRepo.findById(id)
                .map(TvShowDTO::new)
                .map(tvShowDTOAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete method - delete tv show by id.
     * @param id
     */
    @DeleteMapping("/tvShows/{id}")
    @Operation(summary = "delete tv show by id")
    void deleteTvShow(@PathVariable Long id) {
        tvShowRepo.deleteById(id);
    }
}
