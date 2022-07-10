package ar.edu.unju.edm.controller;



//import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
//import java.util.ArrayList;
//import java.util.List;
import java.util.List;

//import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
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
//import ar.edu.unju.edm.service.IMovieUserTicketService;
import ar.edu.unju.edm.service.IUserService;


@Controller
public class CommentController {

	private static final Log LOGGER = LogFactory.getLog(CommentController.class);
	
	@Autowired
	Comment newComment;
	@Autowired 
	IMovieUserTicketService ticketService;
	@Autowired 
	ICommentsService commentService;
	@Autowired
	IMovieService movieService;
	@Autowired
	IUserService userService;
	@Autowired 
	IRatingService ratingService;
	
	
	// guardar tickets
	@PostMapping(value="/saveComment/{id}/{dni}")
	public ModelAndView saveTicket(@ModelAttribute ("comment") Comment comtosave, @PathVariable(name="id") int idmovie, @PathVariable(name="dni") int dni) {
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
			moviefound = movieService.findMovie(idmovie);
		}
		catch (Exception e) {
			view.addObject("formTicketMessage", e.getMessage());
		}
	
		LOGGER.error("ingresando al metodo: saveTicket "+ moviefound.getDescription()+" "+userfound.getLastname());
		
	// guardar comentario
		comtosave.setMovie(moviefound);
		comtosave.setUser(userfound);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		String aux = dtf.format(LocalDateTime.now());
		comtosave.setDate(aux);
		commentService.saveComment(comtosave);
		
	
		
		//controlar cantidad limites de tickets
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
		//actualizar lista de comentarios y enviar
			List<Comment> movieComments = new ArrayList<>();
			movieComments = commentService.findByMovieId(moviefound.getId());
			
	// enviar variables a la vista		
		view.addObject("movie", moviefound);
		view.addObject("ticket", new MovieUserTicket());
		view.addObject("comment",new Comment());
		view.addObject("comments",movieComments);
		view.addObject("rating",new Rating());
		
		LOGGER.error("saliendo del metodo: saveTicket "+ sumTickets+" "+userTicksForMovie.size());
		
		return view;

		
	}
	
	
	
}
