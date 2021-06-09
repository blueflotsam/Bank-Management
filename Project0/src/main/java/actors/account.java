package actors;

public class account extends Person {
	private int id;
	private float amount;
	private Person previous;
	public Person getPrevious() {
		return previous;
	}
	public void setPrevious(Person previous) {
		this.previous = previous;
	}
	public account(float amount) {
		super();
		this.amount = amount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public account(int id, float amount) {
		super();
		this.id = id;
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "account [amount=" + amount + "]";
	}
}
