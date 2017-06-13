package joe.micro.correspondence;

import javax.annotation.Resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableDiscoveryClient
@PropertySource({"classpath:application.properties"})
public class Application {

	private static final String PROPERTY_NAME_REFERENCE_SERVICE_URL = "ref.service.url";

	@Resource
	private Environment environment;	
	
//	@Bean
//    AccountService getAccountService() {
//    	return new AccountServiceImpl();
//    }

//	@Bean
//    WebReferenceService getWebReferenceService() {
//    	return new WebReferenceServiceImpl(environment.getRequiredProperty(PROPERTY_NAME_REFERENCE_SERVICE_URL));
//    }

	@LoadBalanced
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}	

	public static void main(String[] args) throws Exception {
    	System.setProperty("spring.config.name", "correspondence-server");
        SpringApplication.run(Application.class, args);
    }
	    	
}
