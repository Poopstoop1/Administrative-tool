package com.project.Mesa.Controller.Impl;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.Mesa.Controller.LoginController;
import com.project.Mesa.Controller.dto.auth.LoginRequestDTO;
import com.project.Mesa.Controller.dto.auth.LoginResponseDTO;
import com.project.Mesa.Service.LoginService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
@CrossOrigin("*")
public class LoginControllerImpl implements LoginController {
	
	private final LoginService loginService;
	
	@PostMapping
	public ResponseEntity<LoginResponseDTO> login(@RequestBody @Validated LoginRequestDTO dto){
		return ResponseEntity.ok(loginService.login(dto));
	}

}
