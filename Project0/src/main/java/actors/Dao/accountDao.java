package actors.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import actors.User;
import actors.account;
public class accountDao implements daoInterface<account> {
	
	private Connection conn=JDBCConnection.getConnection();
	
	@Override
	public account add(account u) {
		String sql="insert into account values (?, ?) returning *;";
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setInt(1, u.getId());
			ps.setFloat(2, u.getAmount());
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
	public void add(float f){
		String sql="insert into account values (default, ?) returning *;";
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setFloat(1, f);
			boolean b=ps.execute();
			if(b) {
				ResultSet rs=ps.getResultSet();
				if(rs.next()) {
					//u.setId(rs.getInt("id"));
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public account getId(Integer id) {
		String sql = "select * from account where id = ?;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int ID=rs.getInt("id");
				float amount=rs.getFloat("amount");
				account u = new account(ID,amount);
				return u;
			}
				
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<account> getAll() {
		List<account> users=new ArrayList<account>();
		String sql="select * from account";
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			
			while(rs.next()) {
				//User u= new User();
				int id=rs.getInt("id");
				float f=rs.getFloat("amount");
				account u=new account(id,f);
				users.add(u);
				
			}
			return users;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public boolean update(account u) {
		// leaving blank not sure if needed
		return false;
	}

	public boolean delete(account u) {
		String sql = "delete from account where id = ";
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
