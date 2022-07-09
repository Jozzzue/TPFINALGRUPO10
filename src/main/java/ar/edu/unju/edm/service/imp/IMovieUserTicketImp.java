package ar.edu.unju.edm.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import ar.edu.unju.edm.model.MovieUser;
import ar.edu.unju.edm.repository.MovieUserRepository;
import ar.edu.unju.edm.util.ListMovieUser;
import ar.edu.unju.edm.service.IMovieUserService;


@Service
public class IMovieUserTicketImp implements IMovieUserService {

	@Autowired
	ListMovieUser movieUsers;
	@Autowired
	MovieUserRepository movieUserRepository;
	
	
	
	@Override
	public void saveMovieUser(MovieUser ticket) {
		// TODO Auto-generated method stub
		movieUserRepository.save(ticket);
	}

	@Override
	public void deleteMovieUser(Integer id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modMovieUser(MovieUser ticket) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<MovieUser> showMovieUsers() {
		// TODO Auto-generated method stub
		return (List<MovieUser>) movieUserRepository.findAll();
	}

	@Override
	public MovieUser findMovieUser(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MovieUser> findByUserId(Integer id) {
		// TODO Auto-generated method stub
		List<MovieUser> userRows = new ArrayList<>();
		List<MovieUser> aux = new ArrayList<>();
		aux = (List<MovieUser>) movieUserRepository.findAll();
		for (int i=0; i<aux.size();i++) {
			if(aux.get(i).getUser().getId() == id)
				userRows.add(aux.get(i));
		}
		
		return userRows;
	}
	

	@Override
	public List<MovieUser> findByMovieId(Integer id) {
		// TODO Auto-generated method stub
		List<MovieUser> movieRows = new ArrayList<>();
		List<MovieUser> aux = new ArrayList<>();
		aux = (List<MovieUser>) movieUserRepository.findAll();
		for (int i=0; i<aux.size();i++) {
			if(aux.get(i).getMovie().getId() == id)
				movieRows.add(aux.get(i));
		}
		
		return movieRows;
	}

	@Override
	public List<MovieUser> findByMovieUserId(Integer idUser, Integer idMovie) {
		// TODO Auto-generated method stub
		List<MovieUser> movieRows = new ArrayList<>();
		List<MovieUser> userMovieRows = new ArrayList<>();
		List<MovieUser> allRows = new ArrayList<>();
		allRows = (List<MovieUser>) movieUserRepository.findAll();
		
		for (int i=0; i<allRows.size();i++) {
			if(allRows.get(i).getMovie().getId() == idMovie)
				movieRows.add(allRows.get(i));
		}
		
		for (int i=0; i<movieRows.size();i++) {
			if(movieRows.get(i).getUser().getId() == idUser)
				userMovieRows.add(allRows.get(i));
		}
		
		return userMovieRows;
	}

	
}
