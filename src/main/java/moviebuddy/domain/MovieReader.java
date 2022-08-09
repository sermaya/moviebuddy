package moviebuddy.domain;

import moviebuddy.domain.Movie;

import java.util.List;

public interface MovieReader {
    List<Movie> loadMovies();
}
