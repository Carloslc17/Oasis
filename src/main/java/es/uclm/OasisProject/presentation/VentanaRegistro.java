package es.uclm.OasisProject.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import es.uclm.OasisProject.domain.controllers.GestorUsuarios;
import es.uclm.OasisProject.domain.entities.Inquilino;
import es.uclm.OasisProject.domain.entities.Propietario;
import org.springframework.ui.Model;


@Controller
public class VentanaRegistro {

	@Autowired
	private GestorUsuarios gestorUsuarios;

	@GetMapping("/")
	public String MostrarRegistro() {
		return "SeleccionUsuario";
	}
	
	// Formulario de registro de propietario
	@GetMapping("/propietario")
    public String MostrarFormularioPropietario(Model model) {
        model.addAttribute("usuario", new Propietario());
        return "FormularioPropietario"; // HTML 
    }
	
	// Registrar propietario
	@PostMapping("/propietario")
    public String RegistrarPropietario(@ModelAttribute Propietario prop, Model model) {
        boolean Exito = gestorUsuarios.registrarPropietario(prop.getLogin(), prop.getPass(), prop.getNombre(), prop.getApellidos(), prop.getDireccion());
        model.addAttribute("Exito", Exito);
        return "ResultadoRegistro"; // HTML 
    }
	
	// Formulario de registro de inquilino
    @GetMapping("/inquilino")
	public String MostrarFormularioInquilino(Model model) {
	   model.addAttribute("usuario", new Inquilino());
	   return "FormularioInquilino"; // HTML 
	}
		
	// Registrar inquilino
    @PostMapping("/inquilino")
	public String RegistrarInquilino(@ModelAttribute Inquilino inq, Model model) {
	    boolean Exito = gestorUsuarios.registrarInquilino(inq.getLogin(), inq.getPass(), inq.getNombre(), inq.getApellidos(), inq.getDireccion());
	    model.addAttribute("Exito", Exito);
	    return "ResultadoRegistro"; // HTML 
	}
}