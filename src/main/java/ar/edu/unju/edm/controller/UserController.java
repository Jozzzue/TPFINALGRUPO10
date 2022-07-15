package ar.edu.unju.edm.controller;

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
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unju.edm.model.Uzer;
import ar.edu.unju.edm.service.IUserService;

@Controller
public class UserController {

	private static final Log LOGGER = LogFactory.getLog(UserController.class);
	
	@Autowired
	Uzer newUser;
	
	@Autowired
	IUserService userService;
	
	// cargar usuarios
	@GetMapping("/addUser")
	public ModelAndView addUser() {
		LOGGER.info("ingresando al metodo: addUser");
		ModelAndView view = new ModelAndView("uploadusers");
		view.addObject("user", newUser);
		view.addObject("editMode",false);
		view.addObject("editMode2",false);
		return view;
	}
	
	// guardar usuarios
	
	@PostMapping("/saveUser")
	public String saveUser(@Valid @ModelAttribute ("user") Uzer usertosave, BindingResult result, Model model) {
		if(result.hasErrors()) {
			LOGGER.fatal("Error de validacion");
			model.addAttribute("usuario", usertosave);
			model.addAttribute("editMode", false);
			return "uploadusers";
		}
			try {
				userService.saveUser(usertosave);
			} catch(Exception e) {
				model.addAttribute("formUserErrorMessage", e.getMessage());
				model.addAttribute("user", usertosave);
				model.addAttribute("editMode", false);
				LOGGER.error("saliendo del metodo: saveUser");
				return "uploadusers";
			}
		
			model.addAttribute("formUserErrorMessage", "Usuario guardado correctamente");
			model.addAttribute("user", newUser);	 
			model.addAttribute("editMode", false);
			model.addAttribute("editMode2", false);
			return "uploadusers";

		
	}
	
	// listar usuarios
	@GetMapping({"/listUsers"})	
	public ModelAndView listUser() {
		ModelAndView view = new ModelAndView("listusers");
		if(userService.showUsers().size() != 0) {
		view.addObject("users",userService.showUsers());
		LOGGER.info("ingresando al metodo: listUsers "+ userService.showUsers().get(0).getLastname());
		}
		return view;
	}
	
	
	
	// modificar usuario
	@GetMapping("/update/{id}")
	public ModelAndView modUser(Model model, @PathVariable(name="id") int id) throws Exception {
		Uzer userfound = new Uzer();
		try {
			LOGGER.error("Primer try modUsser: "+userfound.getDni()+ " " +userfound.getId());
			userfound = userService.findUser(id);
		}catch (Exception e) {
			model.addAttribute("formUserErrorMessage", e.getMessage());
		}
		if(userfound.getDni()==0)
		{
			try {
				userfound = userService.findUserByDni(id);
				LOGGER.error("Second try modUser: "+userfound.getDni()+ " " +userfound.getId());
				model.addAttribute("formUserErrorMessage",null);
			}catch (Exception e) {
				model.addAttribute("formUserErrorMessage", e.getMessage());
				
			} 
		}
		
		ModelAndView view = new ModelAndView("uploadusers");
	    view.addObject("user", userfound);
	    LOGGER.error("saliendo del metodo: modUser "+ userfound.getDni());
	    view.addObject("editMode",true);
	    view.addObject("editMode2",false);
	    return view;
	}
	
	
	@PostMapping("/editUser")
	public ModelAndView savemodUser(@Valid @ModelAttribute ("user") Uzer usertomod, BindingResult result ) {
		if(result.hasErrors()){
			LOGGER.fatal("Error de validacion");
			ModelAndView view = new ModelAndView("uploadusers");
			view.addObject("user", usertomod);
			view.addObject("editMode",true);
			view.addObject("editMode2",false);
			return view;
		}
		try{
			userService.modUser(usertomod);
		}catch(Exception e){
			ModelAndView view = new ModelAndView("uploadusers");
			view.addObject("user", usertomod);
			view.addObject("editMode",true);
			view.addObject("editMode2",false);
			view.addObject("formUserErrorMessage", e.getMessage());
			LOGGER.error("saliendo del metodo: savemodUser");
			return view;
		}
		ModelAndView view = new ModelAndView("listusers");		
		view.addObject("users", userService.showUsers());	
		view.addObject("formUserErrorMessage","Usuario modificado Correctamente");
		view.addObject("editMode2",false);
		return view;
	}
	
	@GetMapping("/update2/{dni}")
	public ModelAndView modUser2(Model model, @PathVariable(name="dni") int dni) throws Exception {
		Uzer userfound = new Uzer();
		
			try {
				userfound = userService.findUserByDni(dni);
				LOGGER.error("Second try modUser: "+userfound.getDni()+ " " +userfound.getId());
				model.addAttribute("formUserErrorMessage",null);
			}catch (Exception e) {
				model.addAttribute("formUserErrorMessage", e.getMessage());
				
			} 
		
		ModelAndView view = new ModelAndView("uploadusers");
	    view.addObject("user", userfound);
	    LOGGER.error("saliendo del metodo: modUser2 "+ userfound.getDni());
	    view.addObject("editMode2",true);
	    view.addObject("editMode",false);
	    return view;
	}
	
	@PostMapping("/editUser2")
	public ModelAndView savemodUser2(@Valid @ModelAttribute ("user") Uzer usertomod, BindingResult result ) {
		if(result.hasErrors()){
			LOGGER.fatal("Error de validacion");
			ModelAndView view = new ModelAndView("uploadusers");
			view.addObject("user", usertomod);
			view.addObject("editMode",false);
			view.addObject("editMode2",true);
			return view;
		}
		try{
			userService.modUser(usertomod);
		}catch(Exception e){
			ModelAndView view = new ModelAndView("uploadusers");
			view.addObject("user", usertomod);
			view.addObject("editMode",false);
			view.addObject("editMode2",true);
			view.addObject("formUserErrorMessage", e.getMessage());
			LOGGER.error("saliendo del metodo: savemodUser");
			return view;
		}
		ModelAndView view = new ModelAndView("userdata");		
		view.addObject("formUserErrorMessage","Usuario modificado Correctamente");
		view.addObject("user", usertomod);
		
		return view;
	}
	
	@GetMapping("/mydata/{dni}")
	public ModelAndView seeData(Model model, @PathVariable(name="dni") int dni) throws Exception {
		Uzer userfound = new Uzer();
		
			try {
				userfound = userService.findUserByDni(dni);
				LOGGER.error("Second try modUser: "+userfound.getDni()+ " " +userfound.getId());
				model.addAttribute("formUserErrorMessage",null);
			}catch (Exception e) {
				model.addAttribute("formUserErrorMessage", e.getMessage());
				
			} 
		
		ModelAndView view = new ModelAndView("userdata");
	    view.addObject("user", userfound);
	    LOGGER.error("saliendo del metodo: mydata "+ userfound.getDni());
	    return view;
	}
	
	
	// eliminar usuarios
		@RequestMapping("/del/{dni}")
		public String deleteUser(@PathVariable(name="dni") int id, Model model) {
			try {
				userService.deleteUser(id);
			}catch(Exception e){
				LOGGER.error("encontrando: usuario a eliminar");
				model.addAttribute("formUserErrorMessage", e.getMessage());
				return "redirect:/anotherUser";
			}
		    	
		    return "redirect:/listUsers";

		}
	
	
}
