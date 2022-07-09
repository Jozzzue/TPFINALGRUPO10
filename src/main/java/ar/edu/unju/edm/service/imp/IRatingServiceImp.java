package ar.edu.unju.edm.service.imp;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unju.edm.model.Comment;
import ar.edu.unju.edm.model.Rating;
import ar.edu.unju.edm.repository.RatingRepository;
import ar.edu.unju.edm.service.IRatingService;




@Service
public class IRatingServiceImp implements IRatingService {


	@Autowired
	RatingRepository ratingRepository;

	@Override
	public void saveRating(Rating rating) {
		// TODO Auto-generated method stub
		ratingRepository.save(rating);
	}

	@Override
	public void deleteRating(Integer id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modRating(Rating rating) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Rating> showRatings() {
		// TODO Auto-generated method stub
		return (List<Rating>) ratingRepository.findAll();
	}

	@Override
	public Rating findRating(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Rating> findByMovieId(Integer id) {
		// TODO Auto-generated method stub
		List<Rating> ratings = new ArrayList<>();
		List<Rating> aux = new ArrayList<>();
		aux = (List<Rating>) ratingRepository.findAll();
		for (int i=0; i<aux.size();i++) {
			if(aux.get(i).getMovie().getId() == id)
				ratings.add(aux.get(i));
		}
		
		return ratings;
	}
	

	@Override
	public List<Rating> findByUserId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
