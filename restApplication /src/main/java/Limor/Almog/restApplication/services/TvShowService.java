package Limor.Almog.restApplication.services;

import Limor.Almog.restApplication.entities.TvShow;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class TvShowService {
    private final RestTemplate restTemplate;

    public TvShowService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    /**
     * Using @Async annotation and CompletableFuture
     * Send HTTP GET request to a remote REST endpoint and get a JSON representing the tv show.
     * @param id
     */
    @Async
    public CompletableFuture<TvShow> getTvShowDetails(Long id) {
        String urlTemplate = String.format("https://api.tvmaze.com/shows/%s", id);
        TvShow tvShow = this.restTemplate.getForObject(urlTemplate, TvShow.class);
        /*
         return a CompletableFuture<BookInfo> when the computation is done
         this goes hand-with-hand with the join() method
         */

        return CompletableFuture.completedFuture(tvShow);
    }
}
