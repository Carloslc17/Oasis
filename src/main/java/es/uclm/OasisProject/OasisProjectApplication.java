package es.uclm.OasisProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class OasisProjectApplication extends ServletInitializer{
	

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(OasisProjectApplication.class);
    }


	public static void main(String[] args) {
		SpringApplication.run(OasisProjectApplication.class, args);
	}

}