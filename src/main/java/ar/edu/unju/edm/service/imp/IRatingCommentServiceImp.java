package ar.edu.unju.edm.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import ar.edu.unju.edm.model.RatingComment;
import ar.edu.unju.edm.repository.RatingCommentRepository;
import ar.edu.unju.edm.service.IRatingCommentService;


@Service
public class IRatingCommentServiceImp implements IRatingCommentService {

	
	@Autowired
	RatingCommentRepository ratingCommentRepository;
	
	
	
	@Override
	public void saveRatingComment(RatingComment ticket) {
		// TODO Auto-generated method stub
		ratingCommentRepository.save(ticket);
	}

	@Override
	public void deleteRatingComment(Integer id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modRatingComment(RatingComment ticket) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<RatingComment> showRatingComments() {
		// TODO Auto-generated method stub
		return (List<RatingComment>) ratingCommentRepository.findAll();
	}

	@Override
	public RatingComment findRatingComment(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RatingComment> findByUserId(Integer id) {
		// TODO Auto-generated method stub
		List<RatingComment> userRows = new ArrayList<>();
		List<RatingComment> aux = new ArrayList<>();
		aux = (List<RatingComment>) ratingCommentRepository.findAll();
		for (int i=0; i<aux.size();i++) {
			if(aux.get(i).getUser().getId() == id)
				userRows.add(aux.get(i));
		}
		
		return userRows;
	}
	

	@Override
	public List<RatingComment> findByMovieId(Integer id) {
		// TODO Auto-generated method stub
		List<RatingComment> movieRows = new ArrayList<>();
		List<RatingComment> aux = new ArrayList<>();
		aux = (List<RatingComment>) ratingCommentRepository.findAll();
		if(aux.size()!=0)
		for (int i=0; i<aux.size();i++) {
			if( id.equals((aux).get(i).getMovie().getId()))
				movieRows.add(aux.get(i));
		}
		
		return movieRows;
	}

	@Override
	public RatingComment findByMovieUserId(Integer idUser, Integer idMovie) {
		// TODO Auto-generated method stub
		RatingComment userMovieRow = new RatingComment();
		List<RatingComment> allRows = new ArrayList<>();
		allRows = (List<RatingComment>) ratingCommentRepository.findAll();
		if(allRows.size()!=0)
		for (int i=0; i<allRows.size();i++) {
			if(idUser.equals(allRows.get(i).getUser().getId()) && idMovie.equals(allRows.get(i).getMovie().getId() ))
				userMovieRow = allRows.get(i);
		}
		
		
		return userMovieRow;
	}

	
}
