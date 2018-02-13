package tnmk.ln.test.algorithm.graph.exercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author khoi.tran on 3/29/17.
 */
public class Movie {
    public Movie(int movieId, int rate, Movie... relatedMovies) {
        this.movieId = movieId;
        this.rate = rate;
        this.relatedMovies = new ArrayList<>(Arrays.asList(relatedMovies));
    }

    private int movieId;
    private int rate;
    private List<Movie> relatedMovies;

    @Override
    public String toString() {
        return "Movie<" + movieId + ">-" + rate;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public List<Movie> getRelatedMovies() {
        return relatedMovies;
    }

    public void setRelatedMovies(List<Movie> relatedMovies) {
        this.relatedMovies = relatedMovies;
    }
}
