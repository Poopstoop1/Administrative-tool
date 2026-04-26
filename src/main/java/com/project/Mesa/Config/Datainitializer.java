package com.project.Mesa.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.project.Mesa.Controller.dto.filial.FilialRequestDTO;
import com.project.Mesa.Controller.dto.usuario.UsuarioRequestDTO;
import com.project.Mesa.Repository.FilialRepository;
import com.project.Mesa.Repository.UsuarioRepository;
import com.project.Mesa.Service.FilialService;
import com.project.Mesa.Service.UsuarioService;
import com.project.Mesa.Validation.validators.CNPJValidator;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class Datainitializer {

    private final UsuarioService usuarioService;
    private final FilialService filialService;
    private final UsuarioRepository usuarioRepository;
    private final FilialRepository filialRepository;
    private final CNPJValidator cnpjValidator;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
      
            criarFilialSeNaoExistir("12.345.678/0001-95", "Saboris Gourmet", "Saboris Gourmeet LTDA");
            criarFilialSeNaoExistir("98.765.432/0001-10", "Delícia Viva", "Delícia Viva LTDA");
            criarFilialSeNaoExistir("98.765.432/0001-11", "Bela Mesa Alimentos", "Bela Mesa Alimentos LTDA");
            criarFilialSeNaoExistir("98.765.432/0001-12", "Essência do Sabor", "Essência do Sabor LTDA");
            criarFilialSeNaoExistir("98.765.432/0001-13", "Porto Digital", "Porto Digital LTDA");
            criarFilialSeNaoExistir("98.765.432/0001-14", "Sabores do Campo", "Sabores do Campo LTDA");

            criarUsuarioSeNaoExistir("Mariana Soares", "MarianaSoares", "Gerente", "12.345.678/0001-95", "X#x123456");
            criarUsuarioSeNaoExistir("Carla Ferreira", "CarlaFerreira", "Gerente", "98.765.432/0001-10", "X#x123456");
            criarUsuarioSeNaoExistir("Gabriel Lima", "GabrielLima", "Gerente", "98.765.432/0001-11", "X#x123456");
            criarUsuarioSeNaoExistir("Eryson Moreira", "ErysonMoreira", "Administrador", "98.765.432/0001-13", "X#x123456");
        };
    }

    private void criarUsuarioSeNaoExistir(String nome, String login, String cargo, String cnpjEmpresa, String senha) {
		if(usuarioRepository.existsByLogin(login))
			return ;
		usuarioService.adicionarUsuario(new UsuarioRequestDTO(nome, login, senha, cargo, cnpjEmpresa));
	}

	private void criarFilialSeNaoExistir(String cnpj, String nome, String razaosocial) {
		String cnpjLimpo = cnpjValidator.validarCNPJ(cnpj);
		if(filialRepository.existsById(cnpjLimpo))
			return;
		filialService.adicionarFilial(new FilialRequestDTO(cnpj, nome, razaosocial));
	}
}
