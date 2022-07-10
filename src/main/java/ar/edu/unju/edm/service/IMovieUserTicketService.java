package ar.edu.unju.edm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.unju.edm.model.MovieUserTicket;
import ar.edu.unju.edm.model.Uzer;

@Service
public interface IMovieUserTicketService {
	public void saveTicket(MovieUserTicket ticket); 
	public void deleteTicket(Integer id) throws Exception; // no aplica
	public void modTicket(MovieUserTicket ticket); // no aplica
	public List<MovieUserTicket> showTickets(); 
	public MovieUserTicket findTicket(Integer id) throws Exception; // no aplica
	public List<MovieUserTicket> findByUserId(Uzer user);
	public List<MovieUserTicket> findByUserMovieId(Integer iduser, Integer idmovie);
	
}
