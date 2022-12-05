package sakicorp;

import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SakicorpApplication {

	public static void main(String[] args) {
		LocalDateTime datetime = LocalDateTime.now();
		System.out.println(datetime);
		SpringApplication.run(SakicorpApplication.class, args);
	}

}
