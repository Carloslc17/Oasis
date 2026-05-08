package es.uclm.OasisProject.presentation;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.uclm.OasisProject.domain.controllers.GestorBusquedas;
import es.uclm.OasisProject.domain.entities.Inmueble;
import es.uclm.OasisProject.persistence.InmuebleDAO;

@Controller
public class VentanaBusqueda {
	
	@Autowired
	private GestorBusquedas gestorBusquedas;
	
	@Autowired
    InmuebleDAO inmuebleDAO;
	

	@GetMapping("/busqueda")
    public String mostrarFormularioBusqueda() {
        return "Busquedas";
    }
	

    @PostMapping("/buscarInmuebles")
    public String buscarInmuebles(@RequestParam LocalDate fechaInicio,
                                 @RequestParam LocalDate fechaFin,
                                 @RequestParam(required = false) Boolean directa,
                                 @RequestParam(required = false) String politica,
                                 Model model) {

        List<Inmueble> resultados = gestorBusquedas.buscarInmuebles(fechaInicio, fechaFin, directa, politica);

        model.addAttribute("resultados", resultados);
        return "ResultadosBusqueda";
    }
    
    @PostMapping("/anadirFavorito")
    public String anadirFavorito(@RequestParam int idInmueble, Principal principal, RedirectAttributes redirectAttributes) {

        String login = principal.getName();
        
        Inmueble inmueble = inmuebleDAO.select(idInmueble);

        boolean exito = gestorBusquedas.anadirInmueble(login, inmueble);

        if (exito) {
            redirectAttributes.addFlashAttribute("mensaje", "Añadido a favoritos");
        } else {
            redirectAttributes.addFlashAttribute("error", "No se pudo añadir");
        }

        return "redirect:/buscarInmuebles";
    }



}
