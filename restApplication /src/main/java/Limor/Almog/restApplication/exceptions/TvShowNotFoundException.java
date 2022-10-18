package Limor.Almog.restApplication.exceptions;

public class TvShowNotFoundException extends RuntimeException{
    public TvShowNotFoundException(Long id) {
        super("Tv show with this id: " + id + " do not exist");
    }

    public TvShowNotFoundException(String name) {
        super("Tv show with this name: " + name + " do not exist");
    }
}
