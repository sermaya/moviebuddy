package moviebuddy.data;

import lombok.Getter;
import lombok.Setter;
import moviebuddy.ApplicationException;
import moviebuddy.MovieBuddyProfile;
import moviebuddy.domain.Movie;
import moviebuddy.domain.MovieReader;
import org.springframework.context.annotation.Profile;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Repository;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Profile(MovieBuddyProfile.XML_MODE)
@Repository
public class XmlMovieReader extends AbstractFileSystemMovieReader implements MovieReader {

    private final Unmarshaller unmarshaller;

    public XmlMovieReader(Unmarshaller unmarshaller) {
        this.unmarshaller = Objects.requireNonNull(unmarshaller);
    }

    @Override
    public List<Movie> loadMovies() {
        try {
            final InputStream content = ClassLoader.getSystemResourceAsStream(getMetadata());
            final Source source = new StreamSource(content);
            final MovieMetaData metaData = (MovieMetaData) unmarshaller.unmarshal(source);

            return metaData.toMovies();

        } catch (IOException error) {
            throw new ApplicationException("failed to load moives data", error);
        }
    }

    @Getter
    @Setter
    @XmlRootElement(name = "moviemetadata")
    public static class MovieMetaData {
        private List<MovieData> movies;

        public List<Movie> toMovies() {
            return movies.stream().map(MovieData::toMovie).collect(Collectors.toList());
        }
    }

    @Getter
    @Setter
    public static class MovieData {
        private String title;
        private List<String> genres;
        private String language;
        private String country;
        private int releaseYear;
        private String director;
        private List<String> actors;
        private URL imdbLink;
        private String watchedDate;

        public Movie toMovie() {
            String title = getTitle();
            List<String> genres = getGenres();
            String language = getLanguage();
            String country = getCountry();
            int releaseYear = getReleaseYear();
            String director = getDirector();
            List<String> actors = getActors();
            URL imdbLink = getImdbLink();
            String watchedDate = getWatchedDate();

            return Movie.of(title, genres, language, country, releaseYear,
                    director, actors, imdbLink,watchedDate);
        }
    }
}
