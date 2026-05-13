package es.uclm.OasisProject.presentation;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.uclm.OasisProject.domain.controllers.GestorNotificaciones;
import es.uclm.OasisProject.domain.entities.Propietario;
import es.uclm.OasisProject.domain.entities.SolicitudReserva;
import es.uclm.OasisProject.persistence.UsuarioDAO;

@Controller
public class VentanaConfirmacionReservas {
	
	@Autowired
	private GestorNotificaciones gestorNotificaciones;
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@GetMapping("/solicitudReserva")
	public String mostrarSolicitudReserva(Principal principal, Model model) {
		
		String login = principal.getName();
		Propietario propietario = (Propietario) usuarioDAO.findByLogin(login);
		
		List<SolicitudReserva> solicitudes = gestorNotificaciones.obtenerSolicitudes(propietario);
		model.addAttribute("solicitudes",solicitudes);
				
		return "ConfirmacionReserva";
	}

}
