package edu.miu.cs.cs489appsd.hotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
//		DataSourceAutoConfiguration.class,
//		HibernateJpaAutoConfiguration.class,
//		JpaRepositoriesAutoConfiguration.class
})
public class Cs489appsdHotelbookingApplication {

	public static void main(String[] args) {
		DotenvLoaderWithWatcher.loadAndWatch(".env"); // Environment variables load & auto-reload on changes
		SpringApplication.run(Cs489appsdHotelbookingApplication.class, args);
	}

}
