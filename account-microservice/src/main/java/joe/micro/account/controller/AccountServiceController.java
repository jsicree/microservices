package joe.micro.account.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import joe.micro.account.domain.Account;
import joe.micro.account.domain.Address;
import joe.micro.account.service.AccountService;
import joe.micro.account.service.AccountServiceException;
import joe.micro.client.account.AccountDto;
import joe.micro.client.account.AccountRequest;
import joe.micro.client.account.AccountResponse;
import joe.micro.client.account.AddressDto;
import joe.micro.client.account.CreateAccountRequest;
import joe.micro.client.account.CreateAccountResponse;
import joe.micro.client.account.RequestStatus;

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
		
		log.info("Looking for account with email " + request.getEmail());
		Account account;
		try {
			account = accountService.getAccountByEmail(request.getEmail());
			AccountDto dto = null;
			if (account != null) {
	 			dto = new AccountDto();
				dto.setId(account.getId());
				dto.setFirstName(account.getFirstName());				
				dto.setLastName(account.getLastName());
				dto.setEmail(account.getEmail());
				if (account.getBillingAddress() != null) {
					AddressDto addr = new AddressDto();
					addr.setPrimaryAddressLine(account.getBillingAddress().getPrimaryAddressLine());
					addr.setSecondaryAddressLine(account.getBillingAddress().getSecondaryAddressLine());
					addr.setCity(account.getBillingAddress().getCityName());
					addr.setState(account.getBillingAddress().getState());
					addr.setZipCode(account.getBillingAddress().getZipCode());
					dto.setBillingAddress(addr);
				}				
			}
			response.setAccount(dto);
			response.setStatus(RequestStatus.OK);
		} catch (AccountServiceException e) {
			response.setStatus(RequestStatus.ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = "/createAccount", method = RequestMethod.POST, produces = "application/json")
	public CreateAccountResponse createAccount(@RequestBody CreateAccountRequest request) {
		CreateAccountResponse response = new CreateAccountResponse();
		
		AccountDto dto = null;
		try {
			if (accountService.getAccountByEmail(request.getEmail()) == null) {
				log.info("Creating account with email = " + request.getEmail());

				Address billingAddress = null;
				if (request.getBillingAddress() != null) {
					billingAddress = new Address();
					billingAddress.setPrimaryAddressLine(request.getBillingAddress().getPrimaryAddressLine());
					billingAddress.setSecondaryAddressLine(request.getBillingAddress().getSecondaryAddressLine());
					billingAddress.setCityName(request.getBillingAddress().getCity());
					billingAddress.setState(request.getBillingAddress().getState());
					billingAddress.setZipCode(request.getBillingAddress().getZipCode());
				}
				try {
					Account account = accountService.createAccount(request.getFirstName(), request.getLastName(), request.getEmail(), billingAddress);					
					if (account != null) {
			 			dto = new AccountDto();
						dto.setId(account.getId());
						dto.setFirstName(account.getFirstName());				
						dto.setLastName(account.getLastName());
						dto.setEmail(account.getEmail());
						if (account.getBillingAddress() != null) {
							AddressDto addr = new AddressDto();
							addr.setPrimaryAddressLine(account.getBillingAddress().getPrimaryAddressLine());
							addr.setSecondaryAddressLine(account.getBillingAddress().getSecondaryAddressLine());
							addr.setCity(account.getBillingAddress().getCityName());
							addr.setState(account.getBillingAddress().getState());
							addr.setZipCode(account.getBillingAddress().getZipCode());
							dto.setBillingAddress(addr);
						}
					}
					response.setStatus(RequestStatus.OK);
					response.setAccount(dto);					
				} catch (AccountServiceException e) {
					log.warn("Account creation failed: " + e.getMessage());
					response.setStatus(RequestStatus.ERROR);
					response.setMessage("Account creation failed: " + e.getMessage());				
				}
			} else {
				log.warn("Account with email " + request.getEmail() + " already exists.");
				response.setStatus(RequestStatus.ERROR);
				response.setMessage("Account with email " + request.getEmail() + " already exists.");				
			}
		} catch (AccountServiceException e) {
			response.setStatus(RequestStatus.ERROR);
			response.setMessage(e.getMessage());
		}
		return response;
	}
	
}
