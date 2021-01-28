package xyz.andrewkboyd.mitresiphon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Entry point for the spring boot application
 */
@SpringBootApplication
public class MitreSiphonApplication {

	/**
	 * Launch the spring application
	 * @param args passed to SpringApplication.run
	 */
	public static void main(String[] args) {
		try {
			SpringApplication.run(MitreSiphonApplication.class, args);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
