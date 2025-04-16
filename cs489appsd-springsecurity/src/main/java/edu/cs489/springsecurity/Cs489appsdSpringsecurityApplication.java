package edu.cs489.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Cs489appsdSpringsecurityApplication {

	public static void main(String[] args) {
		DotenvLoaderWithWatcher.loadAndWatch(".env"); // Environment variables load & auto-reload on changes
		SpringApplication app = new SpringApplication(Cs489appsdSpringsecurityApplication.class);
		app.setDefaultProperties(System.getProperties()); // <- Tell Spring to use the updated system properties
		app.run(args);
	}

}
