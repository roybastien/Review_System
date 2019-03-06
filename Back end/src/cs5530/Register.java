package cs5530;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Statement;

public class Register {
	public Register()
	{}
	
	
	public String registerUser(String username, String password, Statement stmt)
	{
		String sql = "INSERT INTO Users (login, password) VALUES ('"+username+"', '"+password+"')";

		String output="";
//		System.out.println("executing "+sql);
		try{
			stmt.executeUpdate(sql);
			output = "Successful Registration :) \n";
		}
		catch(Exception e)
		{
			output = "You could not be registered.";
//			System.out.println("You could not be registered.");
			//		 		System.out.println(e);
		}
	    return output;
	}
	
	
	public String getUserInfo(String username, String password, Statement stmt) throws IOException
	{
		String info = "";
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Please enter your comma delimited user information in the form: \n"
		 		+ "name, street address, city, state, phone number");
		while ((info = in.readLine()) == null && username.length() == 0);
		String[] infoArr = info.split(",");
		
		String sql = "UPDATE Users SET name='"+infoArr[0].trim()+"', address='"+infoArr[1].trim()+"', "
					+ "city='"+infoArr[2].trim()+"', state='"+infoArr[3].trim()+"', phone_num='"+infoArr[4].trim()+"' "
					+ "WHERE login='"+username+"'";

		String output="";
//		System.out.println("executing "+sql);
		try{

			stmt.executeUpdate(sql);
			output = "Your information was entered :) \n";
			
		}
		catch(Exception e)
		{
			System.out.println("Your information could not be entered.");
			//		 		System.out.println(e);
		}
	    return output;
	}
	
	
	public String isUserAdmin(String username, String password, Statement stmt) throws IOException
	{
		String ans = "";
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Are you an administrator? (y or n)");
		while ((ans = in.readLine()) == null && username.length() == 0);
		int admin = 0;
		if (ans.equals("y")){admin=1;};
		
		String sql = "UPDATE Users SET user_type='"+admin+"' WHERE login='"+username+"'";

		String output="";
//		System.out.println("executing "+sql);
		try{

			stmt.executeUpdate(sql);
			if (admin==1){
				output = "Thank you, admin!";
			}
			else{
				output = "Thank You! \n";
			}
		}
		catch(Exception e)
		{
			System.out.println("Your information could not be entered.");
			//		 		System.out.println(e);
		}
	    return output;
	}
}
