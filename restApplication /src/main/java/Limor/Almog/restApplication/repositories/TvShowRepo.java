package Limor.Almog.restApplication.repositories;

import Limor.Almog.restApplication.entities.TvShow;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * This class is a Data Access Layer (DAL).
 * extends CrudRepository
 */
public interface TvShowRepo extends CrudRepository<TvShow, Long> {

    Optional<TvShow> findByName(String name);

    List<TvShow> findByType(String type);

    List<TvShow> findByLanguage(String language);
}
