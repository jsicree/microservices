package joe.micro.account.domain;

import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {

	Account findByEmail(String email);

}
