package Limor.Almog.restApplication.db;

import Limor.Almog.restApplication.entities.Customer;
import Limor.Almog.restApplication.entities.OrderTvShows;
import Limor.Almog.restApplication.entities.TvShow;
import Limor.Almog.restApplication.repositories.CustomerRepo;
import Limor.Almog.restApplication.repositories.OrderTvShowRepo;
import Limor.Almog.restApplication.repositories.TvShowRepo;
import Limor.Almog.restApplication.services.TvShowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

/**
 * This class represents our Data Base.
 */
@Configuration
public class SeedDB {
    private static final Logger logger = LoggerFactory.getLogger(SeedDB.class);
    private TvShowService tvShowService;

    public SeedDB(TvShowService tvShowService) {
        this.tvShowService = tvShowService;
    }

    @Bean
    CommandLineRunner initDataBase(TvShowRepo tvShowRepo, OrderTvShowRepo orderTvShowRepo, CustomerRepo customerRepo) throws ExecutionException, InterruptedException {

        TvShow tvShow1 = tvShowRepo.save(tvShowService.getTvShowDetails(1L).get());
        TvShow tvShow2 = tvShowRepo.save(tvShowService.getTvShowDetails(2L).get());
        TvShow tvShow3 = tvShowRepo.save(tvShowService.getTvShowDetails(3L).get());
        TvShow tvShow4 = tvShowRepo.save(tvShowService.getTvShowDetails(4L).get());
        TvShow tvShow5 = tvShowRepo.save(tvShowService.getTvShowDetails(5L).get());
        TvShow tvShow6 = tvShowRepo.save(tvShowService.getTvShowDetails(6L).get());
        return args -> {
            Customer limor = customerRepo.save(new Customer("Limor", "Biran", "0525381648", "limorbiran72@gmail.com"));
            logger.info("saved:  " + limor);
            Customer almog = customerRepo.save(new Customer("Almog", "Anconina", "0502662989", "almogAnconina@gmail.com"));
            logger.info("saved: " + almog);
            logger.info("saved: " + customerRepo.save
                    (new Customer("Limor", "Almog", "0509394560", "limorAlmog@gmail.com")));

            logger.info("saved: " + orderTvShowRepo.save
                    (new OrderTvShows(80.5, "21.2.2022",
                            customerRepo.save(new Customer("Yaron", "Cohen", "0509394560", "limorAlmog@gmail.com")),
                            new ArrayList<>(Arrays.asList(tvShow2, tvShow3, tvShow4, tvShow5)), 4)));
            logger.info("saved: " + orderTvShowRepo.save
                    (new OrderTvShows(52.0, "13.4.2019",
                            customerRepo.save(new Customer("Daniel", "Revach", "0545567924", "Dani@gmail.com")),
                            new ArrayList<>(Arrays.asList(tvShow1, tvShow5, tvShow3)), 3)));
            logger.info("saved: " + orderTvShowRepo.save
                    (new OrderTvShows(92.3, "17.10.2020", limor,
                            new ArrayList<>(Arrays.asList(tvShow1, tvShow6, tvShow3)), 3)));
            logger.info("saved: " + orderTvShowRepo.save
                    (new OrderTvShows(16.3, "30.8.2021", limor,
                            new ArrayList<>(Arrays.asList(tvShow2)), 1)));
            logger.info("saved: " + orderTvShowRepo.save
                    (new OrderTvShows(152.4, "18.9.2022", limor,
                            new ArrayList<>(Arrays.asList(tvShow4,tvShow5)), 2)));
            logger.info("saved: " + orderTvShowRepo.save
                    (new OrderTvShows(200.5, "21.6.2022", almog,
                            new ArrayList<>(Arrays.asList(tvShow1,tvShow2,tvShow3,tvShow4)), 4)));
            logger.info("saved: " + orderTvShowRepo.save
                    (new OrderTvShows(40.7, "12.10.2021", almog,
                            new ArrayList<>(Arrays.asList(tvShow6,tvShow5)), 2)));
        };
    }
}
