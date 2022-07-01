package ar.edu.unju.edm.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unju.edm.model.Movie;
import ar.edu.unju.edm.repository.MovieRepository;
import ar.edu.unju.edm.util.ListMovies;
import ar.edu.unju.edm.service.IMovieService;


@Service
public class IMovieServiceImp implements IMovieService {

	@Autowired
	ListMovies movies;
	@Autowired
	MovieRepository movieRepository;
	
	@Override
	public void saveMovie(Movie movie) {
		// TODO Auto-generated method stub
		movie.setStatus(true);
		movieRepository.save(movie);
	}
	@Override
	public void deleteMovie(int id) throws Exception {
		// TODO Auto-generated method stub
		Movie aux = new Movie();
		aux = findMovie(id);
		aux.setStatus(false);
		movieRepository.save(aux);
	}
	@Override
	public void modMovie(Movie movie) {
		// TODO Auto-generated method stub
		movieRepository.save(movie);
	}
	@Override
	public List<Movie> showMovies() {
		// TODO Auto-generated method stub
		List<Movie> aux = new ArrayList<>();
		List<Movie> aux2 = new ArrayList<>();
		aux = (List<Movie>) movieRepository.findAll();
		for (int i=0; i<aux.size();i++) {
			if(aux.get(i).getStatus()==true)
				aux2.add(aux.get(i));
		}
		return aux2;
	}
	@Override
	public Movie findMovie(int id) throws Exception {
		// TODO Auto-generated method stub
		Movie foundMovie = new Movie();
		for (int i=0; i < movies.getMovies().size(); i++) {
			if ( movies.getMovies().get(i).getId() == id )
				foundMovie = movies.getMovies().get(i);
		}
		
		foundMovie = movieRepository.findById(id).orElseThrow(()->new Exception("pelicula no encontrada"));
		
		
		return foundMovie;
	}	

}
