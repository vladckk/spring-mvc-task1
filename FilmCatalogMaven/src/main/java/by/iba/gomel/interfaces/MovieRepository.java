package by.iba.gomel.interfaces;

import by.iba.gomel.Movie;

import java.util.List;

public interface MovieRepository extends AbstractRepository<Movie>{
    List<Movie> findByDirector(String director);
}
