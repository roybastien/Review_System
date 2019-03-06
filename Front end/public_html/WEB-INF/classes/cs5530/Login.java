package cs5530;

import java.sql.ResultSet;
import java.sql.Statement;

public class Login {
	public Login()
	{};

	public String verifyPassword(String username, String password, Statement stmt)
	{
		String sql="select * from Users where login='"+username+"'";
		String output="";
		ResultSet rs=null;

		try{
			rs=stmt.executeQuery(sql);
			while (rs.next())
			{
				String storedPassword = rs.getString("password");
				if (password.equals(storedPassword)){
					
					if(rs.getString("user_type").equals("1")){
						output="Success, password confirmed admin.\n";
					}
					else{
						output="Success, password confirmed.\n";
					}
				}
				else if (rs == null){
					output = "Login could not be found. \n";
				}
				else{
					output=("Incorrect Password.\n");
				}
			}

			rs.close();
		}
		catch(Exception e)
		{
			output = e.toString();
//			System.out.println("cannot execute the query: " + sql);
//			System.out.println(e);
		}
		finally
		{
			try{
				if (rs!=null && !rs.isClosed())
					rs.close();
			}
			catch(Exception e)
			{
				System.out.println("cannot close resultset");
			}
		}
		return output;
	}
}
