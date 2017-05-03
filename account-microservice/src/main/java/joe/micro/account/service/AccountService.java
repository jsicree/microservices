package joe.micro.account.service;

import joe.micro.account.domain.Account;

public interface AccountService {

	public Account getAccountByIdentifier(final String identifier);

	public Account createAccount(final String identifier);

}
