package joe.micro.account.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import joe.micro.account.service.WebReferenceService;
import joe.micro.client.reference.StateDto;
import joe.micro.client.reference.StateRequest;
import joe.micro.client.reference.StateResponse;

public class WebReferenceServiceImpl implements WebReferenceService {

	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;

	protected String serviceUrl;

	protected Logger logger = Logger.getLogger(WebReferenceService.class
			.getName());

	public WebReferenceServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	public WebReferenceServiceImpl(String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl
				: "http://" + serviceUrl;
	}

	@Override
	public StateDto lookupStateByCode(String stateCode) {
		
		logger.info("lookupStateByCode invoked for " + stateCode);
		StateDto stateDto = null;

		try {
			StateRequest request = new StateRequest();
			request.setCode(stateCode);

			StateResponse response = restTemplate.postForObject(serviceUrl
					+ "/api/reference/getStateByCode", request, StateResponse.class);
			
			if (response != null) {
				stateDto = response.getState();
			}
		} catch (HttpClientErrorException e) { // 404
			// Nothing found
		}
		return stateDto;
	}
	
}
