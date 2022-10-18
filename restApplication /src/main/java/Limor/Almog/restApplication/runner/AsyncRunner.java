package Limor.Almog.restApplication.runner;

import Limor.Almog.restApplication.entities.TvShow;
import Limor.Almog.restApplication.repositories.TvShowRepo;
import Limor.Almog.restApplication.services.TvShowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * This class works by async way, and creates tv shows from the API.
 */

@Component
@EnableAsync
public class AsyncRunner implements CommandLineRunner {
    private final TvShowService tvShowService;
    private final TvShowRepo tvShowRepo;
    private final ArrayList<Long> tvShowsIdsList = new ArrayList(List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L));
    private final List<CompletableFuture<TvShow>> tvShowsList = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(AsyncRunner.class);

    public AsyncRunner(TvShowService tvShowService, TvShowRepo tvShowRepo) {
        this.tvShowService = tvShowService;
        this.tvShowRepo = tvShowRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        CompletableFuture<TvShow>[] taskArray = new CompletableFuture[tvShowsIdsList.size()];
        int i=0;
        for(Long id: tvShowsIdsList){
            CompletableFuture<TvShow> tvShowCompletableFuture = tvShowService.getTvShowDetails(id);
            tvShowsList.add(tvShowCompletableFuture);
            taskArray[i++] = tvShowCompletableFuture;
            CompletableFuture.allOf(tvShowCompletableFuture).join();
        }

        CompletableFuture.allOf(taskArray);

        for(i=0; i < tvShowsIdsList.size(); i++){
            TvShow tvShow = taskArray[i].get();
            tvShowRepo.save(tvShow);
            logger.info("Tv show " + i + " = " + tvShow);
        }
    }
}
