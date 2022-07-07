package ar.edu.unju.edm.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ar.edu.unju.edm.model.Uzer;
import ar.edu.unju.edm.repository.UserRepository;

@Service
public class ILoginUserServiceImp implements UserDetailsService{

	@Autowired
	UserRepository iUser;
	@Override
	public UserDetails loadUserByUsername(String dni) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Uzer founduser = iUser.findByDni(Integer.parseInt(dni)).orElseThrow(()-> new UsernameNotFoundException("Login Invalido"));
		
		List<GrantedAuthority> types = new ArrayList<>();
		GrantedAuthority grantedAutorithy = new SimpleGrantedAuthority(founduser.getType());
		types.add(grantedAutorithy);
		
		UserDetails user = (UserDetails) new User(dni,founduser.getPassword(),types);
		return user;
	}

}
