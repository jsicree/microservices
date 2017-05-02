package joe.micro.account.service.impl;

import joe.micro.account.domain.Account;
import joe.micro.account.service.AccountService;

public class AccountServiceImpl implements AccountService {

	@Override
	public Account getAccountByIdentifier(String identifier) {
		
		return new Account(Long.valueOf(101),identifier);
	}

}
