package ar.edu.unju.edm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ar.edu.unju.edm.service.imp.ILoginUserServiceImp;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private AutenticationSuccessHandler autentication;
	
	String [] resources = new String[] {
		"/include/**","/css/**","/icons/**","/img/**","/js/**","/layer/**","/webjars/**"	
			
	};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.csrf().disable()
			.authorizeRequests()
				.antMatchers(resources).permitAll()
				.antMatchers("/","/home","/addUser","/saveUser").permitAll()
				.antMatchers("/addMovie","/listMovies","/updateMovie/**","editMovie","/delMovie/**","/saveMovie").hasAuthority("ADMIN")
				.antMatchers().hasAuthority("NORMAL")
				.anyRequest().authenticated()
				.and()
				.formLogin()
				.loginPage("/login")
				.permitAll()
				.successHandler(autentication)
				.failureUrl("/login?error=true")
				.usernameParameter("dni")
				.passwordParameter("password")
				.and()
			.logout()
			.permitAll()
			.logoutSuccessUrl("/login?logout");
				
	}
	
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
		return bCryptPasswordEncoder;
	}
	
	@Autowired
	ILoginUserServiceImp userDetailsService;
	// AuthenticationManagerBuilder recupera informacion del usuario que intentamos logear en el sistema
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	

}
