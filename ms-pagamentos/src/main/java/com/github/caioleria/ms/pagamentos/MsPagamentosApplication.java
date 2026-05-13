package com.github.caioleria.ms.pagamentos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDiscoveryClient
public class MsPagamentosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsPagamentosApplication.class, args);
	}

}
