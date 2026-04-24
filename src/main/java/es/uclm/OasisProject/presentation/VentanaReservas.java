package es.uclm.OasisProject.presentation;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.uclm.OasisProject.domain.controllers.GestorReservas;
import es.uclm.OasisProject.domain.entities.Inmueble;
import es.uclm.OasisProject.domain.entities.Inquilino;
import es.uclm.OasisProject.domain.entities.SolicitudReserva;
import es.uclm.OasisProject.persistence.*;

@Controller
public class VentanaReservas {

	@Autowired
	private GestorReservas gestorReservas;
	

	@Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private InmuebleDAO inmuebleDAO;
    

    @GetMapping("reservarInmueble")
    public String mostrarFormularioReserva(@RequestParam int idInmueble, Model model) {
    	
        Inmueble inmueble = inmuebleDAO.select(idInmueble);
        model.addAttribute("inmueble", inmueble);
        model.addAttribute("reserva", new SolicitudReserva());

        return "FormularioReserva"; // HTML
    }
    
    

    @PostMapping("reservarInmueble")
    public String reservarInmueble(@ModelAttribute SolicitudReserva reserva, @RequestParam int idInmueble, Model model, Principal principal) {
    	
        String login = principal.getName();
        Inquilino inquilino = (Inquilino) usuarioDAO.findByLogin(login);

        boolean exito = gestorReservas.crearSolicitud(
                inquilino.getId(),
                idInmueble,
                reserva.getFechaInicio(),
                reserva.getFechaFin(),
                reserva.getPoliticaCancelacion()
        );

        model.addAttribute("Exito", exito);
        return "ReservaRegistrada"; // HTML
    }




}
