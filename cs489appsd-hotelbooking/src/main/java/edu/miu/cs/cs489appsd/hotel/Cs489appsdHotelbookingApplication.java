package edu.miu.cs.cs489appsd.hotel;

import edu.miu.cs.cs489appsd.hotel.dtos.NotificationDto;
import edu.miu.cs.cs489appsd.hotel.enums.NotificationType;
import edu.miu.cs.cs489appsd.hotel.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(exclude = {
//		DataSourceAutoConfiguration.class,
//		HibernateJpaAutoConfiguration.class,
//		JpaRepositoriesAutoConfiguration.class
})
@EnableAsync
public class Cs489appsdHotelbookingApplication implements CommandLineRunner {

	@Autowired
	private NotificationService notificationService;


	public static void main(String[] args) {
		DotenvLoaderWithWatcher.loadAndWatch(".env"); // Environment variables load & auto-reload on changes
		SpringApplication app = new SpringApplication(Cs489appsdHotelbookingApplication.class);
		app.setDefaultProperties(System.getProperties()); // <- Tell Spring to use the updated system properties
		app.run(args);
		//System.out.println("EMAIL_USERNAME: " + System.getProperty("EMAIL_USERNAME"));
	}

	@Override
	public void run(String... args) throws Exception {
		NotificationDto notificationDto = NotificationDto.builder()
				.type(NotificationType.EMAIL)
				.recipient("dunkygeoffrey39@gmail.com")
				.body("I am testing this from a command line 🙂")
				.subject("Testing Email Sending")
				.build();

		notificationService.sendEmail(notificationDto);
	}
}
