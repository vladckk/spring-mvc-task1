package by.iba.gomel.controller;

import by.iba.gomel.Movie;
import by.iba.gomel.MovieService;
import by.iba.gomel.exceptions.AlreadyExistFilmException;
import by.iba.gomel.exceptions.FilmNotFoundException;
import by.iba.gomel.exceptions.IncorrectFilmException;
import by.iba.gomel.exceptions.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * This class works with http requests
 */
@Controller
public class FilmController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilmController.class);

    @Autowired
    private MovieService service;

    /**
     * Loads home page with table full of movies
     */
    @GetMapping("/")
    public String getFilmPage(Model model) {
        List<Movie> movies = service.findAll();
        LOGGER.info(movies.toString());
        model.addAttribute("movieList", movies);
        return "home";
    }

    /**
     * loads page for adding new movie to storage
     */
    @GetMapping("/add")
    public String addNewFilmPage(Model model) {
        return "add";
    }

    /**
     * Gets new data from form and adds it to the storage
     */
    @PostMapping("/add")
    public String addNewFilm(@RequestParam(required=false) String title, @RequestParam(required=false) Integer year,
                             @RequestParam(required=false) String director, @RequestParam(required=false) String genres,
                             @RequestParam(required=false) String actors) {
        if (title == null || year == null || director == null || genres == null || actors == null) {
            LOGGER.info("Validation");
            throw new ValidationException();
        }
        List<Movie> list = service.findByDirector(director);
        boolean check = list.stream().filter(m -> m.getTitle().equals(title)).findFirst().isEmpty();
        if (!check) {
            LOGGER.info("Film already exists");
            throw new AlreadyExistFilmException();
        }
        int currentYear = LocalDate.now().getYear();
        if (year < 1850 || year > currentYear + 50) {
            LOGGER.info("Incorrect film");
            throw new IncorrectFilmException();
        }
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setYear(year);
        movie.setDirector(director);
        String[] genre = genres.split(",");
        String[] actor = actors.split(",");
        for (int i = 0; i < genre.length; i++) {
            genre[i] = genre[i].trim();
        }
        for (int i = 0; i < actor.length; i++) {
            actor[i] = actor[i].trim();
        }
        movie.setGenre(List.of(genre));
        movie.setActor(List.of(actor));
        service.save(movie);
        return "redirect:/";
    }

    /**
     * Deletes chosen movie from storage
     */
    @GetMapping("delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        Movie movie = service.findById(1l).orElse(null);
        if (movie == null) {
            throw new FilmNotFoundException();
        }
        service.delete(movie);
        return "redirect:/";
    }

    /**
     * Loads edit page with data of chosen movie
     * @param id film's id
     */
    @GetMapping("edit/{id}")
    public String editItem(@PathVariable Long id, Model model) {
        Movie movie = service.findById(id).get();
        model.addAttribute("movie", movie);
        List<String> genres = movie.getGenre();
        StringBuilder sb = new StringBuilder();
        for (String genre : genres) {
            sb.append(genre);
            sb.append(", ");
        }
        sb.deleteCharAt(sb.length() - 2);
        model.addAttribute("genres", sb.toString());
        sb = new StringBuilder();
        List<String> actors = movie.getActor();
        for (String actor : actors) {
            sb.append(actor);
            sb.append(", ");
        }
        sb.deleteCharAt(sb.length() - 2);
        model.addAttribute("actors", sb.toString());
        return "edit";
    }

    /**
     * Switching new data with old
     */
    @PostMapping("edit/{id}")
    public String loadEditedItem(@PathVariable Long id, @RequestParam String title, @RequestParam Integer year,
                                 @RequestParam String director, @RequestParam String genres,
                                 @RequestParam String actors) {
        Movie movie = service.findById(id).get();
        movie.setTitle(title);
        movie.setYear(year);
        movie.setDirector(director);
        String[] genre = genres.split(",");
        String[] actor = actors.split(",");
        for (int i = 0; i < genre.length; i++) {
            genre[i] = genre[i].trim();
        }
        for (int i = 0; i < actor.length; i++) {
            actor[i] = actor[i].trim();
        }
        movie.setGenre(List.of(genre));
        movie.setActor(List.of(actor));
        service.save(movie);
        LOGGER.info(service.findAll().toString());
        return "redirect:/";
    }
}
