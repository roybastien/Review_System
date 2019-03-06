package cs5530;
import java.sql.*;
import java.util.ArrayList;
import java.io.*;


public class FunctionSet{
	Boolean isUserAdmin;
	Statement stmt;
	Boolean isLoggedIn = true;
	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	String login;
	public FunctionSet(String login, Boolean isAdmin, Statement stmt){
		this.login = login;
		this.isUserAdmin = isAdmin;
		this.stmt = stmt;
	};

	public void enterMenu(){
		// TODO Auto-generated method stub
		String choice;
		int c=0;
		try
		{
			while(isLoggedIn == true)
			{
				displayMenu();
				while ((choice = in.readLine()) == null && choice.length() == 0);
				try{
					c = Integer.parseInt(choice);
				}catch (Exception e)
				{
					continue;
				}
				if (c<1 | c>12)
					continue;
				if (c==1)
				{
					enterPOI();
				}
				else if (c==2)
				{	 
					editPOI();
				}
				else if(c==3)
				{   
					recordVisit();
				}
				else if(c==4)
				{   
					recordFavorite();
				}
				else if(c==5){
					recordFeedback();
				}
				else if(c==6){
					recordUsefullness();
				}
				else if(c==7){
					trustSomeone();
				}
				else if(c==8){
					getMostUsefulFeedbacks();
				}
				else if(c==9){
					browsePOI();
				}
				else if(c==10){
					quickSummary();
				}
				else if(c==11){
					giveAwards();
				}
				else if(c==12)
				{
					System.out.println("Logging out.");
					isLoggedIn = false;
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
		}
	}


	public String displayMenu(){
		String output = "";
		System.out.println("Please select from an option below:");
		System.out.println("1. Enter a new point of interest.");
		System.out.println("2. Edit a point of interest.");
		System.out.println("3. Record a visit to a point of interest.");
		System.out.println("4. Record a favorite point of interest.");
		System.out.println("5. Enter feedback for a point of interest.");
		System.out.println("6. Provide a usefulness rating.");
		System.out.println("7. Trust a user.");
		System.out.println("8. Get most useful feedbacks for a point of interest.");
		System.out.println("9. Browse and query the POI system.");
		System.out.println("10. Get a quick statistics summary.");
		System.out.println("11. Give User Awards.");
		System.out.println("12. Logout of the system.");
		return output;
	}

	public void enterPOI() throws IOException {
		String reply = "";
		if (isUserAdmin == false){
			System.out.println("You do not have sufficient privledges for that request.\n");
			return;
		}
		System.out.println("Please enter the establishment information in the following comma seperated form: ");
		System.out.println("Name, street address, city, state, phone_number.");
		while ((reply = in.readLine()) == null && reply.length() == 0);
		String[] rep = reply.split(",");
		String name = rep[0];
		String sql = "INSERT INTO POI (name, address, city, state, phone_num) VALUES ('"+rep[0].trim()+"', '"+rep[1].trim()+"', '"+rep[2].trim()+"', '"+rep[3].trim()+"', '"+rep[4].trim()+"')";
		try{
			stmt.executeUpdate(sql);
		}
		catch(Exception e)
		{
			System.out.println("You've encountered an error. Goodbye.");
			isLoggedIn = false;
			System.out.println(e);
			return;
		}
		System.out.println("Thanks!");
		System.out.println("Now please enter " +name+ "'s:");
		System.out.println("url, year established, hours, estimated (integer) price per person, catagory");
		while ((reply = in.readLine()) == null && reply.length() == 0);
		rep = reply.split(",");
		sql = "UPDATE POI SET url='"+rep[0].trim()+"', year='"+rep[1].trim()+"', "
				+ "hours='"+rep[2].trim()+"', price='"+rep[3].trim()+"', catagory='"+rep[4].trim()+"' "
				+ "WHERE name='"+name+"'";
		try{
			stmt.executeUpdate(sql);
		}
		catch(Exception e)
		{
			System.out.println("You've encountered an error. Goodbye.");
			isLoggedIn = false;
			System.out.println(e);
			return;
		}

		System.out.println("Thanks!");
		System.out.println("Now please enter " +name+ "'s comma seperated keywords.");
		while ((reply = in.readLine()) == null && reply.length() == 0);
		int pid = getPID(name);
		rep = reply.split(",");
		for (String s : rep){
			s.trim();
		}
		for (String s : rep){
			sql="INSERT IGNORE INTO Keywords (word, language) VALUES ('"+s.trim()+"', 'english')";
			try{
				stmt.executeUpdate(sql);
			}
			catch(Exception e)
			{
				System.out.println("You've encountered an error entering keywords into Keywords. Goodbye.");
				isLoggedIn = false;
				System.out.println(e);
				return;
			}

			int wid = getWID(s);

			sql="INSERT INTO HasKeywords (pid, wid) VALUES ('"+pid+"', '"+wid+"')";
			try{
				stmt.executeUpdate(sql);
			}
			catch(Exception e)
			{
				System.out.println("You've encountered an error entering keywords into HasKeywords. Goodbye.");
				isLoggedIn = false;
				System.out.println(e);
				return;
			}
		}
	}
	

	public String enterPOIJSP(String poiNameIn, String streetIn, String cityIn, String stateIn, String phoneIn, String urlIn, String yearIn,
			String hoursIn, String priceIn, String catagoryIn, String keywordsIn) throws IOException {
		String output = "";

		String sql = "INSERT INTO POI (name, address, city, state, phone_num, url, year, hours, price, catagory) "
				+ "VALUES ('"+poiNameIn+"', '"+streetIn+"', '"+cityIn+"', '"+stateIn+"', '"+phoneIn+"', '"+urlIn+"', '"+yearIn+"', '"+hoursIn+"', '"+priceIn+"', '"+catagoryIn+"')";
		try{
			stmt.executeUpdate(sql);
			output = "Success";
		}
		catch(Exception e)
		{
			output = e.toString();
			return output;
		}

		int pid = getPID(poiNameIn);
		String rep[] = keywordsIn.split(",");
		for (String s : rep){
			s.trim();
		}
		for (String s : rep){
			sql="INSERT IGNORE INTO Keywords (word, language) VALUES ('"+s.trim()+"', 'english')";
			try{
				stmt.executeUpdate(sql);
				output = "Success";
			}
			catch(Exception e)
			{
				output = e.toString();
				return output;
			}

			int wid = getWID(s);

			sql="INSERT INTO HasKeywords (pid, wid) VALUES ('"+pid+"', '"+wid+"')";
			try{
				stmt.executeUpdate(sql);
				output = "Success";
			}
			catch(Exception e)
			{
				output = e.toString();
				return output;
			}
		}
		return output;
	}
	
	

	public void editPOI() throws IOException {
		String reply = "";
		if (isUserAdmin == false){
			System.out.println("You do not have sufficient privledges for that request.\n");
			return;
		}
		System.out.println("Please enter the name of the establishment to edit.");
		while ((reply = in.readLine()) == null && reply.length() == 0);
		String[] rep = reply.split(",");
		String name = rep[0];
		System.out.println("Please select an attribute to edit:");
		System.out.println("1. Street address.");
		System.out.println("2. City.");
		System.out.println("3. State.");
		System.out.println("4. Phone Number.");
		System.out.println("5. url.");
		System.out.println("6. Year extablished.");
		System.out.println("7. Hours.");
		System.out.println("8. Average Price.");
		System.out.println("9. Catagory.");
		String catagory = "";
		while ((reply = in.readLine()) == null && reply.length() == 0);
		if (reply.equals("1")){
			catagory = "address";
		}
		else if (reply.equals("2")){
			catagory = "city";
		}
		else if (reply.equals("3")){
			catagory = "state";
		}
		else if (reply.equals("4")){
			catagory = "phone_num";
		}
		else if (reply.equals("5")){
			catagory = "url";
		}
		else if (reply.equals("6")){
			catagory = "year";
		}
		else if (reply.equals("7")){
			catagory = "hours";
		}
		else if (reply.equals("8")){
			catagory = "price";
		}
		else if (reply.equals("9")){
			catagory = "catagory";
		}
		else{
			System.out.println("That was not a selection.");
			return;
		}

		System.out.println("Please enter the new value.");
		while ((reply = in.readLine()) == null && reply.length() == 0);
		String change = "";
		change = reply;
		String sql = "UPDATE POI SET "+catagory+"='"+change+"' WHERE name='"+name+"'";
		System.out.println("executing "+sql);
		try{
			stmt.executeUpdate(sql);
		}
		catch(Exception e)
		{
			System.out.println("You've encountered an error. Goodbye.");
			isLoggedIn = false;
			//			System.out.println(e);
			return;
		}
		System.out.println("Thanks!\n");

		try{
			stmt.executeUpdate(sql);
		}
		catch(Exception e)
		{
			System.out.println("You've encountered an error entering updating a POI. Goodbye.");
			isLoggedIn = false;
			System.out.println(e);
			return;
		}
	}
	

	public String editPOIJSP(String name, String attribute, String newValue) throws IOException {
		String output = "";
		String sql = "UPDATE POI SET "+attribute+"='"+newValue+"' WHERE name='"+name+"'";
		System.out.println("executing "+sql);
		try{
			stmt.executeUpdate(sql);
			output = "Success";
		}
		catch(Exception e)
		{
			output = e.toString();
			return output;
		}
		return output;
	}
	
	public String recordSimpleVisit(String login, String poiName, String date, String numHeads, String cost) throws IOException {
		String reply = "";
		String output = "";
		int pid = 0;
		pid = getPID(poiName);
		if(pid==0){output = "That point of interest could not be found.";return output;}
		
		//String sql = "INSERT INTO Visit (login, pid, visitdate) VALUES ('"+logins.get(i)+"', '"+getPID(pids.get(i))+"', '"+dates.get(i)+"')";
		String sql = "INSERT INTO Visit (login, pid, visitdate) VALUES ('"+login+"', '"+pid+"', '"+date+"')";
		//			System.out.println("executing "+sql);
		try{
			stmt.executeUpdate(sql);
		}
		catch(Exception e)
		{
			return e.toString();
		}
		
		
//		double avgCost = (double)(cost / numHeads);
		double avgCost = Double.parseDouble(cost) / Double.parseDouble(numHeads);
		sql = "INSERT INTO VisEvent (cost, numberofheads, avg_cost) VALUES ('"+cost+"', '"+numHeads+"', '"+avgCost+"')";

		try{
			stmt.executeUpdate(sql);
		}
		catch(Exception e)
		{
			return e.toString();
		}
		output = "Thanks!";
		return output;
	}
	
	public void recordVisit() throws IOException {
		String reply = "";
		Boolean keepGoing = true;
		ArrayList<String> logins = new ArrayList<String>();
		ArrayList<String> pids = new ArrayList<String>();
		ArrayList<String> dates = new ArrayList<String>();
		ArrayList<Integer> heads = new ArrayList<Integer>();
		ArrayList<Integer> money = new ArrayList<Integer>();
		int pid = 0;
		String date = "";
		int cost = 0;
		int numHeads = 0;
		while(keepGoing == true){
			System.out.println("Please enter the establishment name you visited.");
			while ((reply = in.readLine()) == null && reply.length() == 0);
			String poiName = reply;
			pid = getPID(reply);
			if(pid==0){System.out.println("That POI could not be found.\n");return;}
			System.out.println("Please enter the date you visted in the format YYYY-MM-DD.");
			while ((reply = in.readLine()) == null && reply.length() == 0);
			date = reply;

			System.out.println("How many people were in your party?.");
			while ((reply = in.readLine()) == null && reply.length() == 0);
			numHeads = Integer.parseInt(reply);
			
			System.out.println("How much did it cost? (an integer please)");
			while ((reply = in.readLine()) == null && reply.length() == 0);
			cost = Integer.parseInt(reply);
			
			logins.add(login);
			pids.add(poiName);
			dates.add(date);
			heads.add(numHeads);
			money.add(cost);
			
			System.out.println("Would you like to enter another visit? (y or n)");
			while ((reply = in.readLine()) == null && reply.length() == 0);
			if (reply.equals("n")){
				keepGoing = false;
			}
		}
		System.out.println("You have entered the following information:");
		System.out.println("POI Name \t \t Date");
		for(int i = 0; i < logins.size(); i++){
			System.out.println(pids.get(i) + "\t \t" + dates.get(i));
		}
		System.out.println("Is this correct? (y or n)");
		while ((reply = in.readLine()) == null && reply.length() == 0);
		if(reply.equals("n")){
			return;
		}
		
		for (int i = 0; i < logins.size(); i++){
			String sql = "INSERT INTO Visit (login, pid, visitdate) VALUES ('"+logins.get(i)+"', '"+getPID(pids.get(i))+"', '"+dates.get(i)+"')";
//			System.out.println("executing "+sql);
			try{
				stmt.executeUpdate(sql);
			}
			catch(Exception e)
			{
				System.out.println("You've encountered an error. Goodbye.");
				isLoggedIn = false;
				System.out.println(e);
				return;
			}
		}
		
		for (int i = 0; i < heads.size(); i++){
			double avgCost = (double)(money.get(i) / heads.get(i));
			String sql = "INSERT INTO VisEvent (cost, numberofheads, avg_cost) VALUES ('"+money.get(i)+"', '"+heads.get(i)+"', '"+avgCost+"')";

			try{
				stmt.executeUpdate(sql);
			}
			catch(Exception e)
			{
				System.out.println("You've encountered an error. Goodbye.");
				isLoggedIn = false;
				System.out.println(e);
				return;
			}
		}
		System.out.println("Thanks!\n");

	}
	
	public void recordFavorite() throws IOException {
		String reply = "";
		System.out.println("Please enter the establishment name you visited.");
		while ((reply = in.readLine()) == null && reply.length() == 0);
		int pid = getPID(reply);
		if(pid==0){System.out.println("That POI could not be found.\n");return;}
		String sql = "INSERT INTO Favorites (login, pid, fvdate) VALUES ('"+login+"', '"+pid+"', CURDATE())"
				+ " ON DUPLICATE KEY UPDATE fvdate=CURDATE()";
		try{
			stmt.executeUpdate(sql);
		}
		catch(Exception e)
		{
			System.out.println("You've encountered an error. Goodbye.");
			isLoggedIn = false;
			System.out.println(e);
			return;
		}
		System.out.println("Success Thanks!\n");

	}
	
	public String recordFavoriteWeb(String login, String poi) throws IOException {
		String output = "";
		int pid = getPID(poi);
//		if(pid==0){output = "That point of interest could not be found.";return output;}
		if(pid==0){output = "That POI could not be found.";return output;}
		String sql = "INSERT INTO Favorites (login, pid, fvdate) VALUES ('"+login+"', '"+pid+"', CURDATE())"
				+ " ON DUPLICATE KEY UPDATE fvdate=CURDATE()";
		try{
			stmt.executeUpdate(sql);
			output = "Success";
		}
		catch(Exception e)
		{
			output = e.toString();
			return output;
		}
		return output;

	}
	
	public String recordFeedbackWeb(String login, String poi, String score, String text) throws IOException {
		String output = "";
		int pid = getPID(poi);
		if(pid==0){output = "That POI could not be found.\n";return output;}
		String sql = "SELECT * FROM Feedback WHERE pid='"+pid+"' AND login='"+login+"'";
		String result = null;
		ResultSet rs = null;
		try{
			rs=stmt.executeQuery(sql);
			while (rs.next())
			{
//				System.out.println(rs.getString("login"));
				result = rs.getString("login"); 
			}
			rs.close();
		}
		catch(Exception e)
		{
			output = e.toString();
			return output;
		}
		finally
		{
			try{
				if (rs!=null && !rs.isClosed())
					rs.close();
			}
			catch(Exception e)
			{
				output = "Cannot close resultset";
			}
		}
		
		if(result!=null){
			output = "You have already reviewed "+poi+"\n";
			return output;
		}
		
		sql = "INSERT INTO Feedback (text, fbdate, pid, login, score) VALUES "
									+ "('"+text+"', CURDATE(), '"+pid+"', '"+login+"', '"+score+"')";
		try{
			stmt.executeUpdate(sql);
		}
		catch(Exception e)
		{
			output = e.toString();
			return output;
		}
		output = "Success, thanks!";
		return output;
	}

	public void recordFeedback() throws IOException {
		String reply = "";
		System.out.println("Please enter the establishment name you want to review.");
		while ((reply = in.readLine()) == null && reply.length() == 0);
		int pid = getPID(reply);
		if(pid==0){System.out.println("That POI could not be found.\n");return;}
		
		String result = null;
		String sql = "SELECT * FROM Feedback WHERE pid='"+pid+"' AND login='"+login+"'";
		ResultSet rs = null;
//		System.out.println("executing "+sql);
		try{
			rs=stmt.executeQuery(sql);
			while (rs.next())
			{
				System.out.println(rs.getString("login"));
				result = rs.getString("login"); 
			}
			rs.close();
		}
		catch(Exception e)
		{
			System.out.println("cannot execute the query: "+sql);
			System.out.println(e);
			return;
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
		
		if(result!=null){
			System.out.println("Sorry, you have already reviewed "+reply+"\n");
			return;
		}
		

		System.out.println("How would you rate it from 1 - 10?");
		while ((reply = in.readLine()) == null && reply.length() == 0);
		String score = reply;
		
		System.out.println("Please insert a small comment.");
		while ((reply = in.readLine()) == null && reply.length() == 0);
		String text = reply;
		
		sql = "INSERT INTO Feedback (text, fbdate, pid, login, score) VALUES "
									+ "('"+text+"', CURDATE(), '"+pid+"', '"+login+"', '"+score+"')";
		try{
			stmt.executeUpdate(sql);
		}
		catch(Exception e)
		{
			System.out.println("You've encountered an error. Goodbye.");
			isLoggedIn = false;
			System.out.println(e);
			return;
		}
		System.out.println("Thanks!\n");
	}
	
	public String recordUsefullnessWeb(String poi, String reviewer, String rating ) throws IOException{

		String output = "";
		int pid = getPID(poi);
		if(pid==0){output = "That POI could not be found.\n";return output;}
		
		String username = reviewer;
		
		String result = null;
		int useful_count = 0;
		int useful_sum = 0;
		String useful_sum_str = null;
		String useful_count_str = null;
		int fid = 0;
		String sql = "SELECT * FROM Feedback WHERE pid='"+pid+"' AND login='"+username+"'";
		ResultSet rs = null;

		try{
			rs=stmt.executeQuery(sql);
			while (rs.next())
			{
				result = rs.getString("login"); 
				useful_sum_str = rs.getString("useful_sum");
				useful_count_str = rs.getString("useful_count");
				fid = Integer.parseInt(rs.getString("fid"));
			}
			rs.close();
		}
		catch(Exception e)
		{
			output = e.toString();
			return output;
		}
		finally
		{
			try{
				if (rs!=null && !rs.isClosed())
					rs.close();
			}
			catch(Exception e)
			{
				output = e.toString();
				return output;
			}
		}
		
		if(result==null){
			output = "That review could not be found.\n";
			return output;
		}
		if(username.equals(login)){
			output = "You cannot review a feedback done by yourself.\n";
			return output;
		}
		
		if(useful_count_str==null){
			useful_count = 0;
		}
		else{
			useful_count = Integer.parseInt(useful_count_str);
		}
		if(useful_sum_str==null){
			useful_sum = 0;
		}
		else{
			useful_sum = Integer.parseInt(useful_sum_str);
		}

		int score = Integer.parseInt(rating);
		useful_sum += score;
		useful_count++;
		double avg = useful_sum / useful_count;
		
		sql = "UPDATE Feedback SET useful_sum='"+useful_sum+"', useful_count='"+useful_count+"', useful_average='"+avg+"' WHERE fid='"+fid+"'";

		try{
			stmt.executeUpdate(sql);
		}
		catch(Exception e)
		{
			output = e.toString();
			return output;
		}
		
		output = "Success";
		return output;
	}
	
	
	public void recordUsefullness() throws IOException{
		String reply = "";
		System.out.println("Please enter the POI name of the review you want to provide usefulness.");
		while ((reply = in.readLine()) == null && reply.length() == 0);
		int pid = getPID(reply);
		if(pid==0){System.out.println("That POI could not be found.\n");return;}
		
		System.out.println("Please enter the username of the person whom did the review.");
		while ((reply = in.readLine()) == null && reply.length() == 0);
		String username = reply;
		
		String result = null;
		int useful_count = 0;
		int useful_sum = 0;
		String useful_sum_str = null;
		String useful_count_str = null;
		int fid = 0;
		String sql = "SELECT * FROM Feedback WHERE pid='"+pid+"' AND login='"+username+"'";
		ResultSet rs = null;
//		System.out.println("executing "+sql);
		try{
			rs=stmt.executeQuery(sql);
			while (rs.next())
			{
				result = rs.getString("login"); 
				useful_sum_str = rs.getString("useful_sum");
				useful_count_str = rs.getString("useful_count");
				fid = Integer.parseInt(rs.getString("fid"));
			}
			rs.close();
		}
		catch(Exception e)
		{
			System.out.println("cannot execute the query: "+sql);
			System.out.println(e);
			return;
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
		
		if(result==null){
			System.out.println("Sorry, that review could not be found.\n");
			return;
		}
		if(username.equals(login)){
			System.out.println("Sorry, you cannot review a feedback done by yourself.\n");
			return;
		}
		
		if(useful_count_str==null){
			useful_count = 0;
		}
		else{
			useful_count = Integer.parseInt(useful_count_str);
		}
		if(useful_sum_str==null){
			useful_sum = 0;
		}
		else{
			useful_sum = Integer.parseInt(useful_sum_str);
		}
		

		System.out.println("How useful did you find this review from 1 - 3?");
		while ((reply = in.readLine()) == null && reply.length() == 0);
		int score = Integer.parseInt(reply);
		useful_sum += score;
		useful_count++;
		double avg = useful_sum / useful_count;
		
		sql = "UPDATE Feedback SET useful_sum='"+useful_sum+"', useful_count='"+useful_count+"', useful_average='"+avg+"' WHERE fid='"+fid+"'";
//		System.out.println("excuting " + sql);
		try{
			stmt.executeUpdate(sql);
		}
		catch(Exception e)
		{
			System.out.println("You've encountered an error. Goodbye.");
			isLoggedIn = false;
			System.out.println(e);
			return;
		}
		System.out.println("Thanks!\n");
	}
	
	public void trustSomeone() throws IOException{
		String reply = "";
		System.out.println("Please enter the login of the person you trust or not trust.");
		while ((reply = in.readLine()) == null && reply.length() == 0);
		String login2 = reply;
		System.out.println("Do you want to trust them? (y or n)");
		while ((reply = in.readLine()) == null && reply.length() == 0);
		int trust = 0;
		if (reply.equals("y")){
			trust = 1;
		}
		
		String sql = "INSERT INTO Trust (login1, login2, isTrusted) VALUES ('"+login+"', '"+login2+"', '"+trust+"')";
				
		try{
			stmt.executeUpdate(sql);
		}
		catch(Exception e)
		{
			System.out.println("You've encountered an error. Goodbye.");
			isLoggedIn = false;
			System.out.println(e);
			return;
		}
		System.out.println("Success Thanks!\n");
	}
	
	public String trustSomeoneWeb(String login1, String login2, String trust) throws IOException{
		int isTrusted = Integer.parseInt(trust);
		String output = "";
		
		String sql = "INSERT INTO Trust (login1, login2, isTrusted) VALUES ('"+login+"', '"+login2+"', '"+isTrusted+"')"
				+ " ON DUPLICATE KEY UPDATE isTrusted ='"+isTrusted+"'";
		try{
			stmt.executeUpdate(sql);
		}
		catch(Exception e)
		{
			
			output = "You've encountered an error.\n" + e.toString();
			return output;
		}
		output = "Success Thanks!\n";
		return output;
	}
	
	
	public String getMostUsefulFeedbacksWeb(String poi, String num) throws IOException{
		String output = "";
		int pid = getPID(poi);

		if(pid==0){output = "That POI could not be found.\n";return output;}
		int numToDisplay = Integer.parseInt(num);
		
		String sql = "SELECT text, useful_average, login FROM Feedback WHERE pid='"+pid+"' order by useful_average DESC Limit "+numToDisplay+"";
		ArrayList<String> textArr = new ArrayList<String>();
		ArrayList<String> userArr = new ArrayList<String>();
		ArrayList<String> ratingArr = new ArrayList<String>();

		String text = "";
		String user = "";
		String rating = "";
		ResultSet rs = null;
		try{
			rs=stmt.executeQuery(sql);
			while (rs.next())
			{
				text = rs.getString("text");
				user = rs.getString("login");
				rating = rs.getString("useful_average");
				textArr.add(text);
				userArr.add(user);
				ratingArr.add(rating);
			}
			System.out.println();
			rs.close();
		}
		catch(Exception e)
		{
			output = "cannot execute the query: "+e.toString();
			return output;
		}
		finally
		{
			try{
				if (rs!=null && !rs.isClosed())
					rs.close();
			}
			catch(Exception e)
			{
				output = "cannot close resultset. "+e.toString();
				return output;
			}
		}
		output = "Success#";
		for (int i = 0; i< textArr.size(); i++){
//			output += textArr.get(i) + "|" + ratingArr.get(i) + "|" + userArr.get(i) + "|";
			output += userArr.get(i) + "#" + ratingArr.get(i) + "#" + textArr.get(i) + "#";
		}
		return output;
	}
	
	public void getMostUsefulFeedbacks() throws IOException{
		String reply = "";
		System.out.println("Please enter the POI name for which you want top usefulness ratings.");
		while ((reply = in.readLine()) == null && reply.length() == 0);
		String poi = reply;
		int pid = getPID(reply);
		if(pid==0){System.out.println("That POI could not be found.\n");return;}
		
		System.out.println("How many POI's do you want to see? Maybe 5 or 10?");
		while ((reply = in.readLine()) == null && reply.length() == 0);
		int numToDisplay = Integer.parseInt(reply);
		String sql = "SELECT text, useful_average, login FROM Feedback WHERE pid='"+pid+"' order by useful_average DESC Limit "+numToDisplay+"";
		ArrayList<String> textArr = new ArrayList<String>();
		ArrayList<String> userArr = new ArrayList<String>();
		ArrayList<String> ratingArr = new ArrayList<String>();
		String text = "";
		String user = "";
		String rating = "";
		ResultSet rs = null;
//		System.out.println("executing "+sql);
		try{
			rs=stmt.executeQuery(sql);
			System.out.println("The most useful feedbacks were from:");
			while (rs.next())
			{
//				System.out.println("User: "+rs.getString("login") + ", with a usefulness rating of: " + rs.getString("useful_average"));
				text = rs.getString("text");
				user = rs.getString("login");
				rating = rs.getString("useful_average");
				textArr.add(text);
				userArr.add(user);
				ratingArr.add(rating);
			}
			System.out.println();
			rs.close();
		}
		catch(Exception e)
		{
			System.out.println("cannot execute the query: "+sql);
			System.out.println(e);
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
		for (int i = 0; i< textArr.size(); i++){
			System.out.println(textArr.get(i) + " was the most helpful, with a rating of " + ratingArr.get(i) + " from user " + userArr.get(i));
		}
		System.out.println();
	}
	
	public String browsePOIWeb(String url) throws IOException{
		String output = "";

		String[] rep = url.split("&");
		for (int i = 0; i < rep.length; i++){
			rep[i].trim();
		}
		Boolean price = false;
		Boolean city = false;
		Boolean state = false; 
		Boolean keyword = false;
		Boolean catagory = false;
		int priceMin = 0;
		int priceMax = 0;
		String city_str = "";
		String state_str = "";
		String keyword_str = "";
		String catagory_str = "";
		Boolean orderPrice = false;
		Boolean orderFeedback = false;
		Boolean orderTrusted = false;
		int sort;
		
		for(String s : rep){
			if (s.contains("minPrice")){
				price=true;
				String[] split = s.split("=");
				double d = Double.parseDouble(split[1]);
				priceMin = (int) Math.round(d);
			}
			else if (s.contains("maxPrice")){
				price=true;
				String[] split = s.split("=");
				double d = Double.parseDouble(split[1]);
				priceMax = (int) Math.round(d);
			}
			else if (s.contains("city")){
				city=true;
				String[] split = s.split("=");
				city_str = split[1];
			}
			else if (s.contains("state")){
				state=true;
				String[] split = s.split("=");
				state_str = split[1];
			}
			else if (s.contains("keyword")){
				keyword=true;
				String[] split = s.split("=");
				keyword_str = split[1];
			}
			else if (s.contains("catagory")){
				catagory=true;
				String[] split = s.split("=");
				catagory_str = split[1];
			}
			else if (s.contains("sort")){
				String[] split = s.split("=");
				sort = Integer.parseInt(split[1]);
				if (sort==1){
				orderPrice = true;
				}

				else if (sort==2){
					orderFeedback = true;
				}
				else{
					orderTrusted = true;
				}
			}
		}

		
		String sql = "SELECT name FROM POI p JOIN Feedback f ON p.pid = f.pid WHERE ";
		
		if(price){
			sql += "price BETWEEN " +priceMin+ " AND " +priceMax;
		}
		
		if(city){
			if(price) {
				sql += " AND ";
			}
			sql+=("city LIKE '%" + city_str + "%'");
		}
		
		if(state){
			if(price || city) {
				sql += " AND ";
			}
			sql+=("state LIKE '%" + state_str + "%'");
		}		
		
		if(keyword){
			if(price || city || state) {
				sql += " AND ";
			}
			sql+=("name LIKE '%" + keyword_str + "%'");
		}
		
		if(catagory){
			if(price || city || state || keyword) {
				sql += " AND ";
			}
			sql+=("catagory LIKE '%" + catagory_str + "%'");
		}
		
		if(orderPrice){
			sql += " ORDER BY price";
		}
		
		if(orderFeedback){
			sql += " ORDER BY useful_average";
		}		
		
		if(orderTrusted){
			sql += " ORDER BY useful_average";
		}
		
		sql+=(";");
		
		ResultSet rs = null;
		try{
			rs=stmt.executeQuery(sql);
			while (rs.next())
			{
				if(!output.contains(rs.getString("name"))){
					output += rs.getString("name") + "#";
				}
				
			}

			rs.close();
		}
		catch(Exception e)
		{
			output = "cannot execute the query. " + e.toString();
		}
		finally
		{
			try{
				if (rs!=null && !rs.isClosed())
					rs.close();
			}
			catch(Exception e)
			{
				output = "cannot close resultset";
			}
		}
		output = "Success#" + output;
		return output;
	}
	
	
	
	
	
	
	public void browsePOI() throws IOException{
		String reply = "";
		System.out.println("You can browse our system by queries on the following:");
		System.out.println("1. Price range.");
		System.out.println("2. City.");
		System.out.println("3. State.");
		System.out.println("4. POI Name keyword.");
		System.out.println("5. Catagory.");
		System.out.println("Please enter your comma seperated choices (i.e. 1, 2, 5):");
		while ((reply = in.readLine()) == null && reply.length() == 0);
		String[] rep = reply.split(",");
		if(rep.length == 0){System.out.println("Not a valid entry.");return;};
		for (int i = 0; i < rep.length; i++){
			rep[i].trim();
		}
		Boolean price = false;
		Boolean city = false;
		Boolean state = false; 
		Boolean keyword = false;
		Boolean catagory = false;
		
		for(String s : rep){
			if (!s.equals("1")&&!s.equals("2")&&!s.equals("3")&&!s.equals("4")&&!s.equals("5")){
				System.out.println("Not a valid entry.");
				return;
			}
			if (s.equals("1")){price=true;}
			else if (s.equals("2")){city=true;}
			else if (s.equals("3")){state=true;}
			else if (s.equals("4")){keyword=true;}
			else if (s.equals("5")){catagory=true;}
		}
		
		int priceMin = 0;
		int priceMax = 0;
		if (price){
			System.out.println("Please enter integer price minimum.");
			while ((reply = in.readLine()) == null && reply.length() == 0);
			priceMin = Integer.parseInt(reply);
			System.out.println("Please enter integer price maximum.");
			while ((reply = in.readLine()) == null && reply.length() == 0);
			priceMax = Integer.parseInt(reply);
		}
		
		String city_str = "";
		if (city){
			System.out.println("Please enter the city.");
			while ((reply = in.readLine()) == null && reply.length() == 0);
			city_str = reply;
		}
		
		String state_str = "";
		if (state){
			System.out.println("Please enter the state.");
			while ((reply = in.readLine()) == null && reply.length() == 0);
			state_str = reply;
		}
		
		String keyword_str = "";
		if (keyword){
			System.out.println("Please enter the POI name keyword.");
			while ((reply = in.readLine()) == null && reply.length() == 0);
			keyword_str = reply;
		}
		
		String catagory_str = "";
		if (catagory){
			System.out.println("Please enter the catagory.");
			while ((reply = in.readLine()) == null && reply.length() == 0);
			catagory_str = reply;
		}

		System.out.println("Great!\n");
		System.out.println("How would you like to sort your results?:");
		System.out.println("1. Price.");
		System.out.println("2. Average Score of the Feedbacks.");
		System.out.println("3. Average Score of the Trusted Feedbacks.");
		while ((reply = in.readLine()) == null && reply.length() == 0);
		int sort = Integer.parseInt(reply);
		if (sort!=1&&sort!=2&&sort!=3){
			System.out.println("Not a valid entry.");
			return;
		}
		Boolean orderPrice = false;
		Boolean orderFeedback = false;
		Boolean orderTrusted = false;
		if (sort==1){
			orderPrice = true;
		}
		//IF NOT PRICE INSERT JOIN HERE!!!!!!!!!!
		else if (sort==2){
			orderFeedback = true;
		}
		else{
			orderTrusted = true;
		}
		
//		String sql = "Select name from POI WHERE ";
		String sql = "SELECT name FROM POI p JOIN Feedback f ON p.pid = f.pid WHERE ";
		
		if(price){
			sql += "price BETWEEN " +priceMin+ " AND " +priceMax;
		}
		
		if(city){
			if(price) {
				sql += " AND ";
			}
			sql+=("city LIKE '%" + city_str + "%'");
		}
		
		if(state){
			if(price || city) {
				sql += " AND ";
			}
			sql+=("state LIKE '%" + state_str + "%'");
		}		
		
		if(keyword){
			if(price || city || state) {
				sql += " AND ";
			}
			sql+=("name LIKE '%" + keyword_str + "%'");
		}
		
		if(catagory){
			if(price || city || state || keyword) {
				sql += " AND ";
			}
			sql+=("catagory LIKE '%" + catagory_str + "%'");
		}
		
		if(orderPrice){
			sql += " ORDER BY price";
		}
		
		if(orderFeedback){
			sql += " ORDER BY useful_average";
		}		
		
		if(orderTrusted){
			sql += " ORDER BY useful_average";
		}
		
		sql+=(";");
		System.out.println("executing: " + sql);
		ResultSet rs = null;
		try{
			rs=stmt.executeQuery(sql);
			while (rs.next())
			{
				System.out.println(rs.getString("name"));
			}
			System.out.println();
			rs.close();
		}
		catch(Exception e)
		{
			System.out.println("cannot execute the query: "+sql);
			System.out.println(e);
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
	}
	
	
	public String quickSummaryWeb(String numResults) throws IOException{
		String output = "";
		int limit = Integer.parseInt(numResults);
		
		String sql = "SELECT pid, COUNT(*) FROM Visit GROUP BY pid ORDER BY COUNT(*) DESC LIMIT "+limit;
		System.out.println("The "+ limit + " most popular points of interest are:");
		ArrayList<Integer> pidArr = new ArrayList<Integer>();
		int res;
		ResultSet rs = null;
		try{
			rs=stmt.executeQuery(sql);
			while (rs.next())
			{
				res = rs.getInt(1);
				pidArr.add(res);
			}
			rs.close();
		}
		catch(Exception e)
		{
			
			output = "cannot execute the query. "+ e.toString();
			return output;
		}
		finally
		{
			try{
				if (rs!=null && !rs.isClosed())
					rs.close();
			}
			catch(Exception e)
			{
				output = "cannot close resultset";
				return output;
			}
		}
		output += "Popular#";
		for (int i : pidArr){
			output += (getPOIName(i)+"#");
		}
		
		
		System.out.println("\nThe "+ limit + " most highly rated points of interest are:");
		sql = "SELECT pid from Feedback GROUP BY pid ORDER BY avg(score) DESC LIMIT " + limit;
		
		pidArr = new ArrayList<Integer>();
		res = 0;
		rs = null;
		try{
			rs=stmt.executeQuery(sql);
			while (rs.next())
			{
				res = rs.getInt(1);
				pidArr.add(res);
			}
			rs.close();
		}
		catch(Exception e)
		{
			output = "cannot execute the query. "+ e.toString();
			return output;
		}
		finally
		{
			try{
				if (rs!=null && !rs.isClosed())
					rs.close();
			}
			catch(Exception e)
			{
				output = "cannot close resultset";
				return output;
			}
		}
		output += "Rated#";
		for (int i : pidArr){
			output += (getPOIName(i)+"#");
		}
		
		
		
		System.out.println("\nThe "+ limit + " most highly expensive points of interest are:");
		sql = "SELECT pid FROM Visit v JOIN VisEvent e ON v.vid=e.vid GROUP BY pid ORDER BY avg(avg_cost) DESC LIMIT " + limit;
		
		pidArr = new ArrayList<Integer>();
		res = 0;
		rs = null;
		try{
			rs=stmt.executeQuery(sql);
			while (rs.next())
			{
				res = rs.getInt(1);
				pidArr.add(res);
			}
			rs.close();
		}
		catch(Exception e)
		{
			output = "cannot execute the query. "+ e.toString();
			return output;
		}
		finally
		{
			try{
				if (rs!=null && !rs.isClosed())
					rs.close();
			}
			catch(Exception e)
			{
				output = "cannot close resultset";
				return output;
			}
		}
		output += "Expensive#";
		for (int i : pidArr){
			output += (getPOIName(i)+"#");
		}
		output = "Success#" + output;
		return output;
	}
	
	public void quickSummary() throws IOException{
		String reply = "";
		System.out.println("How long would you like the summary list to be?");
		while ((reply = in.readLine()) == null && reply.length() == 0);
		int limit = Integer.parseInt(reply);
		
		System.out.println("The "+ limit + " most popular points of interest are:");
		String sql = "SELECT pid, COUNT(*) FROM Visit GROUP BY pid ORDER BY COUNT(*) DESC LIMIT "+limit;
		ArrayList<Integer> pidArr = new ArrayList<Integer>();
		int res;
		ResultSet rs = null;
		try{
			rs=stmt.executeQuery(sql);
			while (rs.next())
			{
				res = rs.getInt(1);
				pidArr.add(res);
			}
			rs.close();
		}
		catch(Exception e)
		{
			System.out.println("cannot execute the query: "+sql);
			System.out.println(e);
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
		for (int i : pidArr){
			System.out.println(getPOIName(i));
		}
		
		
		System.out.println("\nThe "+ limit + " most highly rated points of interest are:");
		sql = "SELECT pid from Feedback GROUP BY pid ORDER BY avg(score) DESC LIMIT " + limit;
		
		pidArr = new ArrayList<Integer>();
		res = 0;
		rs = null;
		try{
			rs=stmt.executeQuery(sql);
			while (rs.next())
			{
				res = rs.getInt(1);
				pidArr.add(res);
			}
			rs.close();
		}
		catch(Exception e)
		{
			System.out.println("cannot execute the query: "+sql);
			System.out.println(e);
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
		for (int i : pidArr){
			System.out.println(getPOIName(i));
		}
		
		
		
		System.out.println("\nThe "+ limit + " most highly expensive points of interest are:");
		sql = "SELECT pid FROM Visit v JOIN VisEvent e ON v.vid=e.vid GROUP BY pid ORDER BY avg(avg_cost) DESC LIMIT " + limit;
		
		pidArr = new ArrayList<Integer>();
		res = 0;
		rs = null;
		try{
			rs=stmt.executeQuery(sql);
			while (rs.next())
			{
				res = rs.getInt(1);
				pidArr.add(res);
			}
			rs.close();
		}
		catch(Exception e)
		{
			System.out.println("cannot execute the query: "+sql);
			System.out.println(e);
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
		for (int i : pidArr){
			System.out.println(getPOIName(i));
		}
		System.out.println();
	}
	
	
	public String giveAwardsWeb(String limitIn) throws IOException{
		String output = "";

		int limit = Integer.parseInt(limitIn);
		
		System.out.println("The "+ limit + " most useful users are:");
		String sql = "SELECT login FROM Feedback GROUP BY useful_average ORDER BY useful_average DESC LIMIT "+limit;
		ArrayList<String> loginArr = new ArrayList<String>();
		String res;
		ResultSet rs = null;
		try{
			rs=stmt.executeQuery(sql);
			while (rs.next())
			{
				res = rs.getString("login");
				loginArr.add(res);
			}
			rs.close();
		}
		catch(Exception e)
		{
			output = "cannot execute the query. "+ e.toString();
			return output;
		}
		finally
		{
			try{
				if (rs!=null && !rs.isClosed())
					rs.close();
			}
			catch(Exception e)
			{
				output = "cannot close resultset";
				return output;
			}
		}
		output += "Success#";
		for (String s : loginArr){
			output += (s+"#");
		}
		return output;
	}
	
	
	public void giveAwards() throws IOException{
		String reply = "";
		if (isUserAdmin == false){
			System.out.println("You do not have sufficient privledges for that request.\n");
			return;
		}
		
		System.out.println("How many awards are you giving out?");
		while ((reply = in.readLine()) == null && reply.length() == 0);
		int limit = Integer.parseInt(reply);
		
		System.out.println("The "+ limit + " most useful users are:");
		String sql = "SELECT login FROM Feedback GROUP BY useful_average ORDER BY useful_average DESC LIMIT "+limit;
		ArrayList<String> loginArr = new ArrayList<String>();
		String res;
		ResultSet rs = null;
		try{
			rs=stmt.executeQuery(sql);
			while (rs.next())
			{
				res = rs.getString("login");
				loginArr.add(res);
			}
			rs.close();
		}
		catch(Exception e)
		{
			System.out.println("cannot execute the query: "+sql);
			System.out.println(e);
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
		for (String s : loginArr){
			System.out.println(s);
		}
		System.out.println();
	}
	
	public int getWID (String word){
		int id = 0;
//		System.out.println("word='"+word+"'");
		String sql = "select wid from Keywords where word='"+word.trim()+"'";
		ResultSet rs = null;
//		System.out.println("executing "+sql);
		try{
			rs=stmt.executeQuery(sql);
			while (rs.next())
			{
//				System.out.println(rs.getString("wid"));
				id=Integer.parseInt(rs.getString("wid")); 

			}

			rs.close();
		}
		catch(Exception e)
		{
			System.out.println("cannot execute the query: "+sql);
			System.out.println(e);
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
		return id;
	}

	public int getPID (String name){
		int id = 0;
		String sql = "select pid from POI where name='"+name+"'";
		ResultSet rs = null;
//		System.out.println("executing "+sql);
		try{
			rs=stmt.executeQuery(sql);
			while (rs.next())
			{
//				System.out.println("pid: " + rs.getString("pid"));
				id=Integer.parseInt(rs.getString("pid")); 
				if (id==0){
					System.out.println("That establishment could not be found.");
				}
			}

			rs.close();
		}
		catch(Exception e)
		{
			System.out.println("cannot execute the query: "+sql);
			System.out.println(e);
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
		return id;
	}

	public String getPOIName (int pid){
		String name = "";
		String sql = "SELECT name FROM POI WHERE pid='"+pid+"'";
		ResultSet rs = null;
		try{
			rs=stmt.executeQuery(sql);
			while (rs.next())
			{
				name = rs.getString("name");
				if (name.equals("")){
					System.out.println("That establishment could not be found.");
				}
			}
			rs.close();
		}
		catch(Exception e)
		{
			System.out.println("cannot execute the query: "+sql);
			System.out.println(e);
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
		return name;
	}
}
