package joe.micro.reference.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import joe.micro.reference.domain.Country;
import joe.micro.reference.domain.CountryRepository;
import joe.micro.reference.domain.State;
import joe.micro.reference.domain.StateRepository;
import joe.micro.reference.service.ReferenceDataService;
import joe.micro.reference.service.ReferenceDataServiceException;

@Service
public class ReferenceDataServiceImpl implements ReferenceDataService {

	@Autowired
	private CountryRepository countryRepo;

	@Autowired
	private StateRepository stateRepo;

	protected final static org.slf4j.Logger log = LoggerFactory
			.getLogger(ReferenceDataServiceImpl.class);
	
	
	public ReferenceDataServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Country> getCountries() throws ReferenceDataServiceException {
		return (List<Country>) countryRepo.findAll();
	}

	@Override
	public Country getCountryByCode(String code) throws ReferenceDataServiceException {
		return countryRepo.findByCode(code);
	}

	@Override
	public List<State> getStatesByCountryCode(String countryCode) throws ReferenceDataServiceException {

		List<State> stateList = new ArrayList<State>();

		Country c = countryRepo.findByCode(countryCode);
		log.warn("Country Lookup(" + countryCode + "): " + c);
		
		if (c != null) {
			stateList = stateRepo.findByCountry(c);
		}
		
		return stateList;
	}

}
