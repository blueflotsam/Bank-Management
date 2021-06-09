package actors;

import java.util.ArrayList;

public class User extends Person{
	private int id;
	private String username;
	private String password;
	private ArrayList<account> accounts;
	
	public ArrayList<account> getAccounts() {
		return accounts;
	}
	public void addAccounts(account accounts) {
		if(this.accounts==null) {
			this.accounts=new ArrayList<account>();
		}
		this.accounts.add(accounts);
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + "]";
	}
	public User(int id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}
	public User(int id, String username, String password, float balance) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		account a=new account(balance);
		this.addAccounts(a);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
}
