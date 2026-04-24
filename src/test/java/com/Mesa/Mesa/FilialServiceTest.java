package com.Mesa.Mesa;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.project.Mesa.Controller.dto.filial.FilialRequestDTO;
import com.project.Mesa.Mapper.impl.FilialMapperImpl;
import com.project.Mesa.Repository.FilialRepository;
import com.project.Mesa.Service.FilialService;
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
}
