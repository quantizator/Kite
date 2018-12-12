package ru.lenobl.egov.kite.KiteExperimentsConfigServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class KiteExperimentsConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KiteExperimentsConfigServerApplication.class, args);
	}
}
