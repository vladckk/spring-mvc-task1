package by.iba.gomel;

import by.iba.gomel.interfaces.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service give access to movie storage
 */
@Service
public class MovieService implements MovieRepository{

    private MovieRepositoryImpl repository = new MovieRepositoryImpl();

    public MovieService() throws InterruptedException {
    }

    @Override
    public Optional<Movie> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void save(Movie movie) {
        if (movie != null) {
            repository.save(movie);
        }
    }

    @Override
    public void delete(Movie movie) {
        if (movie != null) {
            repository.delete(movie);
        }
    }

    @Override
    public List<Movie> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Movie> findByDirector(String director) {
        return repository.findByDirector(director);
    }
}
