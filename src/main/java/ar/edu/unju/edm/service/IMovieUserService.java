package ar.edu.unju.edm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.unju.edm.model.MovieUser;


@Service
public interface IMovieUserService {
	public void saveMovieUser(MovieUser ticket); 
	public void deleteMovieUser(Integer id) throws Exception; 		// no aplica
	public void modMovieUser(MovieUser ticket); 					// no aplica
	public List<MovieUser> showMovieUsers(); 
	public MovieUser findMovieUser(Integer id) throws Exception; 	// no aplica
	public List<MovieUser> findByUserId(Integer id);
	public List<MovieUser> findByMovieId(Integer id);
	public List<MovieUser> findByMovieUserId(Integer idUser, Integer idMovie);
}
