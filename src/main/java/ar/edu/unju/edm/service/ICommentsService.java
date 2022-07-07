package ar.edu.unju.edm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.unju.edm.model.Comment;
import ar.edu.unju.edm.model.MovieUserTicket;



@Service
public interface ICommentsService {
	public void saveComment(Comment comment); 
	public void deleteComment(Integer id) throws Exception; // no aplica
	public void modComment(MovieUserTicket ticket); // no aplica
	public List<Comment> showComments(); 
	public Comment findComment(Integer id) throws Exception; // no aplica
	public List<Comment> findByMovieId(Integer id);
	public List<Comment> findByUserId(Integer id);
}
