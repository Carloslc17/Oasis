package es.uclm.OasisProject.presentation;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import es.uclm.OasisProject.domain.controllers.GestorReservas;
import es.uclm.OasisProject.domain.entities.Disponibilidad;
import es.uclm.OasisProject.domain.entities.Inquilino;
import es.uclm.OasisProject.persistence.*;

@Controller
public class VentanaReservas {

	@Autowired
	private GestorReservas gestorReservas;
	
	@Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private DisponibilidadDAO disponibilidadDAO;
    

    @GetMapping("/reservarInmueble")
    public String mostrarFormularioReserva(@RequestParam int idDisponibilidad, Model model) {

        Disponibilidad disponibilidad = disponibilidadDAO.select(idDisponibilidad);

        model.addAttribute("disponibilidad", disponibilidad);

        return "FormularioReserva";
    }
    
    

    @PostMapping("/reservarInmueble")
    public String reservarInmueble(@RequestParam int idDisponibilidad, Principal principal) {

        String login = principal.getName();

        Inquilino inquilino = (Inquilino) usuarioDAO.findByLogin(login);
        Disponibilidad disponibilidad = disponibilidadDAO.select(idDisponibilidad);
        
        if(disponibilidad.isDirecta()) {
        	return "redirect:/completarPago?idDisponibilidad="+ idDisponibilidad;
        } else { 
        	gestorReservas.crearSolicitud(inquilino.getId(), idDisponibilidad);
        }
        
        return "redirect:/homeInquilino";
    }




}
