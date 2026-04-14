package com.project.Mesa.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.project.Mesa.Service.SecurityDatabaseService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig implements WebMvcConfigurer {

	
	private final SecurityDatabaseService securityDatabaseService;

	public WebSecurityConfig(SecurityDatabaseService securityDatabaseService) {
		this.securityDatabaseService = securityDatabaseService;
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

	    provider.setUserDetailsService(securityDatabaseService);
	    provider.setPasswordEncoder(passwordEncoder());

	    return provider;
	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authenticationProvider(authenticationProvider())
			.authorizeHttpRequests((authz) -> authz
				// Rotas acessíveis apenas pelo admin
				.requestMatchers("/login.css","/img/**","/js/**").permitAll()
				.requestMatchers("/salvarusuarios","/listarusuarios","/editarusuarios/{idusuario}",
						"/filial","/upload","/usuarios","/update-sheets").hasRole("MANAGER")
								// Qualquer outra rota requer autenticação
				.anyRequest().authenticated())
				// Usa o form da pagina tela.html do Spring Security
				.formLogin((form) -> form
						.loginPage("/login")
						.failureUrl("/login?error=true")
						.defaultSuccessUrl("/", true) 
						.permitAll())
				 .logout(logout -> logout.logoutUrl("/logout")
			             .permitAll());  

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return securityDatabaseService;
	}
	
	/*
	 * @Bean public InMemoryUserDetailsManager userDetailsService() {
	 * 
	 * @SuppressWarnings("deprecation") UserDetails user =
	 * User.withDefaultPasswordEncoder().username("user").password("password").roles
	 * ("USER") .build();
	 * 
	 * @SuppressWarnings("deprecation") UserDetails user2 =
	 * User.withDefaultPasswordEncoder().username("user2").password("1234567").roles
	 * ("USER") .build();
	 * 
	 * @SuppressWarnings("deprecation") UserDetails admin =
	 * User.withDefaultPasswordEncoder().username("admin").password("12345678").
	 * roles("MANAGER") .build(); return new InMemoryUserDetailsManager(user, user2,
	 * admin); }
	 */

}
