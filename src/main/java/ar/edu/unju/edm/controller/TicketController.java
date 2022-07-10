package ar.edu.unju.edm.controller;



import java.time.LocalDate;
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

import ar.edu.unju.edm.model.Comment;
import ar.edu.unju.edm.model.Movie;
import ar.edu.unju.edm.model.MovieUserTicket;
import ar.edu.unju.edm.model.Rating;
import ar.edu.unju.edm.model.Uzer;
import ar.edu.unju.edm.service.ICommentsService;
import ar.edu.unju.edm.service.IMovieService;
import ar.edu.unju.edm.service.IMovieUserTicketService;
import ar.edu.unju.edm.service.IRatingService;
import ar.edu.unju.edm.service.IUserService;


@Controller
public class TicketController {

	private static final Log LOGGER = LogFactory.getLog(TicketController.class);
	
	@Autowired
	MovieUserTicket newTicket;
	
	@Autowired
	ICommentsService commentService;
	@Autowired 
	IRatingService ratingService;
	@Autowired 
	IMovieUserTicketService ticketService;
	@Autowired
	IMovieService movieService;
	@Autowired
	IUserService userService;
	
	
	
	// guardar tickets
	@PostMapping(value="/saveTicket/{id}/{dni}")
	public ModelAndView saveTicket(@ModelAttribute ("ticket") MovieUserTicket ticktosave, @PathVariable(name="id") int idmovie, @PathVariable(name="dni") int dni) {
		ModelAndView view = new ModelAndView("actionsmovies");
	//buscar usuario pelicula
		Uzer userfound = new Uzer();
		Movie moviefound = new Movie();
		try {
			userfound = userService.findUserByDni(dni);
		}catch(Exception e) {
			view.addObject("formTicketMessage", e.getMessage());
		}
		
		try {
			moviefound = movieService.findMovie(idmovie);
		}
		catch (Exception e) {
			view.addObject("formTicketMessage", e.getMessage());
		}
		
		LOGGER.error("ingresando al metodo: saveTicket "+ moviefound.getDescription()+" "+userfound.getLastname());
		
		
		
		
		
		
	//controlar cantidad limite de tickets y guardar el ticket si es posible	
		List<MovieUserTicket> userTicksForMovie = new ArrayList<>();
		userTicksForMovie = ticketService.findByUserMovieId(userfound.getId(), moviefound.getId());
		int sumTickets = ticktosave.getTickets();
		
		if(userTicksForMovie.size()!=0) 
		{
			for(int i=0;i<userTicksForMovie.size();i++) {
				sumTickets = userTicksForMovie.get(i).getTickets() + sumTickets;
			}
		}
		if(sumTickets<=3) 
		{
			ticktosave.setMovie(moviefound);
			ticktosave.setUser(userfound);
			LocalDate actualDate = LocalDate.now();
			ticktosave.setSaledate(actualDate);
			try {
				ticketService.saveTicket(ticktosave);
			}
			catch (Exception e) {
				view.addObject("formTicketMessage", e.getMessage());
			}
		}
		
		if(sumTickets==3)
			view.addObject("purchaseLimit", true); 
			else
				view.addObject("purchaseLimit", false); 
		
	// controlar q solo posea una valoracion	
				Rating auxRating = new Rating();	
				auxRating = ratingService.findByUserMovieId(userfound.getId(), moviefound.getId());
				if(auxRating.getValue()==0) {
					view.addObject("ratingAverg",0);
				}else {
					int aux1 =  auxRating.getMovie().getAverageRating();
					if(aux1<=20) {
						view.addObject("ratingAverg",1);
					}else {
						if(aux1 <= 40) {
							view.addObject("ratingAverg",2);
						}else {
							if(aux1<=60) {
								view.addObject("ratingAverg",3);
							}else {
								if(aux1<=80) {
									view.addObject("ratingAverg",4);
								}else {
									view.addObject("ratingAverg",5);
								}
							}
						}
					}
				}
	// buscar lista de comentarios
		List<Comment> movieComments = new ArrayList<>();
		movieComments = commentService.findByMovieId(moviefound.getId());
		
		
	// retornar variables a la vista
		view.addObject("comment",new Comment());
		view.addObject("rating",new Rating());
		view.addObject("comments",movieComments);
		view.addObject("formTicketMessage", "¡La compra se ha realizado con exito!");
		view.addObject("movie", moviefound);
		view.addObject("ticket", newTicket);
		
		
		LOGGER.error("saliendo del metodo: saveTicket "+ ticktosave.getTickets()+" "+userTicksForMovie.size());
			
		return view;

		
	}
	
