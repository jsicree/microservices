package joe.micro.reference.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface StateRepository extends CrudRepository<State, Long> {

	public List<State> findByCountry(Country country);
}
