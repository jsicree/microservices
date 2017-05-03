package joe.micro.account.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import joe.micro.account.domain.Account;
import joe.micro.account.service.AccountService;
import joe.micro.client.account.AccountDto;
import joe.micro.client.account.AccountRequest;
import joe.micro.client.account.AccountResponse;

@RestController
@RequestMapping("api/account")
public class AccountServiceController {

	@Autowired
	private AccountService accountService;

	protected final static org.slf4j.Logger log = LoggerFactory
			.getLogger(AccountServiceController.class);

	@RequestMapping(value = "/getAccount", method = RequestMethod.POST, produces = "application/json")
	public AccountResponse getAccount(@RequestBody AccountRequest request) {
		AccountResponse response = new AccountResponse();
		
		log.warn("accountId = " + request.getAccountIdentifier());
		Account account = accountService.getAccountByIdentifier(request.getAccountIdentifier());
		AccountDto dto = null;
		if (account != null) {
 			dto = new AccountDto();
			dto.setId(account.getId());
			dto.setAccountIdentifier(account.getAccountIdentifier());				
		}
		response.setAccount(dto);
		return response;
	}

	@RequestMapping(value = "/createAccount", method = RequestMethod.POST, produces = "application/json")
	public AccountResponse createAccount(@RequestBody AccountRequest request) {
		AccountResponse response = new AccountResponse();
		
		log.info("accountId = " + request.getAccountIdentifier());
		AccountDto dto = null;
		if (accountService.getAccountByIdentifier(request.getAccountIdentifier()) == null) {
			Account account = accountService.createAccount(request.getAccountIdentifier());
			if (account != null) {
	 			dto = new AccountDto();
				dto.setId(account.getId());
				dto.setAccountIdentifier(account.getAccountIdentifier());				
			}
		} else {
			log.warn("Account with accountIdentifier " + request.getAccountIdentifier() + " already exists.");
		}
		response.setAccount(dto);
		return response;
	}
	
}
