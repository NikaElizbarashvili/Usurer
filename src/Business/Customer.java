package Business;

public class Customer {
	private String customerID;
	private String id;
	private String firstName;
	private String lastName;
	private float score;
	private float existingPayment;

	public Customer(String customerID, String id, String firstName, String lastName, float score,
			float existingPayment) {
		this.customerID = customerID;
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.score = score;
		this.existingPayment = existingPayment;
	}

	public String getCustomerID() {
		return customerID;
	}

	public String getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public float getScore() {
		return score;
	}

	public float getExistingPayment() {
		return existingPayment;
	}

}
