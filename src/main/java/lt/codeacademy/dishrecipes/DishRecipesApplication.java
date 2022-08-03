package lt.codeacademy.dishrecipes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:company.properties")
public class DishRecipesApplication {

	public static void main(String[] args) {
		SpringApplication.run(DishRecipesApplication.class, args);
	}

}
