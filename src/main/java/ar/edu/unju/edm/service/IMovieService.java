package ar.edu.unju.edm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.unju.edm.model.Movie;

@Service
public interface IMovieService {
	public void saveMovie(Movie movie);
	public void deleteMovie(int id) throws Exception;
	public void modMovie(Movie movie);
	public List<Movie> showMovies(); 
	public Movie findMovie(int id) throws Exception; 
}
