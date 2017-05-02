package joe.micro.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import joe.micro.account.service.AccountService;
import joe.micro.account.service.impl.AccountServiceImpl;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {

	@Bean
    AccountService getAccountService() {
    	return new AccountServiceImpl();
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
	    	
}
