package Limor.Almog.restApplication.dto;

import Limor.Almog.restApplication.entities.OrderTvShows;
import Limor.Almog.restApplication.entities.TvShow;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;

import java.util.List;

@Value
@JsonIgnoreProperties({"type", "language", "orderTvShows"})
@JsonPropertyOrder({"id", "name"})
public class TvShowDTO {
    @JsonIgnore
    TvShow tvShow;

    public Long getId() {return this.tvShow.getTvShowId(); }

    public String getName() { return this.tvShow.getName(); }

    public String getType() { return this.tvShow.getType(); }

    public String getLanguage() { return this.tvShow.getLanguage(); }

    public List<OrderTvShows> getOrderTvShows() { return this.tvShow.getOrderTvShows(); }
}