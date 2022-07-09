package ar.edu.unju.edm.controller;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



import org.springframework.web.servlet.ModelAndView;


import ar.edu.unju.edm.model.Movie;
import ar.edu.unju.edm.model.MovieUser;

import ar.edu.unju.edm.model.Uzer;
import ar.edu.unju.edm.service.IMovieService;
import ar.edu.unju.edm.service.IMovieUserService;
import ar.edu.unju.edm.service.IUserService;
import ar.edu.unju.edm.util.ListMovieUser;


@Controller
public class MovieUserController {

	private static final Log LOGGER = LogFactory.getLog(MovieUserController.class);
	
	@Autowired
	MovieUser newMovieUser;
	@Autowired 
	IMovieUserService movieUserService;
	@Autowired
	IMovieService movieService;
	@Autowired
	IUserService userService;
	@Autowired 
	ListMovieUser listMovieUsers;
	
	
	//guardar valoracion
		@PostMapping(value="/saveComment/{dni}/{id}/{wasRated}/{purchaseLimit}")
		public ModelAndView saveComment(@ModelAttribute ("movieUser") MovieUser movieUsertoSave,@PathVariable(name="dni") int dni, @PathVariable(name="id") int id,  @PathVariable(name="wasRated") String wasRated, @PathVariable(name="purchaseLimit") String purchaseLimit) {
			ModelAndView view = new ModelAndView("actionsmovies");
			Movie movie = new Movie();
			Uzer user = new Uzer();
			try {
				movie = movieService.findMovie(id);
			}
			catch (Exception e) {
				view.addObject("formMovieUserMessage", e.getMessage());
			}
			
			try {
				user = userService.findUserByDni(dni);
			}catch(Exception e) {
				view.addObject("formMovieUserMessage", e.getMessage());
			}
			LOGGER.info("ingresando al metodo: saveComment "+ purchaseLimit+" "+wasRated);
			LOGGER.info("ingresando al metodo: saveComment "+ movie.getDescription()+" "+user.getLastname());
			
			
			List<MovieUser> specificMovieUser = new ArrayList<>();
			specificMovieUser = movieUserService.findByMovieUserId(user.getId(), movie.getId());
			
			movieUsertoSave.setMovie(movie);
			movieUsertoSave.setUser(user);
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			String aux = dtf.format(LocalDateTime.now());
			
			movieUsertoSave.setCommentDate(aux);
			
			try {
				movieUserService.saveMovieUser(movieUsertoSave);
			}
			catch (Exception e) {
				view.addObject("formMovieUserMessage", e.getMessage());
			}
			
			   
			List<MovieUser> commentsRatings = new ArrayList<>();
			commentsRatings = movieUserService.findByMovieId(movie.getId());

				
				
				if(purchaseLimit.equals("false"))
			    	view.addObject("purchaseLimit", false);
			    else
			    	view.addObject("purchaseLimit", true);
			    
				if(wasRated.equals("false"))
			    	view.addObject("wasRated", false);
			    else
			    	view.addObject("wasRated", true);
				
				view.addObject("specificMovieUser",specificMovieUser);
				view.addObject("commentsRatings", commentsRatings);
				view.addObject("user", user);
			    view.addObject("movie", movie);
			    view.addObject("movieUser", newMovieUser);
				
			return view;

			
		}
	
	
	
