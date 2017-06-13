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
import joe.micro.client.reference.RequestStatus;
import joe.micro.client.reference.StateDto;
import joe.micro.client.reference.StateRequest;
import joe.micro.client.reference.StateResponse;
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

	protected final static org.slf4j.Logger log = LoggerFactory.getLogger(ReferenceDataServiceController.class);

	public ReferenceDataServiceController() {
		// TODO Auto-generated constructor stub
	}

	@RequestMapping(value = "/getCountries", method = RequestMethod.POST, produces = "application/json")
	public CountriesResponse getCountries() {
		CountriesResponse response = new CountriesResponse();

		try {
			List<Country> countries = refDataService.getCountries();
			log.warn("Got " + countries.size() + " countries");
			if (countries.size() > 0) {
				for (Country c : countries) {
					CountryDto dto = new CountryDto();
					dto.setId(c.getId());
					dto.setCode(c.getCode());
					dto.setName(c.getName());
					response.getCountries().add(dto);
				}
				response.setStatus(RequestStatus.OK);
			}
		} catch (ReferenceDataServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = "/getStatesByCountry", method = RequestMethod.POST, produces = "application/json")
	public StatesByCountryResponse getStatesByCountry(@RequestBody(required = false) StatesByCountryRequest request) {
		StatesByCountryResponse response = new StatesByCountryResponse();

		log.warn("Looking up states for " + request.getCountryCode());
		if (!request.getCountryCode().isEmpty()) {
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
				response.setStatus(RequestStatus.OK);
			} catch (ReferenceDataServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			response.setStatus(RequestStatus.ERROR);
			response.setMessage("Unable to get states for country. Country code is empty or null");
		}
			
		return response;

	}

	@RequestMapping(value = "/getStateByCode", method = RequestMethod.POST, produces = "application/json")
	public StateResponse getStateByCode(@RequestBody(required = false) StateRequest request) {
		StateResponse response = new StateResponse();

		log.warn("Looking up state " + request.getCode());
		
		if (!request.getCode().isEmpty()) {
			try {
				State state = refDataService.getStateByStateCode(request.getCode());
				if (state != null) {
					StateDto dto = new StateDto();				
					dto.setId(state.getId());
					dto.setCode(state.getCode());
					dto.setName(state.getName());
					dto.setCountryCode(state.getCountry().getCode());
					response.setState(dto);
				} else {
					response.setStatus(RequestStatus.OK);
					response.setMessage("Unable to find state with code " + request.getCode());
				}
			} catch (ReferenceDataServiceException e) {
				response.setStatus(RequestStatus.ERROR);
				response.setMessage("Unable to look up state. A ReferenceDataException occurred: " + e.getMessage());
			}
		} else {
			response.setStatus(RequestStatus.ERROR);
			response.setMessage("Unable to look up state. State code is empty or null");
		}
		return response;
	}

}
