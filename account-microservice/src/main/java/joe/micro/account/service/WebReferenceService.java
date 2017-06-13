package joe.micro.account.service;

import joe.micro.client.reference.StateDto;

public interface WebReferenceService {

	public StateDto lookupStateByCode(String stateCode); 
}
