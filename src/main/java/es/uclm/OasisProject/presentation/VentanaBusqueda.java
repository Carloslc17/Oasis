package es.uclm.OasisProject.presentation;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import es.uclm.OasisProject.domain.controllers.GestorBusquedas;
import es.uclm.OasisProject.domain.entities.Inmueble;

@Controller
public class VentanaBusqueda {
	
	@Autowired
	private GestorBusquedas gestorBusquedas;
	

	@GetMapping("/busqueda")
    public String mostrarFormularioBusqueda() {
        return "Busquedas";
    }
	

    @PostMapping("/buscarInmuebles")
    public String buscarInmuebles(@RequestParam LocalDate fechaInicio, @RequestParam LocalDate fechaFin, Model model) {

        List<Inmueble> resultados = gestorBusquedas.buscarInmuebles(fechaInicio, fechaFin);

        model.addAttribute("resultados", resultados);
        return "ResultadosBusqueda";
    }



}
