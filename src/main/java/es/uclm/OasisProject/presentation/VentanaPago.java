package es.uclm.OasisProject.presentation;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import es.uclm.OasisProject.domain.controllers.GestorPagos;
import es.uclm.OasisProject.domain.entities.Disponibilidad;
import es.uclm.OasisProject.domain.entities.MetodoPago;
import es.uclm.OasisProject.domain.entities.Pago;
import es.uclm.OasisProject.persistence.DisponibilidadDAO;

@Controller
public class VentanaPago {
	
	@Autowired 
	private GestorPagos gestorPagos;
	
	@Autowired 
	private DisponibilidadDAO disponibilidadDAO;
	
	@PreAuthorize("hasRole('INQUILINO')")
	@GetMapping("/completarPago")
    public String mostrarFormularioPago(@RequestParam int idDisponibilidad, Model model) {
		
		Disponibilidad disponibilidad = disponibilidadDAO.select(idDisponibilidad);

        model.addAttribute("disponibilidad", disponibilidad);
        model.addAttribute("pago", new Pago());

        return "FormularioPago";
    }
	
	@PreAuthorize("hasRole('INQUILINO')")
	@PostMapping("/completarPago")
	public String completarPago(@RequestParam int idDisponibilidad, @RequestParam MetodoPago metodoPago, Principal principal) {
	    
	    if (principal == null) {
		    return "redirect:/login";
		}
	    
	    String login = principal.getName();

	    gestorPagos.realizarPago(login, idDisponibilidad, metodoPago);

	    return "redirect:/homeInquilino";
	}

}
