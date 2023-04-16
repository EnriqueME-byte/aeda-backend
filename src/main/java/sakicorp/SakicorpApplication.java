
package sakicorp;
import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SakicorpApplication {

	public static String sinNombre = "HOLA";
	public static void main(String[] args) {
		LocalDateTime datetime = LocalDateTime.now();
		System.out.println(datetime);
		
		sinNombre = "PERRO";
		System.out.println(sinNombre);
		
		long k = 5L;
		int j = 6;

		
		Long c = new Long(2);
		
		switch(j) {
			case 2:
				System.out.println("hola");
			case 2*3:
				System.out.println("dos");
			default:
				System.out.println("default");
				break;
 				
		}
		if(k == j) {
			System.out.println("iguales");
		}
		int i = 1;
		do {
			i++;
			System.out.println("hola"+i);
		
		}while(i < 1);
		
		try {
			try {
				
			}catch(Exception e) {
				
			}
			finally {
				
			}
		}catch(Exception e) {
			
		}
		finally{
			
		}
		
		
		
		SpringApplication.run(SakicorpApplication.class, args);
	}

}
