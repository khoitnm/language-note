package tnmk.ln.test.algorithm.graph.exercise;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author khoi.tran on 3/29/17.
 */
public class FindHighestRelatedMovies {
    public static final MovieRateComparator MOVIE_RATE_COMPARATOR = new MovieRateComparator();

    public Set<Movie> findTopRated(Movie movie, int topSize) {
        TreeSet<Movie> result = new TreeSet<>(MOVIE_RATE_COMPARATOR);
        Set<Integer> excluedMovieIds = new HashSet<>();
        addRelationsToTopRatedMovies(result, excluedMovieIds, movie, topSize);
        return result;
    }

    public void addRelationsToTopRatedMovies(TreeSet<Movie> result, Set<Integer> excluedMovieIds, Movie movie, int topSize) {
        System.out.println("Traverse " + movie);
        if (excluedMovieIds.contains(movie.getMovieId())) {
            return;
        }
        excluedMovieIds.add(movie.getMovieId());

        List<Movie> relatedMovies = movie.getRelatedMovies();
        for (Movie irelatedMovie : relatedMovies) {
            if (!excluedMovieIds.contains(irelatedMovie.getMovieId())) {
                result.add(irelatedMovie);
                if (result.size() > topSize) {
                    result.pollLast();
                }
            }
            addRelationsToTopRatedMovies(result, excluedMovieIds, irelatedMovie, topSize);
        }
    }

    public static class MovieRateComparator implements Comparator<Movie> {

        @Override
        public int compare(Movie o1, Movie o2) {
            if (o1 == null && o2 == null) return 0;
            if (o1 == null) return -1;
            if (o2 == null) return 1;
            return o2.getRate() - o1.getRate();
        }
    }
}
