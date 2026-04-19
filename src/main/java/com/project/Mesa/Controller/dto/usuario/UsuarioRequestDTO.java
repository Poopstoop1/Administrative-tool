package com.project.Mesa.Controller.dto.usuario;

import com.project.Mesa.Model.Filial;

public record UsuarioRequestDTO(String nome, String Login, String password, String Cargo, Filial filial) {

}
