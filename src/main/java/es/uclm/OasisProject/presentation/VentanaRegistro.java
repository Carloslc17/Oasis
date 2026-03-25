package es.uclm.OasisProject.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import es.uclm.OasisProject.domain.controllers.GestorUsuarios;
import es.uclm.OasisProject.domain.entities.Inquilino;
import es.uclm.OasisProject.domain.entities.Propietario;
import es.uclm.OasisProject.domain.entities.Usuario;
import jakarta.servlet.http.HttpSession;

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
        boolean Exito = gestorUsuarios.registrarPropietario(prop.getLogin(), prop.getPassword(), prop.getNombre(), prop.getApellidos(), prop.getDireccion());
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
	    boolean Exito = gestorUsuarios.registrarInquilino(inq.getLogin(), inq.getPassword(), inq.getNombre(), inq.getApellidos(), inq.getDireccion());
	    model.addAttribute("Exito", Exito);
	    return "ResultadoRegistro"; // HTML 
	    
    }
    
    // Formulario para iniciar sesion
	    
	@GetMapping("/login")
	public String MostrarFormularioLogin(Model model) {
		return "Inicio_Sesion"; // HTML
	
	}
	
	// Iniciar sesion
	
	@PostMapping("/login")
	public String Iniciar_Sesion(String login, String password, Model model, HttpSession Session) {
		
		
		Usuario usuario = gestorUsuarios.login(login, password);
		
		if(usuario != null) {
			Session.setAttribute("usuario", usuario);
		
		
			if(usuario instanceof Propietario) {
				return "homePropietario"; // HTML, pagina principal de propietario
			} else if(usuario instanceof Inquilino) {
				return "homeInquilino"; // HTML, pagina principal de inquilino
			}
		
		}
		
		model.addAttribute("error", true);
		return "Inicio_Sesion";
		
		
	}
}