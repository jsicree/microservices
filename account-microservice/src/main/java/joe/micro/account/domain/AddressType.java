package joe.micro.account.domain;

public enum AddressType {
	BILLING (1L, "Billing"),
	DELIVERY (2L, "Delivery");

    private final Long id;  
    private final String type; 

    AddressType(Long id, String type) {
        this.id = id;
        this.type = type;
    }
    public Long id() { return id; }
    public String type() { return type; }	
	
}