	// listar peliculas
	@GetMapping("/listTickets/{dni}")	
	public ModelAndView listTickets(@PathVariable(name="dni") int dni) {
		Uzer userfound = new Uzer();
		List<MovieUserTicket> userTickets = new ArrayList<>();
		try {
		userfound = userService.findUserByDni(dni);
		}catch(Exception e) {
			
		}
		
		try {
			userTickets = ticketService.findByUserId(userfound);
			}catch(Exception e) {
				
			}
		
		ModelAndView view = new ModelAndView("ticketlist");
		view.addObject("ticketss", userTickets);
		LOGGER.info("ingresando al metodo: listtickets "+ userTickets.size());
		return view;
	}
	
	
	//va a la pagina con las opciones de pelicula
	@GetMapping("/seeOptions/{id}/{dni}")
	public ModelAndView seeOptions(@PathVariable(name="id") int id,@PathVariable(name="dni") int dni) throws Exception {
		ModelAndView view = new ModelAndView("actionsmovies");
	//buscar usuario y pelicula
		Uzer userfound = new Uzer();
		Movie moviefound = new Movie();
		try {
			userfound = userService.findUserByDni(dni);
		}catch(Exception e) {
			view.addObject("formTicketMessage", e.getMessage());
		}
		
		try {
			moviefound = movieService.findMovie(id);
		}
		catch (Exception e) {
			view.addObject("formTicketMessage", e.getMessage());
		}
		
		LOGGER.error("ingresando al metodo: saveTicket "+ moviefound.getDescription()+" "+userfound.getLastname());
		
		
	// controlar cantidad limites de tickets
	
		List<MovieUserTicket> userTicksForMovie = new ArrayList<>();
		userTicksForMovie = ticketService.findByUserMovieId(userfound.getId(), moviefound.getId());
		int sumTickets = 0;
		if(userTicksForMovie.size()!=0) {
		for(int i=0;i<userTicksForMovie.size();i++) {
			sumTickets = userTicksForMovie.get(i).getTickets() + sumTickets;
		}
		}
		if(sumTickets<3)
			view.addObject("purchaseLimit", false);
		else
			view.addObject("purchaseLimit", true);
		
	// controlar q solo posea una valoracion	
		Rating auxRating = new Rating();	
		auxRating = ratingService.findByUserMovieId(userfound.getId(), moviefound.getId());
		if(auxRating.getValue()==0) {
			view.addObject("ratingAverg",0);
		}else {
			int aux1 =  auxRating.getMovie().getAverageRating();
			if(aux1<=20) {
				view.addObject("ratingAverg",1);
			}else {
				if(aux1 <= 40) {
					view.addObject("ratingAverg",2);
				}else {
					if(aux1<=60) {
						view.addObject("ratingAverg",3);
					}else {
						if(aux1<=80) {
							view.addObject("ratingAverg",4);
						}else {
							view.addObject("ratingAverg",5);
						}
					}
				}
			}
		}
		
	// buscar lista de comentarios
		List<Comment> movieComments = new ArrayList<>();
		movieComments = commentService.findByMovieId(moviefound.getId());
		
	//retornar variables a la vista
		view.addObject("comment",new Comment());
		view.addObject("rating",new Rating());
		view.addObject("comments",movieComments);
	    view.addObject("movie", moviefound);
	    view.addObject("ticket", newTicket);
	    
	    LOGGER.error("saliendo del metodo: seeOptions "+ moviefound.getName());

	    return view;
	}
	
}