	//guardar valoracion
	@PostMapping(value="/saveRating/{dni}/{id}/{wasRated}/{purchaseLimit}")
	public ModelAndView saveRating(@ModelAttribute ("movieUser") MovieUser movieUsertoSave, @PathVariable(name="dni") int dni, @PathVariable(name="id") int id, @PathVariable(name="wasRated") String wasRated, @PathVariable(name="purchaseLimit") String purchaseLimit) {
		ModelAndView view = new ModelAndView("actionsmovies");
		Movie movie = new Movie();
		Uzer user = new Uzer();
		try {
			movie = movieService.findMovie(id);
		}
		catch (Exception e) {
			view.addObject("formMovieUserMessage", e.getMessage());
		}
		
		try {
			user = userService.findUserByDni(dni);
		}catch(Exception e) {
			view.addObject("formMovieUserMessage", e.getMessage());
		}
		LOGGER.info("ingresando al metodo: saveRating "+ movie.getDescription()+" "+user.getLastname());
		
		
		List<MovieUser> specificMovieUser = new ArrayList<>();
		specificMovieUser = movieUserService.findByMovieUserId(user.getId(), movie.getId());
		
		movieUsertoSave.setMovie(movie);
		movieUsertoSave.setUser(user);
		
		try {
			movieUserService.saveMovieUser(movieUsertoSave);
		}
		catch (Exception e) {
			view.addObject("formMovieUserMessage", e.getMessage());
		}
		
		   
		    List<MovieUser> commentsRatings = new ArrayList<>();
			commentsRatings = movieUserService.findByMovieId(movie.getId());
		
			int length = commentsRatings.size(), sumRatings=0 , aux=0;
			
			if(length!=0) {
				wasRated="true";
				for(int i=0;i<length;i++)
					sumRatings = commentsRatings.get(i).getRating() + sumRatings;		
			
				aux = (int) ((100 * sumRatings)/(length*5)); 
				movie.setAverageRating(aux);
			}
			
			if(aux==0)
				view.addObject("ratingAverg",0);
			else {
				if(aux<20) {
					view.addObject("ratingAverg",1);
				}else {
					if(aux < 40) {
						view.addObject("ratingAverg",2);
					}else {
						if(aux<60) {
							view.addObject("ratingAverg",3);
						}else {
							if(aux<80) {
								view.addObject("ratingAverg",4);
							}else {
								view.addObject("ratingAverg",5);
							}
						}
					}
				}
			}
			
			
			if(purchaseLimit.equals("false"))
		    	view.addObject("purchaseLimit", false);
		    else
		    	view.addObject("purchaseLimit", true);
		    
			if(wasRated.equals("false"))
		    	view.addObject("wasRated", false);
		    else
		    	view.addObject("wasRated", true);
			
			view.addObject("specificMovieUser",specificMovieUser);
			view.addObject("commentsRatings", commentsRatings);
			view.addObject("user", user);
		    view.addObject("movie", movie);
		    view.addObject("movieUser", newMovieUser);
			
		return view;

		
	}
	
	
	
	
	// guardar movieUser
	
	@PostMapping(value="/saveTicket/{dni}/{id}")
	public ModelAndView saveTicket(@ModelAttribute ("movieUser") MovieUser movieUsertoSave,@PathVariable(name="dni") int dni, @PathVariable(name="id") int id) {
		ModelAndView view = new ModelAndView("actionsmovies");
		Movie movie = new Movie();
		Uzer user = new Uzer();
		try {
			movie = movieService.findMovie(id);
		}
		catch (Exception e) {
			view.addObject("formMovieUserMessage", e.getMessage());
		}
		
		try {
			user = userService.findUserByDni(dni);
		}catch(Exception e) {
			view.addObject("formMovieUserMessage", e.getMessage());
		}
		LOGGER.info("ingresando al metodo: saveTicket "+ movieUsertoSave.getTickets());
		
		
		List<MovieUser> specificMovieUser = new ArrayList<>();
		specificMovieUser = movieUserService.findByMovieUserId(user.getId(), movie.getId());
		
		LOGGER.info("ingresando al metodo: saveTicket "+ movie.getDescription()+" "+user.getLastname());
		
		 int sumTickets = 0,totalTickets=movieUsertoSave.getTickets();
		 boolean wasRated = false; 
		 if(specificMovieUser.size()!=0) {
		    for(int i=0;i<specificMovieUser.size();i++) {
		    	sumTickets = specificMovieUser.get(i).getTickets() + sumTickets;
		    	if(specificMovieUser.get(i).getRating() > 0)
		    		wasRated = true;
		    }
		    
		 }   
		    
		    if(totalTickets<=3) {
		    	movieUsertoSave.setMovie(movie);
		    	movieUsertoSave.setUser(user);
		    	LocalDate actualDate = LocalDate.now();
				movieUsertoSave.setSaledate(actualDate);
		    	movieUsertoSave.setTickets(totalTickets);
		    	view.addObject("formMovieUserMessage", "¡La compra se ha realizado con exito!");
		    	try {
					movieUserService.saveMovieUser(movieUsertoSave);
				}
				catch (Exception e) {
					view.addObject("formMovieUserMessage", e.getMessage());
				}
		    	
		    }else
		    	view.addObject("formMovieUserMessage", "Limite de boletos alcanzados");
		
		    List<MovieUser> commentsRatings = new ArrayList<>();
			commentsRatings = movieUserService.findByMovieId(movie.getId());
		
			if(sumTickets < 3)
		    	view.addObject("purchaseLimit", false);
		    else
		    	view.addObject("purchaseLimit", true);
		    	
		    view.addObject("wasRated",wasRated);
		
			view.addObject("specificMovieUser",specificMovieUser);
			view.addObject("commentsRatings", commentsRatings);
			view.addObject("user", user);
		    view.addObject("movie", movie);
		    view.addObject("movieUser", newMovieUser);
			
		return view;

		
	}
	
