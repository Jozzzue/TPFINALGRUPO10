package ar.edu.unju.edm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.unju.edm.model.Uzer;

@Service
public interface IUserService {
	public void saveUser(Uzer uzer);
	public void deleteUser(int id) throws Exception;
	public void modUser(Uzer uzer);
	public List<Uzer> showUsers(); 
	public Uzer findUser(int id) throws Exception; 
}
