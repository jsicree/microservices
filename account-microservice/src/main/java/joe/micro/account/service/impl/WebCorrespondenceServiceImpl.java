package joe.micro.account.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import joe.micro.account.service.WebCorrespondenceService;
import joe.micro.account.service.WebReferenceService;
import joe.micro.client.correspondence.EmailRequest;
import joe.micro.client.correspondence.EmailResponse;
import joe.micro.client.reference.RequestStatus;
import joe.micro.client.reference.StateDto;
import joe.micro.client.reference.StateRequest;
import joe.micro.client.reference.StateResponse;

public class WebCorrespondenceServiceImpl implements WebCorrespondenceService {

	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;

	protected String serviceUrl;

	protected Logger logger = Logger.getLogger(WebReferenceService.class
			.getName());

	public WebCorrespondenceServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	public WebCorrespondenceServiceImpl(String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl
				: "http://" + serviceUrl;
	}

	@Override
	public Boolean sendWelcomeEmail(String email) {
		logger.info("Calling sendWelcomeEmail with email " + email);

		Boolean emailSent = false;
		try {
			EmailRequest request = new EmailRequest();
			request.setEmail(email);

			EmailResponse response = restTemplate.postForObject(serviceUrl
					+ "/api/correspondence/sendWelcomeEmail", request, EmailResponse.class);

			if (response.getStatus() == joe.micro.client.correspondence.RequestStatus.OK) {
				logger.info("SendWelcomeEmail successful");
				emailSent = true;
			}
		} catch (HttpClientErrorException e) { // 404
			// Nothing found
		}

		return emailSent;
	}
	
}
