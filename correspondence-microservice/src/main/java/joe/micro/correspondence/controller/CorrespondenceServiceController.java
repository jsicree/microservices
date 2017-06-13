package joe.micro.correspondence.controller;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import joe.micro.client.correspondence.EmailRequest;
import joe.micro.client.correspondence.EmailResponse;
import joe.micro.client.correspondence.RequestStatus;

@RestController
@RequestMapping("api/correspondence")
public class CorrespondenceServiceController {

//	@Autowired
//	private AccountService accountService;

	protected final static org.slf4j.Logger log = LoggerFactory
			.getLogger(CorrespondenceServiceController.class);

	@RequestMapping(value = "/sendWelcomeEmail", method = RequestMethod.POST, produces = "application/json")
	public EmailResponse sendWelcomeEmail(@RequestBody EmailRequest request) {
		EmailResponse response = new EmailResponse();
		log.warn("Sending welcome email to " + request.getEmail());
		response.setStatus(RequestStatus.OK);
		return response;
	}

}
