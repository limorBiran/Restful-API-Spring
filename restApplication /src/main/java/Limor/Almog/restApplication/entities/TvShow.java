package Limor.Almog.restApplication.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "Tv")
public class TvShow {
    @Id
    @GeneratedValue
    private Long tvShowId;
    private Long id;
    private String name;
    private String type;
    private String language;
    private String premiered;

    @JsonIgnore
    @ManyToMany(mappedBy = "tvShowList", cascade = CascadeType.ALL)
    private List<OrderTvShows> orderTvShows = new ArrayList<>();

    public TvShow(Long id, String name, String type, String language, String premiered) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.language = language;
        this.premiered = premiered;
    }
}
