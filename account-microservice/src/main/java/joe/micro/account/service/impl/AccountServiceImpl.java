package joe.micro.account.service.impl;

import java.util.ArrayList;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.client.RestTemplate;

import joe.micro.account.domain.Account;
import joe.micro.account.domain.AccountRepository;
import joe.micro.account.domain.Address;
import joe.micro.account.service.AccountService;
import joe.micro.account.service.AccountServiceException;
import joe.micro.account.service.WebCorrespondenceService;
import joe.micro.account.service.WebReferenceService;
import joe.micro.client.reference.StateDto;

public class AccountServiceImpl implements AccountService {

	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;

	protected String serviceUrl;

	@Autowired
	private AccountRepository repo;
	
	@Autowired
	protected WebReferenceService referenceService;

	@Autowired
	protected WebCorrespondenceService correspondenceService;

	protected final static org.slf4j.Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Override
	public Account createAccount(String firstName, String lastName, String email, Address billingAddress)
			throws AccountServiceException {
		log.info("In createAccount");
		ArrayList<String> fieldErrors = new ArrayList<String>();

		Boolean isValid = true;
		
		log.info("Validating address");
		Address address = null;
		try {
			address = validateBillingAddress(billingAddress);			
		} catch (IllegalArgumentException iae) {
			fieldErrors.add("Billing address is not valid.");
			isValid = false;
		}
		
		if (firstName.isEmpty()) {
			fieldErrors.add("First name is null or empty.");
			isValid = false;
		}

		if (lastName.isEmpty()) {
			fieldErrors.add("Last name is null or empty.");
			isValid = false;
		}

		if (email.isEmpty()) {
			fieldErrors.add("Email is null or empty.");
			isValid = false;
		}
		
		Account a = null;
		if (isValid) {
			if (repo.findByEmail(email) == null) {
				log.info("Creating a new account");
				a = repo.save(new Account(null, firstName, lastName, email, address));
			} else {				
				throw new AccountServiceException("Unable to create account. Email already exists.");
			}
		} else {
			StringBuffer strBuff = new StringBuffer("Unable to create the account. The following issues occurred: ");
			for (String msg : fieldErrors) {
				strBuff.append(msg + " ");
			}
			throw new AccountServiceException(strBuff.toString().trim());
		}

		Boolean emailSent = correspondenceService.sendWelcomeEmail(a.getEmail());
		log.info("Call to sendWelcomeEmail returned " + emailSent);
		
		return a;
	}

	@Override
	public Account getAccountByEmail(String email) throws AccountServiceException {		
		return repo.findByEmail(email);
	}

	protected Address validateBillingAddress(Address billingAddress) throws IllegalArgumentException {

		Boolean isValid = true;
		Address address = null;
		if (billingAddress != null) {
			address = new Address();
			if (billingAddress.getPrimaryAddressLine().isEmpty()) {
				isValid = false;
			} else {
				address.setPrimaryAddressLine(billingAddress.getPrimaryAddressLine());
			}

			address.setSecondaryAddressLine(billingAddress.getSecondaryAddressLine());
			
			if (billingAddress.getCityName().isEmpty()) {
				isValid = false;
			} else {
				address.setCityName(billingAddress.getCityName());
			}
			
			if (billingAddress.getZipCode().isEmpty()) {
				isValid = false;
			} else {
				address.setZipCode(billingAddress.getZipCode());
			}
			
			if (!billingAddress.getState().isEmpty()) {
				log.info("Looking up state from ReferenceService");
				StateDto stateDto = referenceService.lookupStateByCode(billingAddress.getState());			
				if (stateDto != null) {
					address.setState(stateDto.getCode());
				} else {
					isValid = false;
					log.warn("Unable to find state (" + billingAddress.getState() + ") via ReferenceService");
				}
			} else {
				isValid = false;
			}
		} else {
			isValid = false;
		}
		if (isValid) {
			// TODO: Add address lookup service
		} else {
			throw new IllegalArgumentException("Address is invalid");
		}
		
		return address;		
	}
}
