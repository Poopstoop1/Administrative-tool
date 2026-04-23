package com.project.Mesa.Mapper.integration;

import java.util.List;

import com.project.Mesa.Model.Campanha;

public interface CampanhaSheetMapper {
	Campanha toCampanha(List<Object> row, String sheetName);
}
