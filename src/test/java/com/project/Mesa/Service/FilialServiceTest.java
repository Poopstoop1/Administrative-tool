package com.project.Mesa.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.project.Mesa.Controller.dto.filial.FilialRequestDTO;
import com.project.Mesa.Controller.dto.filial.FilialResponseDTO;
import com.project.Mesa.Mapper.impl.FilialMapperImpl;
import com.project.Mesa.Model.Filial;
import com.project.Mesa.Repository.FilialRepository;
import com.project.Mesa.Service.exception.ResourceNotFoundException;
import com.project.Mesa.Validation.validators.filial.FilialValidator;

@ExtendWith(MockitoExtension.class)
public class FilialServiceTest {

	@InjectMocks
	private FilialService filialService;

	@Mock
	private FilialRepository filialRepository;
	@Mock
	private FilialMapperImpl filialMapper;
	@Mock
	private FilialValidator filialValidator;
	
	
	private FilialRequestDTO request;

	@BeforeEach
	void setup() {
		request = new FilialRequestDTO("98.765.432/0001-15", "Empresa X", "Razão X");
	}
	
	@Test
	@DisplayName("Deve lançar exceção quando o request da filial for nulo")
	void deveLancarExcecaoQuandoRequestForNulo() {

		doThrow(new RuntimeException("Filial nula")).when(filialValidator).validarCamposDeFilial(null);

		assertThrows(RuntimeException.class, () -> filialService.adicionarFilial(null));

		verify(filialValidator).validarCamposDeFilial(null);
		verifyNoInteractions(filialMapper);
		verifyNoInteractions(filialRepository);
	}


	@Test
	@DisplayName("Deve lançar exceção quando a validação de campos da filial falhar")
	void deveLancarExcecaoQuandoValidacaoFalhar() {

		request = new FilialRequestDTO("98.765.432/0001-15", "Empresa X", "Razão X");

		doThrow(new RuntimeException("Campos inválidos")).when(filialValidator).validarCamposDeFilial(request);

		assertThrows(RuntimeException.class, () -> filialService.adicionarFilial(request));

		verify(filialValidator).validarCamposDeFilial(request);
		verifyNoInteractions(filialMapper);
		verifyNoInteractions(filialRepository);
	}
	
	@Test
	@DisplayName("Deve adicionar filial com sucesso")
	void deveAdicionarFilialComSucesso() {

	    var filial = new Filial();
	    var response = new FilialResponseDTO("98.765.432/0001-15", "Empresa X", "Razão X");

	    doNothing().when(filialValidator).validarCamposDeFilial(request);
	    when(filialMapper.toFilial(request)).thenReturn(filial);
	    when(filialRepository.save(filial)).thenReturn(filial);
	    when(filialMapper.toFilialResponseDTO(filial)).thenReturn(response);

	    FilialResponseDTO result = filialService.adicionarFilial(request);

	    assertNotNull(result);
	    verify(filialRepository).save(filial);
	}
	
	@Test
	@DisplayName("Deve retornar filial quando existir")
	void deveListarFilialComSucesso() {

	    var filial = new Filial();
	    var response = new FilialResponseDTO("cnpj", "Empresa", "Razão");

	    when(filialRepository.findById("cnpj"))
	        .thenReturn(Optional.of(filial));

	    when(filialMapper.toFilialResponseDTO(filial))
	        .thenReturn(response);

	    FilialResponseDTO result = filialService.listarFilial("cnpj");

	    assertNotNull(result);
	}
	
	@Test
	@DisplayName("Deve lançar exceção ao buscar filial inexistente")
	void deveLancarException_quandoFilialNaoExiste() {

	    when(filialRepository.findById("cnpj"))
	        .thenReturn(Optional.empty());

	    assertThrows(ResourceNotFoundException.class, () -> {
	        filialService.listarFilial("cnpj");
	    });
	}
	
	@Test
	@DisplayName("Deve listar todas as filiais")
	void deveListarFiliais() {

	    var filial = new Filial();
	    var response = new FilialResponseDTO("cnpj", "Empresa", "Razão");

	    when(filialRepository.findAll())
	        .thenReturn(List.of(filial));

	    when(filialMapper.toFilialResponseDTO(filial))
	        .thenReturn(response);

	    var result = filialService.listarFiliais();

	    assertEquals(1, result.size());
	}
	
	@Test
	@DisplayName("Deve remover filial com sucesso")
	void deveRemoverFilial() {

	    var filial = new Filial();

	    when(filialRepository.findById("cnpj"))
	        .thenReturn(Optional.of(filial));

	    filialService.removerFilial("cnpj");

	    verify(filialRepository).delete(filial);
	}
	
	@Test
	@DisplayName("Deve lançar exceção ao remover filial inexistente")
	void deveLancarException_aoRemoverFilialInexistente() {

	    when(filialRepository.findById("cnpj"))
	        .thenReturn(Optional.empty());

	    assertThrows(ResourceNotFoundException.class, () -> {
	        filialService.removerFilial("cnpj");
	    });

	    verify(filialRepository, never()).delete(any());
	}
	
	@Test
	@DisplayName("Deve editar filial com sucesso")
	void deveEditarFilial() {

	    var filial = new Filial();
	    var response = new FilialResponseDTO("cnpj", "Empresa", "Razão");

	    when(filialRepository.findById("cnpj"))
	        .thenReturn(Optional.of(filial));

	    doNothing().when(filialValidator)
	        .validarDadosEditado(request, "cnpj");

	    doNothing().when(filialMapper)
	        .atualizarCampos(filial, request);

	    when(filialRepository.save(filial))
	        .thenReturn(filial);

	    when(filialMapper.toFilialResponseDTO(filial))
	        .thenReturn(response);

	    FilialResponseDTO result = filialService.editarFilial("cnpj", request);

	    assertNotNull(result);
	}
	
	@Test
	@DisplayName("Deve lançar exceção ao editar filial inexistente")
	void deveLancarException_aoEditarFilialInexistente() {

	    when(filialRepository.findById("cnpj"))
	        .thenReturn(Optional.empty());

	    assertThrows(ResourceNotFoundException.class, () -> {
	        filialService.editarFilial("cnpj", request);
	    });
	}
}
