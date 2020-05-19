//============================================================================
// Name        : testjava.java
// Author      : Professor St. Clair provided base code
// Author2	   : modified code with additions by Francisco J Segarra Jr
// Version     :
// Copyright   : Your copyright notice
// Description : prompt user to pick which query they want to run
//============================================================================

package testJav;
import java.sql.*;
import java.util.*;
public class testjav 
{
	// method for query 1
	public static String Query1() 
	{
		String query = "SELECT DISTINCT r.rid, rfname, rlname"
				+ " from realtor r, client c, buy b" 
				+ " where c.cid = b.bcid and r.rid = c.rid"
				+ " AND c.rid not in"
				+ " (select c.rid"
				+ " from client c, sell s"
				+ " where c.cid = s.scid);";
		return query;
	} // end method
	// method for query 2
	public static String Query2(String id) 
	{
		String query = "select pid, listing_price, street, city, state, zip, acreage"
				+ " from pland l, bland b, property p" 
				+ " where p.pid = l.plid and bcid = " + id//buyer 20003 is the only one that will have an output
				+ " and lmin <= acreage and lmax >= acreage;";
		return query;
	} // end method
	// method for query 6
	public static String Query6() 
	{
		String query = "select r.rid, rfname, rlname"
				+ " from transaction t, client c, realtor r" 
				+ " where t.bcid = c.cid and c.rid = r.rid"
				+ " group by rid having count(*) > 3;";
		return query;
	} // end method
	// method for query 7
	public static String Query7() 
	{
		String query = "select *"
				+ " from"
				+ " (select t.pid, MAX(selling_price - listing_price) as profit" 
				+ " from transaction t, property p"
				+ " WHERE t.pid = p.pid and selling_price > listing_price) as sub;";
		return query;
	} // end method
	// method for query 10
	public static String Query10() 
	{
		String query = "select rid, rfname, rlname, profit * .03"
				+ " from"
				+ " (select r.rid, rfname, rlname, MAX(selling_price) as profit" 
				+ " from transaction t, client c, realtor r"
				+ " where c.rid = r.rid and t.bcid = c.cid) as sub;";
				
		return query;
	} // end method

	public static void main(String[] args) 
	{
		
		// these output statements are the opening menu that lets the user know what each query does
		System.out.println("\t\t\t\t\tTHE MENU");
		System.out.println("1)\tShow the realtor id and realtor name for all realtors that are working\n"
				+ "\twith only buying clients.");
		System.out.println("2)\tFor a particular land buying client, show all properties that match the\n"
				+ "\tclient's interests. Show the property id, listing price, address, and acreage.");
		System.out.println("6)\tShow the realtor id and name for any realtor involved in more than three\n"
				+ "\ttransaction as the buying realtor.");
		System.out.println("7)\tShow the property id for the property that gained the most value\n"
				+ "\t(selling price exceeded listing price). ");
		System.out.println("10\tShow the realtor that made the most money as a buying realtor involved\n"
				+ "\tin a transaction. Realtor's earn 3% of the selling price, Although selling price is\n"
				+ "\ta stored value, the amount earned by the realtor is derived.");
		System.out.println();
			
		try 
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
		} // end try
		catch (Exception e) 
		{
			System.out.println("Can't load driver");
		} // end catch
		try 
		{
			System.out.println("Starting Connection........");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Segarra_Realty_Emporium", "fjsegarra",
					"GrunT12!!");
			// "jdbc:oracle:thin:@localhost:1521:xe", "system", "oraclexe");

