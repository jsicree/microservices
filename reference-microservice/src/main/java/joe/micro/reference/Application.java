package joe.micro.reference;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableDiscoveryClient
@PropertySource({"classpath:application.properties"})
public class Application {

//	@Bean
//    ReferenceDataService getReferenceDataService() {
//    	return new ReferenceDataServiceImpl();
//    }

    public static void main(String[] args) throws Exception {
    	System.setProperty("spring.config.name", "reference-server");
        SpringApplication.run(Application.class, args);
    }
	    	
}
