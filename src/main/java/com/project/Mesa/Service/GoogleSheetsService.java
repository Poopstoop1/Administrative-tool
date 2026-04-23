package com.project.Mesa.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.project.Mesa.Config.GoogleSheetsConfig;
import com.project.Mesa.Controller.dto.campanha.CampanhaResponseDTO;
import com.project.Mesa.Mapper.impl.CampanhaMapperImpl;
import com.project.Mesa.Mapper.integration.impl.CampanhaSheetMapperImpl;
import com.project.Mesa.Model.Campanha;
import com.project.Mesa.Repository.CampanhaRepository;
import com.project.Mesa.Validation.validators.campanha.CampanhaExistenteValidator;
import com.project.Mesa.Validation.validators.campanha.CampanhaValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoogleSheetsService {

	private final CampanhaRepository campanhaRepository;
	private final GoogleSheetsConfig googleSheetsConfig;
	private final CampanhaSheetMapperImpl campanhaSheetMapper;
	private final CampanhaMapperImpl campanhaMapper;
	private final CampanhaExistenteValidator campanhaExistenteValidator;
	private final CampanhaValidator campanhaValidator;

	@Transactional
	public List<CampanhaResponseDTO> loadDataFromGoogleSheets() throws IOException, GeneralSecurityException {
		Sheets sheetsService = googleSheetsConfig.getSheetsService();

		List<String> sheetNames = List.of("Comida Premiada", "Melhores Empresas", "Come Melhor", "Destaque", "Destaque Corporativo");

		List<Campanha> campanhasNovas = new ArrayList<>();

		for (String sheetName : sheetNames) {
			List<List<Object>> values = fetchData(sheetsService, sheetName);
			if (values == null)
				continue;

			for (int i = 1; i < values.size(); i++) {
				Campanha campanha = campanhaSheetMapper.toCampanha(values.get(i), sheetName);

				campanhaValidator.validaCamposdeCampanha(campanha);
				if (campanha.getEmpresa() != null && !campanhaExistenteValidator.buscarCampanhaExistente(campanha)) {
					campanhasNovas.add(campanhaRepository.save(campanha));
				}
			}
		}
		return campanhasNovas.stream().map(campanhaMapper::campanhaResponseDTO).toList();
	}

	private List<List<Object>> fetchData(Sheets service, String sheetName) throws IOException {
		String range = sheetName + "!A:O";
		ValueRange response = service.spreadsheets().values().get(googleSheetsConfig.getSpreadSheetID(), range)
				.execute();
		return response.getValues();
	}
	
}
