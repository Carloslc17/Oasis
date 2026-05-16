package es.uclm.OasisProject.presentation;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import es.uclm.OasisProject.domain.controllers.GestorBusquedas;
import es.uclm.OasisProject.domain.entities.Inmueble;
import es.uclm.OasisProject.domain.entities.PoliticaCancelacion;

@Controller
public class VentanaBusqueda {
	
	@Autowired
	private GestorBusquedas gestorBusquedas;

	// Mostrar formulario para buscar inmuebles
	
	@PreAuthorize("hasRole('INQUILINO')")
	@GetMapping("/busqueda")
    public String mostrarFormularioBusqueda() {
        return "Busquedas";
    }
	
	// Buscar inmuebles

	@PreAuthorize("hasRole('INQUILINO')")
    @PostMapping("/buscarInmuebles")
    public String buscarInmuebles(@RequestParam LocalDate fechaInicio, @RequestParam LocalDate fechaFin,
                                 @RequestParam(required = false) Boolean directa,  @RequestParam(required = false) PoliticaCancelacion politica, Model model) {
    	
		if (fechaInicio.isAfter(fechaFin)) {
		    model.addAttribute("error", "Fechas inválidas");
		    return "Busquedas";
		}


        List<Inmueble> resultados = gestorBusquedas.buscarInmuebles(fechaInicio, fechaFin, directa, politica);

        model.addAttribute("resultados", resultados);
        return "ResultadosBusqueda";
    }
    
    // Añadir inmueble a lista de deseos
    
    @PreAuthorize("hasRole('INQUILINO')")
    @PostMapping("/anadirFavorito")
    public String anadirFavorito(@RequestParam int idInmueble, Principal principal, RedirectAttributes redirectAttributes) {

		if (principal == null) {
		    return "redirect:/login";
		}

        String login = principal.getName();

        boolean exito = gestorBusquedas.anadirInmueble(login, idInmueble);

        if (exito) {
            redirectAttributes.addFlashAttribute("mensaje", "Añadido a favoritos");
        } else {
            redirectAttributes.addFlashAttribute("error", "No se pudo añadir");
        }

        return "redirect:/busqueda";
    }
}
