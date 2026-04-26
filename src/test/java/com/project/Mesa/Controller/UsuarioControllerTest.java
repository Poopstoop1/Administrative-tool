package com.project.Mesa.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import com.project.Mesa.Controller.Impl.UsuarioControllerImpl;
import com.project.Mesa.Controller.dto.usuario.UsuarioRequestDTO;
import com.project.Mesa.Controller.dto.usuario.UsuarioResponseDTO;
import com.project.Mesa.Service.SecurityFilter;
import com.project.Mesa.Service.TokenService;
import com.project.Mesa.Service.UsuarioService;



@WebMvcTest(controllers = UsuarioControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false) 
public class UsuarioControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;
    
    @MockBean
    private TokenService tokenService;

    @MockBean
    private SecurityFilter securityFilter;
    
    @Autowired
    private ObjectMapper objectMapper;

    private final String URL = "/usuario";
    
    private UsuarioRequestDTO request ;
    private UsuarioResponseDTO response;
    
    @BeforeEach
    void setup() {
    	request = new UsuarioRequestDTO(
                "Paulo",
                "PauloLogin",
                "123456",
                "Marketing",
                "98.765.432/0001-15"
        );
    	
    	response = new UsuarioResponseDTO(
    	        1L,
    	        "Paulo",
    	        "PauloLogin",
    	        "Marketing",
    	        "98.765.432/0001-15"
    	    );
   
    }
    @Test
    @DisplayName("Deve criar usuário com sucesso")
    void deveCriarUsuario() throws Exception {

        when(usuarioService.adicionarUsuario(any()))
        .thenReturn(response);
        
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Paulo"))
                .andExpect(jsonPath("$.login").value("PauloLogin"))
                .andExpect(jsonPath("$.cargo").value("Marketing"))
                .andExpect(jsonPath("$.cnpj").value("98.765.432/0001-15"));
        
        }

    @Test
    @DisplayName("Deve listar usuários")
    void deveListarUsuarios() throws Exception {

        when(usuarioService.listarUsuarios())
                .thenReturn(List.of(response));

        mockMvc.perform(get(URL))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1))
        .andExpect(jsonPath("$[0].id").value(1L))
        .andExpect(jsonPath("$[0].nome").value("Paulo"))
        .andExpect(jsonPath("$[0].login").value("PauloLogin"))
        .andExpect(jsonPath("$[0].cargo").value("Marketing"))
        .andExpect(jsonPath("$[0].cnpj").value("98.765.432/0001-15"));
    }

    @Test
    @DisplayName("Deve remover usuário")
    void deveRemoverUsuario() throws Exception {

        doNothing().when(usuarioService).deletarUsuario(1L);

        mockMvc.perform(delete(URL + "/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve editar usuário")
    void deveEditarUsuario() throws Exception {


        when(usuarioService.atualizarUsuario(any(), any()))
                .thenReturn(response);

        mockMvc.perform(put(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Paulo"))
                .andExpect(jsonPath("$.login").value("PauloLogin"))
                .andExpect(jsonPath("$.cargo").value("Marketing"))
                .andExpect(jsonPath("$.cnpj").value("98.765.432/0001-15"));
    }


}
