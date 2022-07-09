package ar.edu.unju.edm.controller;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
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
import ar.edu.unju.edm.service.IUserService;


@Controller
public class TicketController {

	private static final Log LOGGER = LogFactory.getLog(TicketController.class);
	
	@Autowired
	MovieUserTicket newTicket;
	
	@Autowired
	ICommentsService commentService;
	
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
		Uzer userfound = new Uzer();
		Movie moviefound = new Movie();
		try {
			userfound = userService.findUserByDni(dni);
		}catch(Exception e) {
			view.addObject("formMovieMessage", e.getMessage());
		}
		
		try {
			moviefound = movieService.findMovie(idmovie);
		}
		catch (Exception e) {
			view.addObject("formMovieMessage", e.getMessage());
		}
		
		LOGGER.info("ingresando al metodo: saveTicket "+ moviefound.getDescription());
		LOGGER.info("ingresando al metodo: saveTicket "+ userfound.getLastname());
		
		ticktosave.setMovie(moviefound);
		ticktosave.setUser(userfound);
		LocalDate actualDate = LocalDate.now();
		ticktosave.setSaledate(actualDate);
		ticketService.saveTicket(ticktosave);
		
		view.addObject("formTicketMessage", "¡La compra se ha realizado con exito!");
		view.addObject("movie", moviefound);
		view.addObject("user", userfound);
		view.addObject("ticket", newTicket);
		view.addObject("wasBuyed", true); // analizar mejor
		
		
			
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
	@GetMapping("/seeOptions/{id}")
	public ModelAndView seeOptions(Model model, @PathVariable(name="id") int id) throws Exception {
		Movie moviefound = new Movie();
		List<Comment> movieComments = new ArrayList<>();
		try {
			moviefound = movieService.findMovie(id);
		}
		catch (Exception e) {
			model.addAttribute("formMovieMessage", e.getMessage());
		}
		
		movieComments = commentService.findByMovieId(moviefound.getId());
	
		ModelAndView view = new ModelAndView("actionsmovies");
		view.addObject("comment",new Comment());
		view.addObject("rating",new Rating());
		view.addObject("comments",movieComments);
	    view.addObject("movie", moviefound);
	    view.addObject("ticket", newTicket);
	    view.addObject("wasBuyed", false);
	    LOGGER.error("saliendo del metodo: seeOptions "+ moviefound.getName());

	    return view;
	}
	
}
