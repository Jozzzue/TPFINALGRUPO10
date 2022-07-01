package ar.edu.unju.edm.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ar.edu.unju.edm.model.Uzer;
import ar.edu.unju.edm.repository.UserRepository;
import ar.edu.unju.edm.util.ListUsers;
import ar.edu.unju.edm.service.IUserService;

@Service
public class IUserServiceImp implements IUserService {

	@Autowired
	ListUsers users;
	@Autowired
	UserRepository userRepository;
	
	@Override
	public void saveUser(Uzer uzer) {
		// TODO Auto-generated method stub
		String pw = uzer.getPassword();
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
		uzer.setPassword(bCryptPasswordEncoder.encode(pw));
		uzer.setStatus(true);
		userRepository.save(uzer);
	}
	@Override
	public void deleteUser(int id) throws Exception {
		// TODO Auto-generated method stub
		Uzer aux = new Uzer();
		aux = findUser(id);
		aux.setStatus(false);
		userRepository.save(aux);
	}
	@Override
	public void modUser(Uzer uzer) {
		// TODO Auto-generated method stub
		userRepository.save(uzer);
	}
	@Override
	public List<Uzer> showUsers() {
		// TODO Auto-generated method stub
		List<Uzer> aux = new ArrayList<>();
		List<Uzer> aux2 = new ArrayList<>();
		aux = (List<Uzer>) userRepository.findAll();
		for (int i=0; i<aux.size();i++) {
			if(aux.get(i).getStatus()==true)
				aux2.add(aux.get(i));
		}
		return aux2;
	}
	@Override
	public Uzer findUser(int id) throws Exception {
		// TODO Auto-generated method stub
		Uzer foundUser = new Uzer();
		for (int i=0; i < users.getUsers().size(); i++) {
			if ( users.getUsers().get(i).getId() == id )
				foundUser = users.getUsers().get(i);
		}
		
		foundUser = userRepository.findById(id).orElseThrow(()->new Exception("usuario no encontrado"));
		
		
		return foundUser;
	}	

}
