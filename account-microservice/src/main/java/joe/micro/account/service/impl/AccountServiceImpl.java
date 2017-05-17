package joe.micro.account.service.impl;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.client.RestTemplate;

import joe.micro.account.domain.Account;
import joe.micro.account.domain.AccountRepository;
import joe.micro.account.domain.Address;
import joe.micro.account.service.AccountService;
import joe.micro.account.service.AccountServiceException;
import joe.micro.client.reference.StateDto;

public class AccountServiceImpl implements AccountService {

	@Autowired // NO LONGER auto-created by Spring Cloud (see below)
	@LoadBalanced
	protected RestTemplate restTemplate;

	protected String serviceUrl;

	@Autowired
	private AccountRepository repo;

	protected final static org.slf4j.Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Override
	public Account createAccount(String firstName, String lastName, String email, Address billingAddress)
			throws AccountServiceException {

		Account a = null;
		if (repo.findByEmail(email) == null) {
			a = repo.save(new Account(null, firstName, lastName, email, billingAddress));
			log.info("After save: " + repo.count());
		}

		return a;
	}

	@Override
	public Account getAccountByEmail(String email) throws AccountServiceException {
		return repo.findByEmail(email);
	}

//	protected StateDto lookupState(String serviceUrl, String stateCode) {
//
//		StateDto s = null;
//		
//		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl : "http://" + serviceUrl;		
//
//		s = restTemplate.getForObject(serviceUrl + "/api/getState/{stateCode}", StateDto.class, stateCode);
//		
//		return s;
//	}
}