	// listar boletos de pelicula de usuario
	@GetMapping("/listTickets/{dni}")	
	public ModelAndView listTickets(@PathVariable(name="dni") int dni) {
		ModelAndView view = new ModelAndView("ticketlist");
		Uzer userfound = new Uzer();
		List<MovieUser> userTickets = new ArrayList<>();
		try {
		userfound = userService.findUserByDni(dni);
		}catch(Exception e) {
			view.addObject("formErrorMessage", e.getMessage());
		}
		
		try {
			userTickets = movieUserService.findByUserId(userfound.getId());
			}catch(Exception e) {
				view.addObject("formErrorMessage", e.getMessage());
			}
		
		
		view.addObject("ticketss", userTickets);
		LOGGER.info("ingresando al metodo: listtickets "+ userTickets.size());
		return view;
	}
	

	
	//	va a la pagina con las opciones de pelicula
	@GetMapping("/seeOptions/{id}/{dni}")
	public ModelAndView seeOptions(@PathVariable(name="id") int id, @PathVariable(name="dni") int dni) throws Exception {
		ModelAndView view = new ModelAndView("actionsmovies");
		Movie moviefound = new Movie();
		Uzer userfound = new Uzer();
		
		LOGGER.error("ingresando al metodo: seeOptions "+ id + " " +dni);
		
		try {
			moviefound = movieService.findMovie(id);
		}
		catch (Exception e) {
			view.addObject("formMovieUserMessage", e.getMessage());
		}
		
		try {
			userfound = userService.findUserByDni(dni);
		}catch(Exception e) {
			view.addObject("formMovieUserMessage", e.getMessage());
		}
		
		
	// devolver una lista movieUser de la pelicula, que tendra la lista de comentarios y de valoraciones, entre otras cosas
		
		List<MovieUser> commentsRatings = new ArrayList<>();
		commentsRatings = movieUserService.findByMovieId(moviefound.getId());
		
	/*	devolver una lista movieUser de la pelicula y el usuario, 
	Que tendra los comentarios del usuario sobre pelicula, (pueden ser muchos o ningunos)
	La valoracion del usuario sobre la pelicula (debe ser una sola o ninguna)
	Y tambien la cantidad de tickets del usuario(maximo 3, minimo 0)	*/

		List<MovieUser> specificMovieUser = new ArrayList<>();
		specificMovieUser = movieUserService.findByMovieUserId(userfound.getId(), moviefound.getId());
		
		
	//Retornar a la vista las listas, usuario, pelicula y nueva MovieUser
		
		view.addObject("specificMovieUser",specificMovieUser);
		view.addObject("commentsRatings", commentsRatings);
		view.addObject("user", userfound);
	    view.addObject("movie", moviefound);
	    view.addObject("movieUser", newMovieUser);
	    
	/*	agregar variables banderas para no visualizar objetos indebidos
	 Bandera q controle q no se supere la cantidad de 3 boletos
	 Bandera q controle q solo tenga 1 valoracion 	*/

	    int sumTickets = 0;
	    boolean wasRated = false;
	    for(int i=0;i<specificMovieUser.size();i++) {
	    	sumTickets = specificMovieUser.get(i).getTickets() + sumTickets;
	    	if(specificMovieUser.get(i).getRating() > 0)
	    		wasRated = true;
	    }
	    
	    if(sumTickets < 3)
	    	view.addObject("purchaseLimit", false);
	    else
	    	view.addObject("purchaseLimit", true);
	    	
	    view.addObject("wasRated",wasRated);
	    
	    LOGGER.error("saliendo del metodo: seeOptions "+ moviefound.getName() + " " +userfound.getLastname());

	    return view;
	}
	
}
