package cs5530;


import java.lang.*;
import java.sql.*;
import java.io.*;

public class testdriver2 {

	/**
	 * @param args
	 */
	public static void displayMenu()
	{
		System.out.println("*******Welcome to the UTrack System*******");
		System.out.println("1. Register for the system:");
		System.out.println("2. Log into the system:");
		System.out.println("3. exit:");
		System.out.println("please enter your choice:");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connector con = null;
		String choice;
		String username;
		String password;
		String sql=null;
		Boolean isLoggedIn = false;
		Boolean isAdmin = false;
		int c=0;
		try
		{
			con= new Connector();
			//	             System.out.println ("Database connection established");

			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

			while(isLoggedIn == false)
			{
				displayMenu();
				while ((choice = in.readLine()) == null && choice.length() == 0);
				try{
					c = Integer.parseInt(choice);
				}catch (Exception e)
				{
					continue;
				}
				if (c<1 | c>3)
					continue;
				if (c==1)
				{
					System.out.println("please enter a username:");
					while ((username = in.readLine()) == null && username.length() == 0);
					System.out.println("please enter a password:");
					while ((password = in.readLine()) == null && password.length() == 0);
					Register reg = new Register();
					System.out.println(reg.registerUser(username, password, con.stmt));
					System.out.println(reg.getUserInfo(username, password, con.stmt));
					String reply = reg.isUserAdmin(username, password, con.stmt);
					if (reply.contains("admin")){isAdmin=true;}
					System.out.println(reply);
					isLoggedIn = true;
					if (isLoggedIn == true){
						FunctionSet func = new FunctionSet(username, isAdmin, con.stmt);
						func.enterMenu();
					}
				}
				else if (c==2)
				{	 
					System.out.println("please enter a username:");
					while ((username = in.readLine()) == null && username.length() == 0);
					System.out.println("please enter a password:");
					while ((password = in.readLine()) == null && password.length() == 0);
					Login log = new Login();
					String reply = log.verifyPassword(username, password, con.stmt);
					if (reply.contains("Success")){
						isLoggedIn=true;
						if (reply.contains("admin")){
							isAdmin=true;
						}
					}
					System.out.println(reply);
					if (isLoggedIn == true){
						FunctionSet func = new FunctionSet(username,isAdmin, con.stmt);
						func.enterMenu();
					}
				}
				else if(c==3)
				{   
					System.out.println("Remeber to pay us!");
					isLoggedIn = false;
					con.stmt.close();
					break;
				}
				else{
					System.out.println("That is not an option.");
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.err.println ("Either connection error or query execution error!");
		}
		finally
		{
			if (con != null)
			{
				try
				{
					con.closeConnection();
					System.out.println ("Database connection terminated");
				}
				catch (Exception e) { /* ignore close errors */ }
			}	
		}
	}
}
