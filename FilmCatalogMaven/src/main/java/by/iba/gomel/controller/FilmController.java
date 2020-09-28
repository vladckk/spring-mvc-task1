package by.iba.gomel.controller;

import by.iba.gomel.Movie;
import by.iba.gomel.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
     * @param model
     * @return
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
     * @param model
     * @return
     */
    @GetMapping("/add")
    public String addNewFilmPage(Model model) {
        return "add";
    }

    /**
     * Gets new data from form and adds it to the storage
     * @param title
     * @param year
     * @param director
     * @param genres
     * @param actors
     * @return
     */
    @PostMapping("/add")
    public String addNewFilm(@RequestParam(required=false) String title, @RequestParam(required=false) Integer year,
                             @RequestParam(required=false) String director, @RequestParam(required=false) String genres,
                             @RequestParam(required=false) String actors) {
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
     * @param id
     * @return
     */
    @GetMapping("delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        Movie movie = service.findById(id).get();
        service.delete(movie);
        return "redirect:/";
    }

    /**
     * Loads edit page with data of chosen movie
     * @param id
     * @param model
     * @return
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
     * @param id
     * @param title
     * @param year
     * @param director
     * @param genres
     * @param actors
     * @return
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
