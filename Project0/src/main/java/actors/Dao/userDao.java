package actors.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import actors.Person;
import actors.User;
import actors.account;
public class userDao implements daoInterface<User> {
	
	private Connection conn=JDBCConnection.getConnection();
	
	@Override
	public User add(User u) {
		String sql="insert into customer values (default, ?, ?) returning *;";
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());
			boolean b=ps.execute();
			if(b) {
				ResultSet rs=ps.getResultSet();
				if(rs.next()) {
					u.setId(rs.getInt("id"));
					return u;
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public User getId(Integer id) {
		String sql = "select * from customer where id = ?;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int ID=rs.getInt("id");
				String username=rs.getString("username");
				String password=rs.getString("password");
				User u = new User(ID, username, password);
				return u;
			}
				
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public User getLogin(String user, String pass) {
		String sql="select * from customer where username = ? and password = ?;";
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, user);
			ps.setString(2, pass);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				int ID=rs.getInt("id");
				String username=rs.getString("username");
				String password=rs.getString("password");
				User u = new User(ID, username, password);
				sql="select * from account where id=?;";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, ID);
				rs=ps.executeQuery();
				while(rs.next()) {
					float balance=rs.getFloat("amount");
					account a=new account(balance);
					u.addAccounts(a);
				}
				return u;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	
	}
	
	@Override
	public List<User> getAll() {
		List<User> users=new ArrayList<User>();
		String sql="select * from customer";
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			
			while(rs.next()) {
				//User u= new User();
				int id=rs.getInt("id");
				String username=rs.getString("username");
				String password=rs.getString("password");
				User u=new User(id,username,password);
				users.add(u);
				
			}
			return users;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public boolean update(User u) {
		// leaving blank not sure if needed
		return false;
	}

	public boolean delete(User u) {
		String sql = "delete from customer where id = ";
		sql=sql.concat(Integer.toString(u.getId()));
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			//String s=Integer.toString(u.getId());
			//ps.setString(1, (String) s);
			boolean success = ps.execute();
			if (success) {
				return true;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}
}
