package ar.edu.unju.edm.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;


import ar.edu.unju.edm.model.MovieUser;

@Component
public class ListMovieUser {

	private List<MovieUser> movieUsers = new ArrayList<>();
	public ListMovieUser() {
		// TODO Auto-generated constructor stub
	}
	public List<MovieUser> getMovieUsers() {
		return movieUsers;
	}
	public void setMovieUsers(List<MovieUser> movieUsers) {
		this.movieUsers = movieUsers;
	}
	
}

