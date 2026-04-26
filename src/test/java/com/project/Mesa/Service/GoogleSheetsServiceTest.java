package com.project.Mesa.Service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.project.Mesa.Config.GoogleSheetsConfig;
import com.project.Mesa.Controller.dto.campanha.CampanhaResponseDTO;
import com.project.Mesa.Mapper.impl.CampanhaMapperImpl;
import com.project.Mesa.Mapper.integration.impl.CampanhaSheetMapperImpl;
import com.project.Mesa.Model.Campanha;
import com.project.Mesa.Model.Filial;
import com.project.Mesa.Repository.CampanhaRepository;
import com.project.Mesa.Validation.validators.campanha.CampanhaExistenteValidator;
import com.project.Mesa.Validation.validators.campanha.CampanhaValidator;

@ExtendWith(MockitoExtension.class)
public class GoogleSheetsServiceTest {

	  	@InjectMocks
	    private GoogleSheetsService googleSheetsService;

	    @Mock
	    private CampanhaRepository campanhaRepository;

	    @Mock
	    private GoogleSheetsConfig googleSheetsConfig;

	    @Mock
	    private CampanhaSheetMapperImpl campanhaSheetMapper;

	    @Mock
	    private CampanhaMapperImpl campanhaMapper;

	    @Mock
	    private CampanhaExistenteValidator campanhaExistenteValidator;

	    @Mock
	    private CampanhaValidator campanhaValidator;

	    private Sheets sheets;
	    private Sheets.Spreadsheets spreadsheets;
	    private Sheets.Spreadsheets.Values values;
	    private Sheets.Spreadsheets.Values.Get get;

	    @BeforeEach
	    void setup() throws Exception {

	        sheets = mock(Sheets.class);
	        spreadsheets = mock(Sheets.Spreadsheets.class);
	        values = mock(Sheets.Spreadsheets.Values.class);
	        get = mock(Sheets.Spreadsheets.Values.Get.class);

	        when(googleSheetsConfig.getSheetsService()).thenReturn(sheets);
	        when(sheets.spreadsheets()).thenReturn(spreadsheets);
	        when(spreadsheets.values()).thenReturn(values);
	        when(values.get(anyString(), anyString())).thenReturn(get);

	        ValueRange valueRange = new ValueRange();
	        valueRange.setValues(List.of(
	                List.of("header1", "header2"),
	                List.of("valor1", "valor2")
	        ));

	        when(get.execute()).thenReturn(valueRange);
	        when(googleSheetsConfig.getSpreadSheetID()).thenReturn("spreadsheet-id");
	    }
	    
	    
	    @Test
	    @DisplayName("Deve salvar campanhas válidas")
	    void deveSalvarCampanhasValidas() throws Exception {

	        Campanha campanha = new Campanha();
	        campanha.setEmpresa(new Filial());

	        when(campanhaSheetMapper.toCampanha(any(), anyString()))
	                .thenReturn(campanha);

	        doNothing().when(campanhaValidator)
	                .validaCamposdeCampanha(campanha);

	        when(campanhaExistenteValidator.buscarCampanhaExistente(campanha))
	                .thenReturn(false);

	        when(campanhaRepository.save(campanha))
	                .thenReturn(campanha);

	        when(campanhaMapper.campanhaResponseDTO(campanha))
	                .thenReturn(mock(CampanhaResponseDTO.class));

	        var result = googleSheetsService.loadDataFromGoogleSheets();

	        assertNotNull(result);
	        verify(campanhaRepository, atLeastOnce()).save(campanha);
	    }

	    @Test
	    @DisplayName("Não deve salvar campanha duplicada")
	    void naoDeveSalvarCampanhaDuplicada() throws Exception {

	        Campanha campanha = new Campanha();
	        campanha.setEmpresa(new Filial());

	        when(campanhaSheetMapper.toCampanha(any(), anyString()))
	                .thenReturn(campanha);

	        when(campanhaExistenteValidator.buscarCampanhaExistente(campanha))
	                .thenReturn(true);

	        googleSheetsService.loadDataFromGoogleSheets();

	        verify(campanhaRepository, never()).save(any());
	    }

	    @Test
	    @DisplayName("Não deve salvar campanha sem empresa")
	    void naoDeveSalvarSemEmpresa() throws Exception {

	        Campanha campanha = new Campanha();
	        campanha.setEmpresa(null);

	        when(campanhaSheetMapper.toCampanha(any(), anyString()))
	                .thenReturn(campanha);

	        googleSheetsService.loadDataFromGoogleSheets();

	        verify(campanhaRepository, never()).save(any());
	    }

}
