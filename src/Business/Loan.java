package Business;

public class Loan {

	private String customerID;
	private double amount;
	private int maturity;
	private float rate;
	private double pmt;

	public Loan(String customerID, double amount, int maturity, float rate, double pmt) {
		this(amount, maturity, rate, pmt);
		this.customerID = customerID;
	}

	public Loan(double amount, int maturity, float rate, double pmt) {
		this.amount = amount;
		this.maturity = maturity;
		this.rate = rate;
		this.pmt = pmt;
	}

	public String getCustomerID() {
		return customerID;
	}

	public double getAmount() {
		return amount;
	}

	public int getMaturity() {
		return maturity;
	}

	public float getRate() {
		return rate;
	}

	public double getPmt() {
		return pmt;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public void setMaturity(int maturity) {
		this.maturity = maturity;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public void setPmt(long pmt) {
		this.pmt = pmt;
	}

}
