package joe.micro.reference.controller;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import joe.micro.client.reference.CountriesResponse;
import joe.micro.client.reference.CountryDto;
import joe.micro.client.reference.StateDto;
import joe.micro.client.reference.StatesByCountryRequest;
import joe.micro.client.reference.StatesByCountryResponse;
import joe.micro.reference.domain.Country;
import joe.micro.reference.domain.State;
import joe.micro.reference.service.ReferenceDataService;
import joe.micro.reference.service.ReferenceDataServiceException;

@RestController
@RequestMapping("api/reference")
public class ReferenceDataServiceController {

	@Autowired
	private ReferenceDataService refDataService;

	protected final static org.slf4j.Logger log = LoggerFactory
			.getLogger(ReferenceDataServiceController.class);

	public ReferenceDataServiceController() {
		// TODO Auto-generated constructor stub
	}

	@RequestMapping(value = "/getCountries", method = RequestMethod.POST, produces = "application/json")
	public CountriesResponse getCountries() {
		CountriesResponse response = new CountriesResponse();
		
		try {
			List<Country> countries = refDataService.getCountries();
			log.warn("Got " + countries.size() + " countries");
			for (Country c : countries) {
				CountryDto dto = new CountryDto();
				dto.setId(c.getId());
				dto.setCode(c.getCode());
				dto.setName(c.getName());
				response.getCountries().add(dto);
			}
		} catch (ReferenceDataServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = "/getStatesByCountry", method = RequestMethod.POST, produces = "application/json")
	public StatesByCountryResponse getStatesByCountry(@RequestBody(required=false) StatesByCountryRequest request) {
		StatesByCountryResponse response = new StatesByCountryResponse();

		log.warn("Looking up states for " + request.getCountryCode());

		try {
			List<State> states = refDataService.getStatesByCountryCode(request.getCountryCode());
			log.warn("Got " + states.size() + " states");
			for (State s : states) {
				StateDto dto = new StateDto();
				dto.setId(s.getId());
				dto.setCode(s.getCode());
				dto.setName(s.getName());
				dto.setCountryCode(s.getCountry().getCode());
				response.getStates().add(dto);
			}
		} catch (ReferenceDataServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
}
