package com.project.Mesa.Controller;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import com.project.Mesa.Controller.Impl.CampanhaControllerImpl;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.project.Mesa.Controller.dto.campanha.CampanhaResponseDTO;
import com.project.Mesa.Service.CampanhaService;
import com.project.Mesa.Service.GoogleSheetsService;
import com.project.Mesa.Service.SecurityFilter;
import com.project.Mesa.Service.TokenService;

@WebMvcTest(CampanhaControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false)
public class CampanhaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CampanhaService campanhaService;

    @MockBean
    private GoogleSheetsService googleSheetsService;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private SecurityFilter securityFilter;

    private final String URL = "/campanha";

    private CampanhaResponseDTO responseValido;
    
    @BeforeEach
    void setup() {
    	
        responseValido =  new CampanhaResponseDTO(
                1L, "2024", "grupo", "123",
                "categoria", "cargo", "nome",
                "meta", "real", "realizado",
                "atingida", "metaPos", "realPos",
                "metaPosAtingida", "valorBat",
                "faixa", "premio", "taxa",
                "1", "sim", "pagina1"
            );
    }

    @Test
    @DisplayName("Deve listar campanhas por página com sucesso")
    void deveListarCampanhasPorPagina() throws Exception {

        when(campanhaService.ListarCampanhaPorPagina(anyString()))
                .thenReturn(List.of(responseValido));

        mockMvc.perform(get(URL + "/{pagina}", "pagina1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].pagina").value("pagina1"));
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não houver campanhas")
    void deveRetornarListaVazia() throws Exception {

        when(campanhaService.ListarCampanhaPorPagina(anyString()))
                .thenReturn(List.of());

        mockMvc.perform(get(URL + "/{pagina}", "pagina1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }


    @Test
    @DisplayName("Deve importar campanhas com sucesso")
    void deveImportarCampanhasComSucesso() throws Exception {

        when(googleSheetsService.loadDataFromGoogleSheets())
                .thenReturn(List.of(responseValido));

        mockMvc.perform(post(URL + "/importarCampanha"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @DisplayName("Deve retornar 500 quando erro ao importar campanhas")
    void deveRetornarErroAoImportarCampanhas() throws Exception {

        when(googleSheetsService.loadDataFromGoogleSheets())
                .thenThrow(new RuntimeException());

        mockMvc.perform(post(URL + "/importarCampanha"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Deve remover campanha com sucesso")
    void deveRemoverCampanhaComSucesso() throws Exception {

        doNothing().when(campanhaService).removerCampanha(eq(1L));

        mockMvc.perform(delete(URL + "/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
