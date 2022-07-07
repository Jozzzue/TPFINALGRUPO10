package ar.edu.unju.edm.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import ar.edu.unju.edm.model.Uzer;

@Component
public class ListUsers {

	private List<Uzer> uzers = new ArrayList<>();
	public ListUsers() {
		// TODO Auto-generated constructor stub
	}
	public List<Uzer> getUsers() {
		return uzers;
	}
	public void setUsers(List<Uzer> uzers) {
		this.uzers = uzers;
	}
	
}
