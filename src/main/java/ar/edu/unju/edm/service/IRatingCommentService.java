package ar.edu.unju.edm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.unju.edm.model.RatingComment;


@Service
public interface IRatingCommentService {
	public void saveRatingComment(RatingComment ticket); 
	public void deleteRatingComment(Integer id) throws Exception; 		// no aplica
	public void modRatingComment(RatingComment ticket); 					// no aplica
	public List<RatingComment> showRatingComments(); 
	public RatingComment findRatingComment(Integer id) throws Exception; 	// no aplica
	public List<RatingComment> findByUserId(Integer id);
	public List<RatingComment> findByMovieId(Integer id);
	public RatingComment findByMovieUserId(Integer idUser, Integer idMovie);
}
