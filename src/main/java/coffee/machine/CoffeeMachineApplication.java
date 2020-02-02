package coffee.machine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class CoffeeMachineApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(CoffeeMachineApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", 8086));
		app.run(args);
	}

}
