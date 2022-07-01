package ar.edu.unju.edm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ar.edu.unju.edm.model.Uzer;
import ar.edu.unju.edm.service.imp.IUserServiceImp;

@Controller
public class LoginController {
		
	@Autowired
	private IUserServiceImp userService;
	@Autowired
	private Uzer anUser;
	
	@GetMapping({"/login"})
	public String ingresarLogin(Model model) {
		model.addAttribute("usuarioDelForm",anUser);
		return "login";
	}
	
	/*@PostMapping("/ingresarUsuario")
	public String loginUsuario(@ModelAttribute("usuarioDelForm") Uzer usuario, ModelMap model) throws Exception{
		String redireccion = "";
			try {				
				Uzer usuarioEncontrado = userService. .encontrarUsuarioDni(usuario);
				//usuarioService.validarCredenciales(usuarioEncontrado.getPassword(),usuario.getPassword());
				if (usuarioEncontrado == null) {		        
		            throw new Exception("No hay coincidencia entre dni or password.");
		        }
				redireccion = userService.redirigirUsuario(usuarioEncontrado);
				model.addAttribute("usuarioDelForm", usuarioEncontrado);
				return redireccion;	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// pasar las excepciones al html
				model.addAttribute("formLoginErrorMessage",e.getMessage());
				model.addAttribute("usuarioDelForm", anUser);
				return "/login";
			}			
	}*/
}
