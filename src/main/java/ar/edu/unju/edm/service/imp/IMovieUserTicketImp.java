package ar.edu.unju.edm.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import ar.edu.unju.edm.model.MovieUserTicket;
import ar.edu.unju.edm.model.Uzer;
import ar.edu.unju.edm.repository.MovieUserTicketRepository;
import ar.edu.unju.edm.util.ListMovieUserTickets;
import ar.edu.unju.edm.service.IMovieUserTicketService;


@Service
public class IMovieUserTicketImp implements IMovieUserTicketService {

	@Autowired
	ListMovieUserTickets tickets;
	@Autowired
	MovieUserTicketRepository ticketRepository;
	@Override
	public void saveTicket(MovieUserTicket ticket) {
		// TODO Auto-generated method stub
		ticketRepository.save(ticket);
	}
	
	@Override
	public void deleteTicket(Integer id) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void modTicket(MovieUserTicket ticket) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public List<MovieUserTicket> showTickets() {
		// TODO Auto-generated method stub
		List<MovieUserTicket> aux = new ArrayList<>();
		aux = (List<MovieUserTicket>) ticketRepository.findAll();
		return aux;
	}
	
	
	@Override
	public MovieUserTicket findTicket(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MovieUserTicket> findByUserId(Uzer user) {
		// TODO Auto-generated method stub
		List<MovieUserTicket> userTickets = new ArrayList<>();
		List<MovieUserTicket> aux = new ArrayList<>();
		aux = (List<MovieUserTicket>) ticketRepository.findAll();
		for (int i=0; i<aux.size();i++) {
			if(aux.get(i).getUser().getId() == user.getId())
				userTickets.add(aux.get(i));
		}
		
		return userTickets;
	}

	@Override
	public List<MovieUserTicket> findByUserMovieId(Integer iduser, Integer idmovie) {
		// TODO Auto-generated method stub
		List<MovieUserTicket> movieRows = new ArrayList<>();
		List<MovieUserTicket> userMovieRows = new ArrayList<>();
		List<MovieUserTicket> allRows = new ArrayList<>();
		allRows = (List<MovieUserTicket>) ticketRepository.findAll();
		
		for (int i=0; i<allRows.size();i++) {
			if(allRows.get(i).getMovie().getId() == idmovie)
				movieRows.add(allRows.get(i));
		}
		
		for (int i=0; i<movieRows.size();i++) {
			if(movieRows.get(i).getUser().getId() == iduser)
				userMovieRows.add(allRows.get(i));
		}
		
		return userMovieRows;
	}
	


}
