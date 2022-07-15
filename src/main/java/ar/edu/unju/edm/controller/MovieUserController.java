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
import ar.edu.unju.edm.model.RatingComment;
import ar.edu.unju.edm.model.Uzer;
import ar.edu.unju.edm.service.IMovieService;
import ar.edu.unju.edm.service.IMovieUserService;
import ar.edu.unju.edm.service.IRatingCommentService;
import ar.edu.unju.edm.service.IUserService;
import ar.edu.unju.edm.util.ListMovieUser;


@Controller
public class MovieUserController {

	private static final Log LOGGER = LogFactory.getLog(MovieUserController.class);
	
	@Autowired
	MovieUser newMovieUser;
	@Autowired
	IRatingCommentService ratingCommentService;
	@Autowired 
	IMovieUserService movieUserService;
	@Autowired
	IMovieService movieService;
	@Autowired
	IUserService userService;
	@Autowired 
	ListMovieUser listMovieUsers;
	
	
	//guardar valoracion
		@PostMapping(value="/saveComment/{dni}/{id}")
		public ModelAndView saveComment(@ModelAttribute ("userRatingComment") RatingComment commentToSave,@PathVariable(name="dni") int dni, @PathVariable(name="id") int id) {
			ModelAndView view = new ModelAndView("actionsmovies");
		//encuentro usuario y pelicula
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
			
			LOGGER.info("ingresando al metodo: saveComment "+ movie.getDescription()+" "+user.getLastname());
			
			
			
			
			//if(movieUsertoSave.getRating()==0 && movieUsertoSave.getTickets()==0) {
					commentToSave.setUser(user);
					commentToSave.setMovie(movie);
			//}
			
		// seteo fecha y guardo
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			String aux = dtf.format(LocalDateTime.now());
			
			commentToSave.setCommentDate(aux);
			
			LOGGER.info("ingresando al metodo: saveComment "+ commentToSave.getCommentDate()+" "+aux );
			
			try {
				ratingCommentService.saveRatingComment(commentToSave);
			}
			catch (Exception e) {
				view.addObject("formMovieUserMessage", e.getMessage());
			}
			
			LOGGER.error("saliendo del metodo: saveComment "+ commentToSave.getRating());
			// encuentra fila de usuario y pelicula
			MovieUser specificMovieUser = new MovieUser();
			specificMovieUser = movieUserService.findByMovieUserId(user.getId(), movie.getId());
			
			LOGGER.error("saliendo del metodo: saveComment "+ commentToSave.getRating());
			
			LOGGER.error("saliendo del metodo: saveComment "+specificMovieUser.getTickets());
			
		//lista de comentarios de pelicula
			List<RatingComment> commentsRatings = new ArrayList<>();
			commentsRatings = ratingCommentService.findByMovieId(movie.getId());

			
			// encuentra fila de usuario y pelicula
						MovieUser userTicket = new MovieUser();
						userTicket = movieUserService.findByMovieUserId(user.getId(), movie.getId());
						
						

						RatingComment userRatingComment = new RatingComment();
						userRatingComment = ratingCommentService.findByMovieUserId(user.getId(), movie.getId());
						
						//enviar banderas
					    if(userTicket.getTickets() == 0)
					    	view.addObject("hasTickets", false);
					    else
					    	view.addObject("hasTickets", true);
					    
					    if(userTicket.getTickets() < 3)
					    	view.addObject("purchaseLimit", false);
					    else
					    	view.addObject("purchaseLimit", true);
					    
					    if(userRatingComment.getRating() == 0)
					    	view.addObject("wasRated", false);
					    else
					    	view.addObject("wasRated", true);
					    
					    if(userRatingComment.getCommentContent() == null)
					    	view.addObject("wasCommented", false);
					    else
					    	view.addObject("wasCommented", true);
						    	

					//enviar objetos
					    view.addObject("userTicket", new MovieUser());
						view.addObject("userRatingComment",new RatingComment());
						view.addObject("commentsRatings", commentsRatings);
						view.addObject("user", user);
					    view.addObject("movie", movie);
						
				
			return view;

			
		}
	
	
	
