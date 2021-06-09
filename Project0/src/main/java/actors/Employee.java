package actors;

import java.util.ArrayList;

public class Employee extends Person{
		private int id;
		private String username;
		private String password;
		private ArrayList<account> accounts=new ArrayList<account>();
		public ArrayList<account> getAccounts() {
			return accounts;
		}
		public void addAccounts(account accounts) {
			this.accounts.add(accounts);
		}
		@Override
		public String toString() {
			return "User [id=" + id + ", username=" + username + ", password=" + password + "]";
		}
		public Employee(int id, String username, String password) {
			super();
			this.id = id;
			this.username = username;
			this.password = password;
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
