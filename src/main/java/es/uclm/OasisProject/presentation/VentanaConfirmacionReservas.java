package es.uclm.OasisProject.presentation;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import es.uclm.OasisProject.domain.controllers.GestorNotificaciones;
import es.uclm.OasisProject.domain.entities.SolicitudReserva;

@Controller
public class VentanaConfirmacionReservas {
	
	@Autowired
	private GestorNotificaciones gestorNotificaciones;
	
	@PreAuthorize("hasRole('PROPIETARIO')")
	@GetMapping("/solicitudReserva")
	public String mostrarSolicitudReserva(Principal principal, Model model) {

		if (principal == null) {
		    return "redirect:/login";
		}
		
		String login = principal.getName();
		
		List<SolicitudReserva> solicitudes = gestorNotificaciones.obtenerSolicitudes(login);
		model.addAttribute("solicitudes",solicitudes);
				
		return "ConfirmacionReserva";
	}

}
