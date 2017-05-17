package joe.micro.account.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity(name="ADDRESS")
public class Address {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="PRIMARY_ADDRESS_LINE")
	private String primaryAddressLine;

	@Column(name="SECONDARY_ADDRESS_LINE")
	private String secondaryAddressLine;

	@Column(name="CITY_NAME")
	private String cityName;

	@Column(name="STATE")
	private String state;
	
	@Column(name="ZIP_CODE")
	private String zipCode;

//	@OneToOne(mappedBy = "ADDRESS")
//	private Account account;
	
	public Address() {
		// TODO Auto-generated constructor stub
	}

	public Address(Long id, String primaryAddressLine, String secondaryAddressLine, String cityName, String state,
			String zipCode) {
		super();
		this.id = id;
		this.primaryAddressLine = primaryAddressLine;
		this.secondaryAddressLine = secondaryAddressLine;
		this.cityName = cityName;
		this.state = state;
		this.zipCode = zipCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPrimaryAddressLine() {
		return primaryAddressLine;
	}

	public void setPrimaryAddressLine(String primaryAddressLine) {
		this.primaryAddressLine = primaryAddressLine;
	}

	public String getSecondaryAddressLine() {
		return secondaryAddressLine;
	}

	public void setSecondaryAddressLine(String secondaryAddressLine) {
		this.secondaryAddressLine = secondaryAddressLine;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

//	public Account getAccount() {
//		return account;
//	}
//
//	public void setAccount(Account account) {
//		this.account = account;
//	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Address [id=");
		builder.append(id);
		builder.append(", primaryAddressLine=");
		builder.append(primaryAddressLine);
		builder.append(", secondaryAddressLine=");
		builder.append(secondaryAddressLine);
		builder.append(", cityName=");
		builder.append(cityName);
		builder.append(", state=");
		builder.append(state);
		builder.append(", zipCode=");
		builder.append(zipCode);
		builder.append("]");
		return builder.toString();
	}

	
}
