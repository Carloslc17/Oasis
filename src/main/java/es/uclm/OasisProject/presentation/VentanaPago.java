package es.uclm.OasisProject.presentation;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.uclm.OasisProject.domain.controllers.GestorPagos;
import es.uclm.OasisProject.domain.entities.Disponibilidad;
import es.uclm.OasisProject.domain.entities.Inquilino;
import es.uclm.OasisProject.domain.entities.Pago;
import es.uclm.OasisProject.persistence.DisponibilidadDAO;
import es.uclm.OasisProject.persistence.UsuarioDAO;

@Controller
public class VentanaPago {
	
	@Autowired 
	private GestorPagos gestorPagos;
	
	@Autowired 
	private DisponibilidadDAO disponibilidadDAO;
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@GetMapping("/completarPago")
    public String mostrarFormularioPago(@RequestParam int idDisponibilidad, Model model) {

        Disponibilidad disponibilidad = disponibilidadDAO.select(idDisponibilidad);

        model.addAttribute("disponibilidad", disponibilidad);
        model.addAttribute("pago", new Pago());

        return "FormularioPago";
    }
	
	@PostMapping("/completarPago")
	public String completarPago(@ModelAttribute Pago pago, @RequestParam int idDisponibilidad, Principal principal) {

	    String login = principal.getName();

	    Inquilino inquilino = (Inquilino) usuarioDAO.findByLogin(login);

	    gestorPagos.realizarPago(inquilino.getId(), idDisponibilidad, pago.getMetodoPago());

	    return "redirect:/homeInquilino";
	}

}
