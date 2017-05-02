package joe.micro.account.domain;

public class Account {
	private Long id;
	private String accountIdentifier;

	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Account(Long id, String accountIdentifier) {
		super();
		this.id = id;
		this.accountIdentifier = accountIdentifier;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountIdentifier() {
		return accountIdentifier;
	}

	public void setAccountIdentifier(String accountIdentifier) {
		this.accountIdentifier = accountIdentifier;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountIdentifier == null) ? 0 : accountIdentifier.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountIdentifier == null) {
			if (other.accountIdentifier != null)
				return false;
		} else if (!accountIdentifier.equals(other.accountIdentifier))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Account [id=");
		builder.append(id);
		builder.append(", accountIdentifier=");
		builder.append(accountIdentifier);
		builder.append("]");
		return builder.toString();
	}

}
