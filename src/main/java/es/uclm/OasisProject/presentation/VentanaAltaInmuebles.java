package es.uclm.OasisProject.presentation;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import es.uclm.OasisProject.domain.controllers.GestorInmuebles;
import es.uclm.OasisProject.domain.entities.*;
import es.uclm.OasisProject.persistence.UsuarioDAO;

@Controller
public class VentanaAltaInmuebles {
	
	@Autowired
	private GestorInmuebles gestorInmuebles;
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	// Formulario para registrar inmueble
	@GetMapping("registrarInmueble")
	public String MostrarFormularioInmueble(Model model) {
		model.addAttribute("inmueble", new Inmueble());
		return "FormularioInmueble"; // HTML
	}
	
	
	// Registrar inmueble
	@PostMapping("registrarInmueble")
	public String RegistrarInmueble(@ModelAttribute Inmueble inmueble, Model model, Principal principal) {
		
		String login = principal.getName();

	    Propietario propietario = (Propietario) usuarioDAO.findByLogin(login);
	    inmueble.setOwner(propietario);

	    boolean exito = gestorInmuebles.registrarInmueble(inmueble);

	    model.addAttribute("Exito", exito);
		return "InmuebleRegistrado"; // HTML
	}

}
