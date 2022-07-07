package ar.edu.unju.edm.service.imp;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unju.edm.model.Comment;

import ar.edu.unju.edm.model.MovieUserTicket;

import ar.edu.unju.edm.repository.CommentsRepository;
import ar.edu.unju.edm.service.ICommentsService;




@Service
public class ICommentServiceImp implements ICommentsService {


	@Autowired
	CommentsRepository commentRepository;
	
	
	
	@Override
	public void saveComment(Comment comment) {
		// TODO Auto-generated method stub
		commentRepository.save(comment);
	}

	@Override
	public void deleteComment(Integer id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modComment(MovieUserTicket ticket) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Comment> showComments() {
		// TODO Auto-generated method stub
		List<Comment> aux = new ArrayList<>();
		aux = (List<Comment>) commentRepository.findAll();
		return aux;
	}

	@Override
	public Comment findComment(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Comment> findByMovieId(Integer id) {
		// TODO Auto-generated method stub
		List<Comment> movieComments = new ArrayList<>();
		List<Comment> aux = new ArrayList<>();
		aux = (List<Comment>) commentRepository.findAll();
		for (int i=0; i<aux.size();i++) {
			if(aux.get(i).getMovie().getId() == id)
				movieComments.add(aux.get(i));
		}
		
		return movieComments;
	}

	@Override
	public List<Comment> findByUserId(Integer id) {
		// TODO Auto-generated method stub
		List<Comment> userComments = new ArrayList<>();
		List<Comment> aux = new ArrayList<>();
		aux = (List<Comment>) commentRepository.findAll();
		for (int i=0; i<aux.size();i++) {
			if(aux.get(i).getUser().getId() == id)
				userComments.add(aux.get(i));
		}
		
		return userComments;
	}

	

}
