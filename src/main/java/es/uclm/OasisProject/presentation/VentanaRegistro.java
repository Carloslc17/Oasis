package es.uclm.OasisProject.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import es.uclm.OasisProject.domain.controllers.GestorUsuarios;
import es.uclm.OasisProject.domain.entities.Inquilino;
import es.uclm.OasisProject.domain.entities.Propietario;
import org.springframework.ui.Model;


@Controller
public class VentanaRegistro {

	@Autowired
	private GestorUsuarios gestorUsuarios;

	@GetMapping("/registro")
	public String mostrarRegistro() {
		return "SeleccionUsuario";
	}
	
	// Formulario de registro de propietario
	@GetMapping("/registro/propietario")
    public String msostrarFormularioPropietario(Model model) {
        model.addAttribute("usuario", new Propietario());
        return "FormularioPropietario"; // HTML 
    }
	
	// Registrar propietario
	@PostMapping("/registro/propietario")
    public String registrarPropietario(@ModelAttribute Propietario prop, RedirectAttributes redirectAttrs ) {
        boolean exito = gestorUsuarios.registrarPropietario(prop.getLogin(), prop.getPassword(), prop.getNombre(), prop.getApellidos(), prop.getDireccion());

		if (exito) {
		        redirectAttrs.addFlashAttribute("mensaje", "Registro correcto");
		} else {
		        redirectAttrs.addFlashAttribute("error", "El usuario ya existe");
		}
        return "redirect:/login"; // HTML 
    }
	
	// Formulario de registro de inquilino
    @GetMapping("/registro/inquilino")
	public String mostrarFormularioInquilino(Model model) {
	   model.addAttribute("usuario", new Inquilino());
	   return "FormularioInquilino"; // HTML 
	}
		
	// Registrar inquilino
    @PostMapping("/registro/inquilino")
	public String registrarInquilino(@ModelAttribute Inquilino inq, RedirectAttributes redirectAttrs) {
	    boolean exito = gestorUsuarios.registrarInquilino(inq.getLogin(), inq.getPassword(), inq.getNombre(), inq.getApellidos(), inq.getDireccion());
	    if (exito) {
	        redirectAttrs.addFlashAttribute("mensaje", "Registro correcto");
	    } else {
	        redirectAttrs.addFlashAttribute("error", "El usuario ya existe");
	    }
	    return "redirect:/login"; // HTML 
	    
    }
    
    // Pagina inicio sesion
    @GetMapping("/login")
    public String mostrarLogin() {
        return "Inicio_Sesion"; 
    }
    
	// Pagina principal propietario
	
    @PreAuthorize("hasRole('PROPIETARIO')")
	@GetMapping("/homePropietario")
	public String homePropietario() {
		return "HomePropietario";
	}
	
	// Pagina principal inquilino
		    
    @PreAuthorize("hasRole('INQUILINO')")
	@GetMapping("/homeInquilino")
	public String homeInquilino() {
		return "HomeInquilino";
	}
		
		
}