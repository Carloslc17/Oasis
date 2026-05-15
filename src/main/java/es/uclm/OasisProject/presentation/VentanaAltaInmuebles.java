package es.uclm.OasisProject.presentation;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import es.uclm.OasisProject.domain.controllers.GestorInmuebles;
import es.uclm.OasisProject.domain.entities.*;

@Controller
public class VentanaAltaInmuebles {
	
	@Autowired
	private GestorInmuebles gestorInmuebles;
	
	// Formulario para registrar inmueble
	@GetMapping("/registrarInmueble")
	public String mostrarFormularioInmueble(Model model) {
		model.addAttribute("inmueble", new Inmueble());
		return "FormularioInmueble"; // HTML
	}
	
	
	// Registrar inmueble
	@PostMapping("/registrarInmueble")
	public String registrarInmueble(@ModelAttribute Inmueble inmueble, Model model, Principal principal) {
		
		String login = principal.getName();

	    boolean exito = gestorInmuebles.registrarInmueble(inmueble, login);

	    model.addAttribute("Exito", exito);
		return "redirect:/misInmuebles"; // HTML
	}
	
	// Pagina para que el propietario pueda ver sus inmuebles
	@GetMapping("/misInmuebles")
	public String misInmuebles(Model model, Principal principal) {

	    String login = principal.getName();

	    List<Inmueble> inmuebles = gestorInmuebles.obtenerInmuebles(login);

	    model.addAttribute("inmuebles", inmuebles);

	    return "Inmuebles";
	}
	
	// Formulario para anadir disponibilidad al inmueble
	@GetMapping("/anadirDisponibilidad/{id}")
	public String mostrarFormularioDisponibilidad(@PathVariable int id, Model model) {

	    model.addAttribute("disponibilidad", new Disponibilidad());
	    model.addAttribute("idInmueble", id);

	    return "FormularioDisponibilidad";
	}
	
	// Anadir disponibilidad
	@PostMapping("/anadirDisponibilidad")
	public String registrarDisponibilidad(@ModelAttribute Disponibilidad disponibilidad, @RequestParam int idInmueble, @RequestParam PoliticaCancelacion politica, Model model) {
		
	    boolean exito = gestorInmuebles.anadirDisponibilidad(disponibilidad, idInmueble);

	    // Flash attribute aqui
	    model.addAttribute("Exito", exito);

	    return "redirect:/misInmuebles";

	}
	
}