			System.out.println("Connection Established");
			// the loop that continues to prompt user until exit case is entered
			while (true) 
			{
				String answer = "";
				String query = "";
				Scanner scan = new Scanner(System.in);
				System.out.println("Which number query would you like to run, press 0 to exit: ");
				answer = scan.nextLine();
				// exit case
				if (answer.equals("0"))
					break;
				// Print statement for query 1
				if (answer.equals("1"))
				{
					query = Query1();
					PreparedStatement stmt = con.prepareStatement(query);
					ResultSet result = stmt.executeQuery();
					System.out.println("Processing Results");
					System.out.println("--------------------------------------");
					System.out.println(" Rid\t    |First Name\t  |Last Name");
					System.out.println("--------------------------------------");
					while (result.next()) 
					{
						
						System.out.printf(" %-10s |%-12s |%-12s\n", result.getString("rid") ,result.getString("rfname"), result.getString("rlname"));
						System.out.println("--------------------------------------");
						System.out.println("\n");
					} // end while
				} // end if
				// Print statement for query 2 & prompts user for buying client ID
				if (answer.equals("2")) 
				{
					// scanner
					Scanner kb = new Scanner(System.in);
					System.out.println("Which buying clients ID do you want to look up?: ");
					String id = kb.nextLine();
					// passing id as parameters
					query = Query2(id);
					PreparedStatement stmt = con.prepareStatement(query);
					ResultSet result = stmt.executeQuery();
					System.out.println("Processing Results");
					System.out.println("---------------------------------------------------------------------------------------");
					System.out.println(" Pid    |listing price |Street\t\t\t|City\t     |State |Zip     |acreage");
					System.out.println("---------------------------------------------------------------------------------------");
					while (result.next()) 
					{
						System.out.printf(" %-7s|%-12s  |%-22s  |%-12s|%-3s   |%-7s |%-10s\n", result.getString("p.pid") ,result.getString("listing_price"), result.getString("street"), result.getString("city"), result.getString("state"), result.getString("zip"), result.getString("acreage"));
						System.out.println("---------------------------------------------------------------------------------------");
						System.out.println();
					} // end while
				} // end if
				// Print statement for query 6
				if (answer.equals("6"))
				{
					query = Query6();
					PreparedStatement stmt = con.prepareStatement(query);
					ResultSet result = stmt.executeQuery();
					System.out.println("Processing Results");
					System.out.println("--------------------------------------");
					System.out.println(" Rid\t    |First Name\t  |Last Name");
					System.out.println("--------------------------------------");
					while (result.next()) 
					{
						System.out.printf(" %-10s |%-12s |%-12s\n", result.getString("rid") ,result.getString("rfname"), result.getString("rlname"));
						System.out.println("--------------------------------------");
						System.out.println("\n");
					} // end while
				} // end if
				// Print statement for query 7
				if (answer.equals("7"))
				{
					query = Query7();
					PreparedStatement stmt = con.prepareStatement(query);
					ResultSet result = stmt.executeQuery();
					System.out.println("Processing Results");
					System.out.println("---------------------");
					System.out.println(" Pid\t   |Profit ");
					System.out.println("---------------------");
					while (result.next()) 
					{
						System.out.printf(" %-10s|%-12s\n", result.getString("pid") ,result.getString("profit"));
						System.out.println("---------------------");
					/*	System.out.println("\npid\t " + result.getString("pid"));
						System.out.println("profit\t " + result.getString("profit"));*/
						System.out.println();
					} // end while
				} // end if
				// Print statement for query 10
				if (answer.equals("10"))
				{
					query = Query10();
					PreparedStatement stmt = con.prepareStatement(query);
					ResultSet result = stmt.executeQuery();
					System.out.println("Processing Results");
					System.out.println("-----------------------------------------------------");
					System.out.println(" Rid\t    |First Name\t  |Last Name\t|Commission");
					System.out.println("-----------------------------------------------------");
					while (result.next()) 
					{
						System.out.printf(" %-10s |%-12s |%-12s |%-12s\n" , result.getString("rid") ,result.getString("rfname"), result.getString("rlname"), result.getString("profit * .03"));
						System.out.println("-----------------------------------------------------");
						System.out.println();
					} // end while
				} // end if 
			} // end BIG while loop
		} // end try block
		catch (SQLException e) 
		{
			System.out.println(e.getMessage() + " Can't connect to database");
			while (e != null) 
			{
				System.out.println("Message: " + e.getMessage());
				e = e.getNextException();
			} // end while
		} // end catch 
		catch (Exception e) 
		{
			System.out.println("Other Error");
		} // end catch
	} // main
} // end class
