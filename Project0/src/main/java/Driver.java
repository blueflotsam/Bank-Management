import java.util.ArrayList;
import java.util.Scanner;

import actors.Employee;
import actors.Person;
import actors.User;
import actors.account;
import actors.Dao.accountDao;
import actors.Dao.employeeDao;
import actors.Dao.userDao;

public class Driver {
	public static int menuGuest() {
		System.out.println("Welcome Guest please select an option:\n1: login\n2: register\n3: exit");
		return collectInt();
	}
	
	public static int menuUser(User u) {
		ArrayList<account> list=u.getAccounts();
		int pos=1;
		for(account a: list) {
			System.out.println(pos+": "+a);
			pos++;
		}
		System.out.println(pos+": logout");
		pos=collectInt();
		return pos;
	}
	
	public static int menuAccount(account a) {
		float balance=a.getAmount();
		System.out.println("Current account balance: "+balance+" choose what to do:");
		if(a.getPrevious() instanceof Employee) {
			System.out.println("0: Transfer funds to another account");
		}
		System.out.println("1: Deposit\n2: Withdraw\n3: return to accounts screen\n4: logout");
		int n=collectInt();
		return n;
	}
	
	public static int menuEmployee() {
		System.out.println("Please select an option:\n1: view pending accounts\n2: view bankAccounts\n3: view logs\n4:logout");
		return collectInt();
	}

	public static int collectInt() {
		Scanner scrn=new Scanner(System.in);
		int num=-1;
		while(num==-1) {
			String s=scrn.nextLine();
			char c='a';
			if(isNumber(s)) {
				num=Integer.parseInt(s);
			}
			else {
				System.out.println("Invalid number please enter a number");
			}

		}
		//scrn.close();
		return num;
	}
	public static boolean isNumber(String s) {
		for(int i=0;i<s.length();i++) {
			char c=s.charAt(i);
			if(!Character.isDigit(c))
				return false;
		}
		return true;
	}
	
	public static String collectString() {
		Scanner scrn=new Scanner(System.in);
		String s="-1";
		while(s.equals("-1")) {
			s=scrn.nextLine();
		}
		//scrn.close();
		return s;
	}
	
	public static Person login(String name, String pass) {
		userDao d=new userDao();
		Person p=d.getLogin(name, pass);
		return p;
	}
	
	public static Person employeeLogin(String name, String pass) {
		employeeDao d=new employeeDao();
		Person p=d.getLogin(name, pass);
		return p;
	}
	
	public static void main(String[] args) {
		Person p=new Person();
		userDao uD=new userDao();
		ArrayList<User> pending=new ArrayList<User>();
		while(true) {
			if(p instanceof User) {
				User u=(User)p;
				int num=menuUser(u)-1;
				ArrayList<account> list=u.getAccounts();
				if(num==list.size()) {
					p=new Person();
				}
				else if(num<list.size()) {
					list.get(num).setPrevious(u);
					p=list.get(num);
					
				}
				else {
					System.out.println("invalid input");
				}
			}
			
			else if(p instanceof Employee) {
				Employee e=(Employee)p;
				int num=menuEmployee();
				if(num==1) {//view pending accounts
					int index=1;
					System.out.println("Please enter an account you would like to approve/reject");
					
					for(User u:pending) {
						System.out.println(index+": "+u);
						index++;
					}
					System.out.println(index+": back to employee homescreen");
					index=collectInt()-1;
					if(index==pending.size()) {
						continue;
					}
					else if(index<pending.size()) {
						//approve or deny
						System.out.println("Would you like to:\n1: approve the account\n2: reject the account");
						int n=-1;
						while(n<1||n>2) {
							n=collectInt();
						}
						if(n==1) {
							//add pending[index] into data base and remove from array list
							userDao dao2=new userDao();
							dao2.add(pending.get(index));
							pending.remove(index);
							accountDao dao=new accountDao();
							dao.add(pending.get(index).getAccounts().get(0));
							pending.remove(index);
						}
						else {
							//remove pending[index] from arraylist
							pending.remove(index);
						}
					}
					else {
						System.out.println("Invalid input");
					}
				}
				else if(num==2) {//view bank accounts (change p to account on selection
					int index=1;
					System.out.println("Enter the account you would like to select");
					for(account a: e.getAccounts()) {
						System.out.println(index+": "+a);
						index++;
					}
					System.out.println(index+": logout");
					index=collectInt()-1;
					if(index==e.getAccounts().size()) {
						p=new Person();
					}
					else if(index<e.getAccounts().size()) {
						e.getAccounts().get(index).setPrevious(e);
						p=e.getAccounts().get(index);
						
					}
					else {
						System.out.println("Invalid input");
					}
				}
				else if(num==3) {//view logs
					
				}
				else if(num==4) {//logout
					p=new Person();
				}
				else {
					System.out.println("Invalid Input");
				}
			}
			
			else if(p instanceof account) {
				account a=(account)p;
				int num=menuAccount(a);
				if(num==0&&a.getPrevious() instanceof Employee) {
					Employee e=(Employee)a.getPrevious();
					ArrayList<account> list=e.getAccounts();
					int index=1;
					System.out.println("please enter choose the account you want to transfer funds to");
					for(account acc:list) {
						System.out.println(index+": "+acc);
					}
					index=collectInt()-1;
					if(index<list.size()) {
						account a2=list.get(index);
						System.out.println("Enter the amount of funds to transfer");
						int input=collectInt();
						if(input>a.getAmount()) {
							System.out.println("unable to complete transaction "+a+" does not have enough money");
						}
						else {
							a.setAmount(a.getAmount()-input);
							a2.setAmount(a2.getAmount()+input);
							
						}
					}
					else {
						System.out.println("invalid input");
					}
				}
				else if(num==1) {//deposit
					System.out.println("enter an amount to deposit");
					float dep=collectInt();
					a.setAmount(a.getAmount()+dep);
					
				}
				else if(num==2) {//withdraw
					System.out.println("enter an amount to withdraw");
					float dep=collectInt();
					dep=a.getAmount()-dep;
					if(dep>=0)
						a.setAmount(dep);
					else
						System.out.println("cant withdraw more then your current balance");
				}
				else if(num==3) {//return to account screen
					p=a.getPrevious();
				}
				else if(num==4) {//log out
					p=new Person();
				}
				else {
					System.out.println("invalid input");
				}
			}
			
			else {//guest stuff
				int num=menuGuest();
				if(num==1) {
					//login
					System.out.println("enter your Username: ");
					String name=collectString();
					System.out.println("enter your password");
					String pass=collectString();
					p=login(name,pass);
					if(p==null) {
						p=employeeLogin(name, pass);
						if(p==null) {
							System.out.println("invalid username or password");
						}
					}
					
				}
				else if(num==2) {
					//register stuff
					System.out.println("enter your new Username: ");
					String name=collectString();
					System.out.println("enter your new password");
					String pass=collectString();
					System.out.println("enter your starting balance");
					int n=collectInt();
					userDao d=new userDao();
					User u=new User(0,name,pass);
					account a =new account(n);
					u.addAccounts(a);
					pending.add(u);
					p=u;
				}
				else if(num==3){
					System.out.println("come again!");
					System.exit(0);
				}
				else
					System.out.println("invalid input");
			}
		}
		//User u=new User(3, "java","coffee");
		//u=uD.add(u);
		//System.out.println(uD.getAll());
		//uD.delete(u);
		
	}
}
