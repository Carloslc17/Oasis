package es.uclm.OasisProject.presentation;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.uclm.OasisProject.domain.controllers.GestorNotificaciones;
import es.uclm.OasisProject.domain.entities.SolicitudReserva;

@Controller
public class VentanaConfirmacionReservas {
	
	@Autowired
	private GestorNotificaciones gestorNotificaciones;
	
	@PreAuthorize("hasRole('PROPIETARIO')")
	@GetMapping("/reservas/solicitud")
	public String mostrarSolicitudReserva(@RequestParam int idSolicitud, Principal principal, Model model) {
		
		SolicitudReserva solicitud = gestorNotificaciones.getSolicitud(idSolicitud);
		model.addAttribute("solicitud", solicitud);
				
		return "ConfirmacionReserva";
	}

}
