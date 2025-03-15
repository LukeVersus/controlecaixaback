package com.omega.controlecaixa;

import com.omega.controlecaixa.domain.model.Usuario;
import com.omega.controlecaixa.repositories.UsuarioRepository;
import lombok.Data;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Data
@SpringBootApplication
public class ControlecaixaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControlecaixaApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			iniciarUsuario(usuarioRepository, passwordEncoder);
		};
	}

	public void iniciarUsuario(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
		Usuario usuario = repository.findFirst();

		if (usuario == null) {
			usuario = new Usuario();
			usuario.setNome("Administrador");
			usuario.setUsername("admin");
			usuario.setPassword(passwordEncoder.encode("@dm1n"));
			usuario.setEmail("admin@omega.com.br");
			usuario.setAtivo(true);
			repository.save(usuario);
		}
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
