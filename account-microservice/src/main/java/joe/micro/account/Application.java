package joe.micro.account;

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

import joe.micro.account.service.AccountService;
import joe.micro.account.service.WebCorrespondenceService;
import joe.micro.account.service.WebReferenceService;
import joe.micro.account.service.impl.AccountServiceImpl;
import joe.micro.account.service.impl.WebCorrespondenceServiceImpl;
import joe.micro.account.service.impl.WebReferenceServiceImpl;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableDiscoveryClient
@PropertySource({"classpath:application.properties"})
public class Application {

	private static final String PROPERTY_NAME_REFERENCE_SERVICE_URL = "ref.service.url";
	private static final String PROPERTY_NAME_CORRESPONDENCE_SERVICE_URL = "correspondence.service.url";

	@Resource
	private Environment environment;	
	
	@Bean
    AccountService getAccountService() {
    	return new AccountServiceImpl();
    }

	@Bean
    WebReferenceService getWebReferenceService() {
    	return new WebReferenceServiceImpl(environment.getRequiredProperty(PROPERTY_NAME_REFERENCE_SERVICE_URL));
    }

	@Bean
    WebCorrespondenceService getWebCorrespondenceService() {
    	return new WebCorrespondenceServiceImpl(environment.getRequiredProperty(PROPERTY_NAME_CORRESPONDENCE_SERVICE_URL));
    }
	
	@LoadBalanced
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}	
    public static void main(String[] args) throws Exception {
    	System.setProperty("spring.config.name", "accounts-server");
        SpringApplication.run(Application.class, args);
    }
	    	
}
