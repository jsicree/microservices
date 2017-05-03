package joe.micro.account.service.impl;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import joe.micro.account.controller.AccountServiceController;
import joe.micro.account.domain.Account;
import joe.micro.account.domain.AccountRepository;
import joe.micro.account.service.AccountService;

public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository repo;

	protected final static org.slf4j.Logger log = LoggerFactory
			.getLogger(AccountServiceImpl.class);
	
	@Override
	public Account getAccountByIdentifier(String identifier) {
		
		return repo.findByAccountIdentifier(identifier);
	}

	@Override
	public Account createAccount(String identifier) {
		
		Account a = null;
		if (repo.findByAccountIdentifier(identifier) == null) {
			a = repo.save(new Account(identifier));			
			log.info("After save: " + repo.count());
		}
		
		return a;
	}

}