	//guardar valoracion
	@PostMapping(value="/saveRating/{dni}/{id}")
	public ModelAndView saveRating(@ModelAttribute ("userRatingComment") RatingComment ratingtosave, @PathVariable(name="dni") int dni, @PathVariable(name="id") int id) {
		ModelAndView view = new ModelAndView("actionsmovies");
	//busqueda usuario y pelicula	
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
		LOGGER.error("ingresando al metodo: saveRating "+ movie.getDescription()+" "+user.getLastname());
		
	//	if(movieUsertoSave.getTickets()==0 && movieUsertoSave.getCommentContent()==null) {
			ratingtosave.setUser(user);
			ratingtosave.setMovie(movie);
		//}
			
	
				LOGGER.error("ingresando al metodo: saveRating "+ ratingtosave.getCommentDate());
	//guardo rating
			
		
		try {
			ratingCommentService.saveRatingComment(ratingtosave);
		}
		catch (Exception e) {
			view.addObject("formMovieUserMessage", e.getMessage());
		}
		
		LOGGER.error("ingresando al metodo: saveRating "+ ratingtosave.getCommentDate());
		
		
		//lista de comentarios
		List<RatingComment> commentsRatings = new ArrayList<>();
		commentsRatings = ratingCommentService.findByMovieId(movie.getId());
		
		// para setear el promedio general en la pelicula	
			int length = commentsRatings.size(), sumRatings=0 , aux=0;
			
			if(length!=0) {
				for(int i=0;i<length;i++)
					sumRatings = commentsRatings.get(i).getRating() + sumRatings;		
			
				aux = (int) ((100 * sumRatings)/(length*5)); 
				movie.setAverageRating(aux);
				try {
					movieService.saveMovie(movie);
				}
				catch (Exception e) {
					view.addObject("formMovieUserMessage", e.getMessage());
				}
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
			
			
			// encuentra fila de usuario y pelicula
			MovieUser userTicket = new MovieUser();
			userTicket = movieUserService.findByMovieUserId(user.getId(), movie.getId());
			
			

			RatingComment userRatingComment = new RatingComment();
			userRatingComment = ratingCommentService.findByMovieUserId(user.getId(), movie.getId());
			
			//enviar banderas
		    if(userTicket.getTickets() == 0)
		    	view.addObject("hasTickets", false);
		    else
		    	view.addObject("hasTickets", true);
		    
		    if(userTicket.getTickets() < 3)
		    	view.addObject("purchaseLimit", false);
		    else
		    	view.addObject("purchaseLimit", true);
		    
		    if(userRatingComment.getRating() == 0)
		    	view.addObject("wasRated", false);
		    else
		    	view.addObject("wasRated", true);
		    
		    if(userRatingComment.getCommentContent() == null)
		    	view.addObject("wasCommented", false);
		    else
		    	view.addObject("wasCommented", true);
			    	

		//enviar objetos
		    view.addObject("userTicket", new MovieUser());
			view.addObject("userRatingComment",new RatingComment());
			view.addObject("commentsRatings", commentsRatings);
			view.addObject("user", user);
		    view.addObject("movie", movie);
			
		return view;

		
	}
	
	
	
	
	// guardar movieUser
	
	@PostMapping(value="/saveTicket/{dni}/{id}")
	public ModelAndView saveTicket(@ModelAttribute ("userTicket") MovieUser movieUsertoSave,@PathVariable(name="dni") int dni, @PathVariable(name="id") int id) {
		ModelAndView view = new ModelAndView("actionsmovies");
	// busco pelicula y usuario
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
		LOGGER.error("ingresando al metodo: saveTicket "+ movieUsertoSave.getTickets()+" "+user.getDni()+" "+movie.getName());
		
	
		
		
		LOGGER.error("ingresando al metodo: saveTicket ");
	
	// proceso de guardar ticket y controlar q no sea mayor q 3	
		 int totalTickets=movieUsertoSave.getTickets();
		       
		    if(totalTickets<=3) {
		    
		    	//if(movieUsertoSave.getRating()==0  && movieUsertoSave.getCommentContent()==null) {
					movieUsertoSave.setUser(user);
					movieUsertoSave.setMovie(movie);
				//}
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
		
		 // encuentra fila de usuario y pelicula
		    
		    MovieUser userTicket = new MovieUser();
			userTicket = movieUserService.findByMovieUserId(user.getId(), movie.getId());
	
			//enviar lista de comentarios 
			List<RatingComment> commentsRatings = new ArrayList<>();
			commentsRatings = ratingCommentService.findByMovieId(movie.getId());
			
			
			RatingComment userRatingComment = new RatingComment();
			userRatingComment = ratingCommentService.findByMovieUserId(user.getId(), movie.getId());
	
			
		//enviar banderas
		    if(userTicket.getTickets() == 0)
		    	view.addObject("hasTickets", false);
		    else
		    	view.addObject("hasTickets", true);
		    
		    if(userTicket.getTickets() < 3)
		    	view.addObject("purchaseLimit", false);
		    else
		    	view.addObject("purchaseLimit", true);
		    
		    if(userRatingComment.getRating() == 0)
		    	view.addObject("wasRated", false);
		    else
		    	view.addObject("wasRated", true);
		    
		    if(userRatingComment.getCommentContent() == null)
		    	view.addObject("wasCommented", false);
		    else
		    	view.addObject("wasCommented", true);
			    	

		//enviar objetos
		    view.addObject("userTicket",userTicket);
			view.addObject("userRatingComment",userRatingComment);
			view.addObject("commentsRatings", commentsRatings);
			view.addObject("user", user);
		    view.addObject("movie", movie);
			
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
		
		
	// devolver una lista ratingcomment de la pelicula, que tendra la lista de comentarios y de valoraciones, entre otras cosas
		
		
		
		
		List<RatingComment> commentsRatings = new ArrayList<>();
		commentsRatings = ratingCommentService.findByMovieId(moviefound.getId());
		
		
	

		MovieUser userTicket = new MovieUser();
		userTicket = movieUserService.findByMovieUserId(userfound.getId(), moviefound.getId());
		
		RatingComment userRatingComment = new RatingComment();
		userRatingComment = ratingCommentService.findByMovieUserId(userfound.getId(), moviefound.getId());
		
		
	//Retornar a la vista las listas, usuario, pelicula y nueva MovieUser
		
		
		view.addObject("userTicket", new MovieUser());
		view.addObject("userRatingComment",new RatingComment());
		view.addObject("commentsRatings", commentsRatings);
		view.addObject("user", userfound);
	    view.addObject("movie", moviefound);
	    
	    
	/*	agregar variables banderas para no visualizar objetos indebidos
	 Bandera q controle q no se supere la cantidad de 3 boletos
	 Bandera q controle q solo tenga 1 valoracion 	*/

	    
	    
	    if(userTicket.getTickets() == 0)
	    	view.addObject("hasTickets", false);
	    else
	    	view.addObject("hasTickets", true);
	    
	    if(userTicket.getTickets() < 3)
	    	view.addObject("purchaseLimit", false);
	    else
	    	view.addObject("purchaseLimit", true);
	    
	    if(userRatingComment.getRating() == 0)
	    	view.addObject("wasRated", false);
	    else
	    	view.addObject("wasRated", true);
	    
	    if(userRatingComment.getCommentContent() == null)
	    	view.addObject("wasCommented", false);
	    else
	    	view.addObject("wasCommented", true);
	    	
	    
	 
	    
	    LOGGER.error("saliendo del metodo: seeOptions "+ moviefound.getName() + " " +userfound.getLastname());

	    return view;
	}
	
}
