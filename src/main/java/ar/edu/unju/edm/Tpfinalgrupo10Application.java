package ar.edu.unju.edm;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Tpfinalgrupo10Application {

	public static void main(String[] args) {
		SpringApplication.run(Tpfinalgrupo10Application.class, args);
	
		
		Integer integer1 = Integer.valueOf(12),integer2=Integer.valueOf(12);
		
		int entero1=12;
		int entero2=12;
		
		if(integer1.equals(entero2)) {
			System.out.println("Integer equals int = true");
		}else {
			System.out.println("Integer equals int = false");
		}
		
		if(integer1==entero1) {
			System.out.println("Integer == int = true");
		}else {
			System.out.println("Integer == int = false");
		}
		
		if(entero2==entero1) {
			System.out.println("int == int = true");
		}else {
			System.out.println("int == int = false");
		}
	
		if(integer1.equals(integer2)) {
			System.out.println("Integer equals integer = true");
		}else {
			System.out.println("Integer equals integer = false");
		}
	
	}
	
	
	
	
	
		
}
