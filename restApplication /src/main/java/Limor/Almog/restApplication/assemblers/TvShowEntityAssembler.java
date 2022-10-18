package Limor.Almog.restApplication.assemblers;

import Limor.Almog.restApplication.controllers.TvShowController;
import Limor.Almog.restApplication.entities.TvShow;
import org.springframework.stereotype.Component;

@Component
public class TvShowEntityAssembler extends SimpleIdentifiableRepresentationModelAssembler<TvShow> {
    public TvShowEntityAssembler() {
        super(TvShowController.class);
    }
}
