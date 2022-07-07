package ar.edu.unju.edm.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;


import ar.edu.unju.edm.model.MovieUserTicket;

@Component
public class ListMovieUserTickets {

	private List<MovieUserTicket> tickets = new ArrayList<>();
	public ListMovieUserTickets() {
		// TODO Auto-generated constructor stub
	}
	public List<MovieUserTicket> getMovies() {
		return tickets;
	}
	public void setMovies(List<MovieUserTicket> tickets) {
		this.tickets = tickets;
	}
	
}
