package joe.micro.reference.service;

import java.util.List;

import joe.micro.reference.domain.Country;
import joe.micro.reference.domain.State;

public interface ReferenceDataService {

	public List<Country> getCountries() throws ReferenceDataServiceException;

	public Country getCountryByCode(final String code) throws ReferenceDataServiceException;

	public List<State> getStatesByCountryCode(String countryCode) throws ReferenceDataServiceException;

}
