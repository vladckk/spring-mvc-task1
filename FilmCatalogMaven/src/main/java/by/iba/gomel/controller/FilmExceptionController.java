package by.iba.gomel.controller;

import by.iba.gomel.exceptions.AlreadyExistFilmException;
import by.iba.gomel.exceptions.FilmNotFoundException;
import by.iba.gomel.exceptions.IncorrectFilmException;
import by.iba.gomel.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@ControllerAdvice
public class FilmExceptionController {

    @ExceptionHandler(FilmNotFoundException.class)
    public ModelAndView exception(HttpServletRequest req, FilmNotFoundException e) throws IOException {
        ModelAndView mav = new ModelAndView();
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        return mav;
    }

    @ExceptionHandler(AlreadyExistFilmException.class)
    public ResponseEntity<Object> exception(AlreadyExistFilmException e) {
        return new ResponseEntity<>("Can't add this film to the film catalog because of its existing", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectFilmException.class)
    public ResponseEntity<Object> exception(IncorrectFilmException e) {
        return new ResponseEntity<>("You typed the wrong information about the film, check out year of publishing of the new film", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> exception(ValidationException e) {
        return new ResponseEntity<>("Verify your input", HttpStatus.BAD_REQUEST);
    }
}
