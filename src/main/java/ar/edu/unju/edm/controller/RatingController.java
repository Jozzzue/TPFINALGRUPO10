package ar.edu.unju.edm.controller;







import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



import org.springframework.web.servlet.ModelAndView;

import ar.edu.unju.edm.model.Rating;
import ar.edu.unju.edm.model.Comment;
import ar.edu.unju.edm.model.Movie;
import ar.edu.unju.edm.model.MovieUserTicket;
import ar.edu.unju.edm.model.Uzer;
import ar.edu.unju.edm.service.IRatingService;
import ar.edu.unju.edm.service.ICommentsService;
import ar.edu.unju.edm.service.IMovieService;
import ar.edu.unju.edm.service.IMovieUserTicketService;
import ar.edu.unju.edm.service.IUserService;



@Controller
public class RatingController {

	private static final Log LOGGER = LogFactory.getLog(RatingController.class);
	
	@Autowired 
	ICommentsService commentService;
	
	@Autowired
	Rating newRating;
	@Autowired 
	IMovieUserTicketService ticketService;
	@Autowired 
	IRatingService ratingService;
	@Autowired
	IMovieService movieService;
	@Autowired
	IUserService userService;
	
	
	@PostMapping(value="/saveRating/{id}/{dni}")
	public ModelAndView saveTicket(@ModelAttribute ("rating") Rating ratingtosave, @ModelAttribute("wasBuyed") String wasBuyed, @PathVariable(name="id") int idmovie, @PathVariable(name="dni") int dni) {
		ModelAndView view = new ModelAndView("actionsmovies");
	//buscar usuario y pelicula
		Uzer userfound = new Uzer();
		Movie moviefound = new Movie();
		try {
			userfound = userService.findUserByDni(dni);
		}catch(Exception e) {
			view.addObject("formCommentMessage", e.getMessage());
		}
		
		try {
			moviefound = movieService.findMovie(idmovie);
		}
		catch (Exception e) {
			view.addObject("formCommentMessage", e.getMessage());
		}
		
		LOGGER.error("ingresando al metodo: saveTicket "+ moviefound.getDescription()+" "+userfound.getLastname());
		
	
	//guardar valoracion o no
		Rating auxRating = new Rating();	
		auxRating = ratingService.findByUserMovieId(userfound.getId(), moviefound.getId());
		if(auxRating.getValue()==0) {
		//guardo la valoracion
			ratingtosave.setMovie(moviefound);
			ratingtosave.setUser(userfound);
			ratingService.saveRating(ratingtosave);
		
		// Guardo promedio general de todas las valoraciones
			List<Rating> numRatings = new ArrayList<>();
			numRatings = ratingService.findByMovieId(moviefound.getId());
			int length = numRatings.size(), sumRatings=0 , aux=0;
			
			for(int i=0;i<length;i++)
				sumRatings = numRatings.get(i).getValue() + sumRatings;		
		
			aux = (int) ((100 * sumRatings)/(length*5)); 
			
			moviefound.setAverageRating(aux);
			
			
			if(aux<=20) {
				view.addObject("ratingAverg",1);
			}else {
				if(aux <= 40) {
					view.addObject("ratingAverg",2);
				}else {
					if(aux<=60) {
						view.addObject("ratingAverg",3);
					}else {
						if(aux<=80) {
							view.addObject("ratingAverg",4);
						}else {
							view.addObject("ratingAverg",5);
						}
					}
				}
			}
		}
		
	
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
			
		
	//busca comentarios y devuelve a la vista
		List<Comment> movieComments = new ArrayList<>();
		movieComments = commentService.findByMovieId(moviefound.getId());
		
		view.addObject("movie", moviefound);
		view.addObject("ticket", new MovieUserTicket());
		view.addObject("comment",new Comment());
		view.addObject("comments",movieComments);
		view.addObject("rating",new Rating());
		
		return view;

		
	}
	
	
}
