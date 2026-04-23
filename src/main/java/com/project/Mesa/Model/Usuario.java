package com.project.Mesa.Model;
import java.util.Collection;
import java.util.Collections;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Usuario implements UserDetails {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @SequenceGenerator(name = "users_seq", sequenceName = "users_seq", allocationSize = 1)
	private Long id;

	@Column(unique = true)
	private String nome;
	private String login;
	private String password;
	private String cargo;
	@ManyToOne
	@JoinColumn(name = "cnpj_filial", referencedColumnName = "cnpj")
	private Filial empresa;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    String role = cargo.equalsIgnoreCase("Administrador") || cargo.equalsIgnoreCase("Marketing") 
	                  ? "ROLE_MANAGER" 
	                  : "ROLE_USER";
	    return Collections.singletonList(new SimpleGrantedAuthority(role));
	}
	

	
	@Override
	public String getUsername() {
		return login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
