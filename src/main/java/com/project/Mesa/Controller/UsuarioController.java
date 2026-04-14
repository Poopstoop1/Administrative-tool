package com.project.Mesa.Controller;

import com.project.Mesa.Model.Users;
import com.project.Mesa.Repository.UserRepository;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UsuarioController {

	private final PasswordEncoder passwordEncoder;
	
	private final UserRepository userRepository;
	
	public UsuarioController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@RequestMapping("/usuarios")
	public String user(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();


		if(username != null){
			String nomeFormatado = formatarNome(username);
			model.addAttribute("message", nomeFormatado);
		}
		 model.addAttribute("usuarios", userRepository.findAll());
		model.addAttribute("usuarioobj", new Users());
		return "/paginas/gestaodeusuarios";
	}

	private String formatarNome(String username) {
		// Divide o nome em letras maiúsculas e minúsculas
		// Exemplo: "ErysonMoreira" -> "Eryson Moreira"
		return username.replaceAll("([a-z])([A-Z])", "$1 $2");
	}
	
	
    @RequestMapping(method = RequestMethod.POST, value = "/salvarusuarios")
    public String salvar(@ModelAttribute Users user) {
    	Users UsersDTO = new Users();
    	UsersDTO.setLogin(user.getLogin());
    	UsersDTO.setPassword(passwordEncoder.encode(user.getPassword()));
    	UsersDTO.setCargo(user.getCargo());
    	UsersDTO.setEmpresa(user.getEmpresa());
        
        userRepository.save(UsersDTO);
        return "redirect:/usuarios";
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/editarusuarios")
	 public ModelAndView editarUsuarios(@ModelAttribute Users user) {
	     // Busca a filial pelo CNPJ
	     Optional<Users> userExistente = userRepository.findById(user.getId());
	     
	     if (userExistente.isPresent()) {
	         // Atualiza os dados da filial existente
	         Users existente = userExistente.get();
	         existente.setLogin(user.getLogin());
	         existente.setPassword(passwordEncoder.encode(user.getPassword()));
	         existente.setCargo(user.getCargo());
	         existente.setEmpresa(user.getEmpresa());
	         
	         // Salva as alterações no banco
	         userRepository.save(existente);
	     } else {
	         System.out.println("User não encontrado: " + user.getId());
	     }
	     
	     // Redireciona para a página de gestão de filiais
	     return new ModelAndView("redirect:/usuarios");
	 }

    @RequestMapping(method = RequestMethod.GET, value = "/removerusuario/{idusuario}")
    public String excluir(@PathVariable("idusuario") Long idusuario) {
        userRepository.deleteById(idusuario);
        return "redirect:/usuarios";
    }
    
}
