package ar.edu.unju.edm.controller;

import java.util.Base64;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unju.edm.model.Movie;
import ar.edu.unju.edm.service.IMovieService;


@Controller
public class MovieController {

	private static final Log LOGGER = LogFactory.getLog(MovieController.class);
	
	@Autowired
	Movie newMovie;
	
	@Autowired
	IMovieService movieService;
	
	// cargar peliculas
	@GetMapping("/anotherMovie")
	public ModelAndView addMovie() {
		LOGGER.info("ingresando al metodo: addMovie");
		ModelAndView view = new ModelAndView("uploadmovies");
		view.addObject("movie", newMovie);
		view.addObject("editMode",false);
		return view;
	}
	
	// guardar peliculas
	@PostMapping(value="/saveMovie" , consumes = "multipart/form-data")
	public String saveMovie(@Valid @ModelAttribute ("movie") Movie movietosave, BindingResult result, Model model, @RequestParam("file") MultipartFile file) {
		if(result.hasErrors()) {
			LOGGER.fatal("Error de validacion");
			model.addAttribute("usuario", movietosave);
			model.addAttribute("editMode", false);
			return "uploadmovies";
		}
			try {
				byte[] content = file.getBytes();
				String base64 = Base64.getEncoder().encodeToString(content);
				movietosave.setImage(base64);
				movieService.saveMovie(movietosave);
			} catch(Exception e) {
				model.addAttribute("formMovieMessage", e.getMessage());
				model.addAttribute("movie", movietosave);
				model.addAttribute("editMode", false);
				LOGGER.error("saliendo del metodo: saveMovie");
				return "uploadmovies";
			}
		
			model.addAttribute("formMovieMessage", "Pelicula guardada correctamente");
			model.addAttribute("user", newMovie);	 
			model.addAttribute("editMode", false);
			return "uploadmovies";

		
	}
	
	// listar peliculas
	@GetMapping({"/listMovies"})	
	public ModelAndView listMovie() {
		ModelAndView view = new ModelAndView("listmovies");
		if(movieService.showMovies().size() != 0) {
		view.addObject("movies",movieService.showMovies());
		LOGGER.info("ingresando al metodo: listMovies "+ movieService.showMovies().get(0).getName());
		}
		return view;
	}
	
	@GetMapping({"/listMovies2"})	
	public ModelAndView listMovie2() {
		ModelAndView view = new ModelAndView("listmovies2");
		if(movieService.showMovies().size() != 0) {
		view.addObject("movies",movieService.showMovies());
		LOGGER.info("ingresando al metodo: listMovies2 "+ movieService.showMovies().get(0).getName());
		}
		return view;
	}
	
	
	
	// modificar pelicula
	@GetMapping("/updateMovie/{id}")
	public ModelAndView modMovie(Model model, @PathVariable(name="id") int id) throws Exception {
		Movie moviefound = new Movie();
		try {
			moviefound = movieService.findMovie(id);		
		}
		catch (Exception e) {
			model.addAttribute("formMovieMessage", e.getMessage());
		}
		ModelAndView view = new ModelAndView("uploadmovies");
	    view.addObject("movie", moviefound);
	    LOGGER.error("saliendo del metodo: modMovie "+ moviefound.getName());
	    view.addObject("editMode",true);
	    return view;
	}
	
	
	@PostMapping("/editMovie")
	public ModelAndView savemodMovie(@Valid @ModelAttribute ("movie") Movie movietomod, BindingResult result ) {
		if(result.hasErrors()){
			LOGGER.fatal("Error de validacion");
			ModelAndView view = new ModelAndView("uploadmovies");
			view.addObject("movie", movietomod);
			view.addObject("editMode",true);
			return view;
		}
		try{
			movieService.modMovie(movietomod);
		}catch(Exception e){
			ModelAndView view = new ModelAndView("uploadmovies");
			view.addObject("movie", movietomod);
			view.addObject("editMode",true);
			view.addObject("formMovieMessage", e.getMessage());
			LOGGER.error("saliendo del metodo: savemodMovie");
			return view;
		}
		ModelAndView view = new ModelAndView("listmovies");		
		view.addObject("movies", movieService.showMovies());	
		view.addObject("formMovieMessage","Pelicula modificada Correctamente");
		
		return view;
	}
	
	// eliminar peliculas
		@RequestMapping("/delMovie/{id}")
		public String deleteUser(@PathVariable(name="id") int id, Model model) {
			try {
				movieService.deleteMovie(id);
			}catch(Exception e){
				LOGGER.error("encontrando: pelicula a eliminar");
				model.addAttribute("formMovieMessage", e.getMessage());
				return "redirect:/anotherMovie";
			}
		    	
		    return "redirect:/listMovies";

		}
	
	
}
