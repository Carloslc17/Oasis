package es.uclm.OasisProject.presentation;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import es.uclm.OasisProject.domain.controllers.GestorNotificaciones;
import es.uclm.OasisProject.domain.controllers.GestorReservas;
import es.uclm.OasisProject.domain.entities.Disponibilidad;
import es.uclm.OasisProject.domain.entities.Reserva;
import es.uclm.OasisProject.domain.entities.SolicitudReserva;
import es.uclm.OasisProject.persistence.*;

@Controller
public class VentanaReservas {

	@Autowired
	private GestorReservas gestorReservas;

    @Autowired
    private DisponibilidadDAO disponibilidadDAO;
    
    @Autowired
    private GestorNotificaciones gestorNotificaciones;
    
    @PreAuthorize("hasRole('INQUILINO')")
    @GetMapping("/reservarInmueble")
    public String mostrarFormularioReserva(@RequestParam int idDisponibilidad, Model model) {

        Disponibilidad disponibilidad = disponibilidadDAO.select(idDisponibilidad);

        model.addAttribute("disponibilidad", disponibilidad);

        return "FormularioReserva";
    }
    
    
    @PreAuthorize("hasRole('INQUILINO')")
    @PostMapping("/reservarInmueble")
    public String reservarInmueble(@RequestParam int idDisponibilidad, Principal principal, RedirectAttributes redirectAttributes) {
    	
    	if (principal == null) {
		    return "redirect:/login";
		}

        String login = principal.getName();
        
        if(gestorReservas.directa(idDisponibilidad)) {
        	return "redirect:/completarPago?idDisponibilidad="+ idDisponibilidad; // Reserva inmediata
        } 
        
        boolean exito = gestorReservas.crearSolicitud(login, idDisponibilidad); // Solicitud a propietario
        

        if (!exito) {
        	redirectAttributes.addFlashAttribute("error", "No se pudo crear la solicitud");
        } else {
        	redirectAttributes.addFlashAttribute("mensaje", "Solicitud enviada correctamente");
        }	

        return "redirect:/homeInquilino";
    }

    @PreAuthorize("hasRole('INQUILINO')")
    @GetMapping("/misReservas")
    public String mostrarReservas(Model model, Principal principal) {

        String login = principal.getName();

        List<Reserva> reservas = gestorReservas.getReservas(login);

        model.addAttribute("reservas", reservas);

        return "ReservasUsuario";
    }
    
    @PreAuthorize("hasRole('PROPIETARIO')")
    @GetMapping("/reservas")
    public String verSolicitudes(Principal principal, Model model) {

        String login = principal.getName();

        List<SolicitudReserva> solicitudes =  gestorNotificaciones.obtenerSolicitudes(login);

        model.addAttribute("solicitudes", solicitudes);

        return "Solicitudes";
    }
    
}
