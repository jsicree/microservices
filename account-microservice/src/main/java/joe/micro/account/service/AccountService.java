package joe.micro.account.service;

import joe.micro.account.domain.Account;
import joe.micro.account.domain.Address;

public interface AccountService {

	public Account createAccount(String firstName, String lastName, String email, Address billingAddress) throws AccountServiceException;

	public Account getAccountByEmail(String email) throws AccountServiceException;
	
}
