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
import ar.edu.unju.edm.model.Uzer;
import ar.edu.unju.edm.service.ICommentsService;
import ar.edu.unju.edm.service.IMovieService;
//import ar.edu.unju.edm.service.IMovieUserTicketService;
import ar.edu.unju.edm.service.IUserService;


@Controller
public class CommentController {

	private static final Log LOGGER = LogFactory.getLog(CommentController.class);
	
	@Autowired
	Comment newComment;
	
	@Autowired 
	ICommentsService commentService;
	@Autowired
	IMovieService movieService;
	@Autowired
	IUserService userService;
	
	
	
	// guardar tickets
	@PostMapping(value="/saveComment/{id}/{dni}")
	public ModelAndView saveTicket(@ModelAttribute ("comment") Comment comtosave, @ModelAttribute("wasBuyed") String wasBuyed, @PathVariable(name="id") int idmovie, @PathVariable(name="dni") int dni) {
		ModelAndView view = new ModelAndView("actionsmovies");
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
		
		LOGGER.info("ingresando al metodo: saveComment "+ moviefound.getDescription());
		LOGGER.info("ingresando al metodo: saveComment "+ userfound.getLastname());
		
		comtosave.setMovie(moviefound);
		comtosave.setUser(userfound);
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		 String aux = dtf.format(LocalDateTime.now());
		 comtosave.setDate(aux);
		 
		 LOGGER.info("ingresando al metodo: saveComment "+ comtosave.getDate());
		commentService.saveComment(comtosave);
		
		
		List<Comment> movieComments = new ArrayList<>();
		
		movieComments = commentService.findByMovieId(moviefound.getId());
		
		//view.addObject("formCommentMessage", "El comentario se ha realizado con exito!");
		view.addObject("movie", moviefound);
		view.addObject("user", userfound);
		view.addObject("ticket", new MovieUserTicket());
		if(wasBuyed.equals("false")==false)
			view.addObject("wasBuyed", false); 								// analizar mejor xd
		else
			view.addObject("wasBuyed", true);
		
		view.addObject("comment",new Comment());
		view.addObject("comments",movieComments);
		
		
		return view;

		
	}
	
	
	
}
