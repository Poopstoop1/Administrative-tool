package com.project.Mesa.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Mesa.Controller.Impl.FilialControllerImpl;
import com.project.Mesa.Controller.dto.filial.FilialRequestDTO;
import com.project.Mesa.Controller.dto.filial.FilialResponseDTO;
import com.project.Mesa.Service.FilialService;
import com.project.Mesa.Service.SecurityFilter;
import com.project.Mesa.Service.TokenService;
import com.project.Mesa.Service.exception.ResourceNotFoundException;
import com.project.Mesa.Service.exception.ValidationException;

@WebMvcTest(FilialControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false)
public class FilialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilialService filialService;

    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private TokenService tokenService;

    @MockBean
    private SecurityFilter securityFilter;

    private final String URL = "/filial";
    private final String CNPJ_VALIDO = "12345678000190";
    private final String CNPJ_INEXISTENTE = "99999999999999";
    
    private FilialRequestDTO requestValido;
    private FilialResponseDTO responseValido;

    @BeforeEach
    void setup() {
        requestValido = new FilialRequestDTO(CNPJ_VALIDO, "Filial Centro", "Empresa Centro LTDA");

        responseValido = new FilialResponseDTO(
            CNPJ_VALIDO,
            "Filial Centro",
            "Empresa Centro LTDA"
        );
    }

    
    @Test
    @DisplayName("Deve criar filial com sucesso e retornar 201 CREATED")
    void deveCriarFilialComSucesso() throws Exception {
        when(filialService.adicionarFilial(any(FilialRequestDTO.class)))
            .thenReturn(responseValido);

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cnpj").value(CNPJ_VALIDO))
                .andExpect(jsonPath("$.nome").value("Filial Centro"))
                .andExpect(jsonPath("$.razaosocial").value("Empresa Centro LTDA"));
    }

    @Test
    @DisplayName("Deve retornar 400 BAD REQUEST quando criar com dados inválidos")
    void deveRetornarBadRequestQuandoDadosInvalidos() throws Exception {
        FilialRequestDTO requestInvalido = new FilialRequestDTO("", "", "");

        when(filialService.adicionarFilial(any()))
            .thenThrow(new ValidationException("Dados inválidos"));

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestInvalido)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    @DisplayName("Deve retornar 400 Bad Request quando CNPJ já existe")
    void deveRetornarConflictQuandoCnpjJaExiste() throws Exception {
        when(filialService.adicionarFilial(any(FilialRequestDTO.class)))
            .thenThrow(new ValidationException("CNPJ já cadastrado"));

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve listar todas as filiais com sucesso")
    void deveListarFiliaisComSucesso() throws Exception {
        FilialResponseDTO filial2 = new FilialResponseDTO(
            "98.765.432/0001-12",
            "Filial Norte",
            "Empresa Norte S/A"
        );

        List<FilialResponseDTO> filiais = Arrays.asList(responseValido, filial2);

        when(filialService.listarFiliais()).thenReturn(filiais);

        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].cnpj").value(CNPJ_VALIDO))
                .andExpect(jsonPath("$[0].nome").value("Filial Centro"))
                .andExpect(jsonPath("$[0].razaosocial").value("Empresa Centro LTDA"))
                .andExpect(jsonPath("$[1].cnpj").value("98.765.432/0001-12"))
                .andExpect(jsonPath("$[1].nome").value("Filial Norte"))
                .andExpect(jsonPath("$[1].razaosocial").value("Empresa Norte S/A"));
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há filiais")
    void deveRetornarListaVaziaQuandoNaoHaFiliais() throws Exception {
        when(filialService.listarFiliais()).thenReturn(List.of());

        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("Deve editar filial com sucesso")
    void deveEditarFilialComSucesso() throws Exception {
        String cnpj = "12345678000190";

        FilialRequestDTO requestAtualizacao = new FilialRequestDTO(
                cnpj,
                "Filial Centro Renovado",
                "Empresa Centro Renovado LTDA"
        );

        FilialResponseDTO responseAtualizado = new FilialResponseDTO(
                cnpj,
                "Filial Centro Renovado",
                "Empresa Centro Renovado LTDA"
        );

        when(filialService.editarFilial(eq(cnpj), any(FilialRequestDTO.class)))
                .thenReturn(responseAtualizado);

        mockMvc.perform(put(URL + "/{cnpj}", cnpj)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestAtualizacao)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cnpj").value(cnpj))
                .andExpect(jsonPath("$.nome").value("Filial Centro Renovado"))
                .andExpect(jsonPath("$.razaosocial").value("Empresa Centro Renovado LTDA"));
    }

    @Test
    @DisplayName("Deve retornar 404 NOT FOUND ao editar filial inexistente")
    void deveRetornar404QuandoFilialNaoEncontradaAoEditar() throws Exception {
        when(filialService.editarFilial(eq(CNPJ_INEXISTENTE), any(FilialRequestDTO.class)))
            .thenThrow(new ResourceNotFoundException("Filial não encontrada com CNPJ: " + CNPJ_INEXISTENTE));

        mockMvc.perform(put(URL + "/{cnpj}", CNPJ_INEXISTENTE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("Deve remover filial com sucesso e retornar 204 NO CONTENT")
    void deveRemoverFilialComSucesso() throws Exception {
        String cnpj = "12345678000190";

        doNothing().when(filialService).removerFilial(cnpj);

        mockMvc.perform(delete(URL + "/{cnpj}", cnpj))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar 404 NOT FOUND ao remover filial inexistente")
    void deveRetornar404QuandoFilialNaoEncontradaAoRemover() throws Exception {
        doThrow(new ResourceNotFoundException("Filial não encontrada"))
            .when(filialService).removerFilial(CNPJ_INEXISTENTE);

        mockMvc.perform(delete(URL + "/{cnpj}", CNPJ_INEXISTENTE))
                .andExpect(status().isNotFound());
    }


    
    @Test
    @DisplayName("Deve criar e depois buscar filial por CNPJ")
    void deveCriarEBuscarFilial() throws Exception {
        when(filialService.adicionarFilial(any(FilialRequestDTO.class)))
            .thenReturn(responseValido);
            
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isCreated());
        
        when(filialService.listarFiliais()).thenReturn(List.of(responseValido));
        
        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].cnpj").value(CNPJ_VALIDO));
    }
}