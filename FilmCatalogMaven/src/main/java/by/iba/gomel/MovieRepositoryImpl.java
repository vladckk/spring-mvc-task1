package by.iba.gomel;

import by.iba.gomel.interfaces.MovieRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class that implements Movie class with storage of different movies
 */
public class MovieRepositoryImpl implements MovieRepository {

    private Map<Long, Movie> storage = new TreeMap<>();

    /**
     * Adding some movies in storage
     * @throws InterruptedException
     */
    public MovieRepositoryImpl() throws InterruptedException {
        Movie movie1 = new Movie();
        movie1.setTitle("The Green Mile");
        movie1.setDirector("Frank Darabont");
        movie1.setYear(1999);
        movie1.setGenre(List.of("fantasy", "drama"));
        movie1.setActor(List.of("Tom Hanks", "Michael Clarke Duncan", "David Morse", "Bonnie Hunt"));
        storage.put(movie1.getId(), movie1);
        Thread.sleep(1);
        Movie movie2 = new Movie();
        System.out.println("1. " + storage);
        movie2.setTitle("Forrest Gump");
        movie2.setDirector("Robert Zemeckis");
        movie2.setYear(1994);
        movie2.setGenre(List.of("comedy", "drama"));
        movie2.setActor(List.of("Tom Hanks", "Robin Wright", "Mykelti Williamson"));
        storage.put(movie2.getId(), movie2);
        System.out.println("2. " + storage);
        Thread.sleep(1);
        Movie movie3 = new Movie();
        movie3.setTitle("The Little Mermaid");
        movie3.setDirector("John Musker");
        movie3.setYear(1989);
        movie3.setGenre(List.of("animated", "musical", "fantasy"));
        movie3.setActor(List.of("Jodi Benson", "Pat Carroll", "Rene Auberjonois"));
        storage.put(movie3.getId(), movie3);
        System.out.println("3. " + storage);
    }

    @Override
    public List<Movie> findByDirector(String director) {
        return storage.values().stream().filter(movie -> movie.getDirector().equals(director)).collect(Collectors.toList());
    }

    @Override
    public Optional<Movie> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public void save(Movie movie) {
        storage.put(movie.getId(), movie);
    }

    @Override
    public void delete(Movie movie) {
        storage.remove(movie.getId());
    }

    @Override
    public List<Movie> findAll() {
        return List.copyOf(storage.values());
    }
}
