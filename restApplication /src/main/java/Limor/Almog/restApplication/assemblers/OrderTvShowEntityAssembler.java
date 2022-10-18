package Limor.Almog.restApplication.assemblers;

import Limor.Almog.restApplication.controllers.OrderTvShowController;
import Limor.Almog.restApplication.entities.OrderTvShows;
import org.springframework.stereotype.Component;

@Component
public class OrderTvShowEntityAssembler extends SimpleIdentifiableRepresentationModelAssembler<OrderTvShows> {
    public OrderTvShowEntityAssembler() {
        super(OrderTvShowController.class);
    }
}
