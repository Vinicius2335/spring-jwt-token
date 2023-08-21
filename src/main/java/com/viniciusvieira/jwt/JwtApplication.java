package com.viniciusvieira.jwt;

import com.viniciusvieira.jwt.api.representation.model.request.RegisterRequest;
import com.viniciusvieira.jwt.domain.model.user.RoleTypes;
import com.viniciusvieira.jwt.domain.service.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtApplication.class, args);
	}


	@Bean
	public CommandLineRunner commandLineRunner(AuthenticationService authenticationService){
		return args -> {
			RegisterRequest admin = RegisterRequest.builder()
					.firstName("admin")
					.lastName("admin")
					.email("admin@email.com")
					.password("admin")
					.role(RoleTypes.ADMIN)
					.build();

			RegisterRequest manager = RegisterRequest.builder()
					.firstName("manager")
					.lastName("manager")
					.email("manager@email.com")
					.password("manager")
					.role(RoleTypes.MANAGER)
					.build();

			System.out.println("Admin token: " + authenticationService.register(admin).getToken());
			System.out.println("Manager token: " + authenticationService.register(manager).getToken());
		};
	}
}
