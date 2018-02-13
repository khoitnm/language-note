package org.tnmk.ln.test.algorithm.graph;

import org.junit.Test;
import org.tnmk.ln.test.algorithm.graph.exercise.FindHighestRelatedMovies;
import org.tnmk.ln.test.algorithm.graph.exercise.Movie;

import java.util.Set;

/**
 * @author khoi.tran on 3/29/17.
 */
public class MovieTesting {

    @Test
    public void testTopRatedMovie() {
        FindHighestRelatedMovies findHighestRelatedMovies = new FindHighestRelatedMovies();
        Set<Movie> result = findHighestRelatedMovies.findTopRated(generatedData(), 3);
        System.out.println(result);
    }

    public static Movie generatedData() {
        Movie movie = new Movie(1, 100
                , new Movie(2, 12, new Movie(21, 90), new Movie(22, 17), new Movie(23, 15))
                , new Movie(3, 2, new Movie(31, 10), new Movie(32, 5), new Movie(33, 99))
        );
        movie.getRelatedMovies().get(0).getRelatedMovies().add(movie);
        movie.getRelatedMovies().get(1).getRelatedMovies().add(movie.getRelatedMovies().get(0));
        return movie;
    }

}
