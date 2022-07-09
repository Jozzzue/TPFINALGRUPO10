package ar.edu.unju.edm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.unju.edm.model.Rating;




@Service
public interface IRatingService {
	public void saveRating(Rating rating); 
	public void deleteRating(Integer id) throws Exception; // no aplica
	public void modRating(Rating rating); // no aplica
	public List<Rating> showRatings(); 
	public Rating findRating(Integer id) throws Exception; // no aplica
	public List<Rating> findByMovieId(Integer id);
	public List<Rating> findByUserId(Integer id);
}
