import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;

public class automate {

	public static void main(String[] args) {
		Data_base data = new Data_base();
		
		//Parameters:Database URL, username, password.
		Connection c = data.connect("jdbc:mysql://localhost:3306/sakila", "root", "Goombie1!");
		
		//Second parameter: location of file to be imported.
		data.importData(c, "C:/Users/Sam/Downloads/gfa25.csv");
	}
}

class Data_base {
	
	//default constructor.
	public Data_base() {
		//leave empty.
	}
	
	public Connection connect(String db_connect_str, String db_userid, String db_password) {
		Connection c;
		
		//connect to Database.
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			c = DriverManager.getConnection(db_connect_str, db_userid, db_password);
		}catch(Exception e) {
			e.printStackTrace();
			c = null;
		}
		
		return c;
	}
	
	//import file locally from computer, then read from database.
	public void importData(Connection c, String file) {
		
		Statement s;
		//double [] result = null;
		String q;
		//double mean, median, mode, range, iqr, inner_fence, outer_fence;
		
		//import .csv, and other text domains to MySQL.
		try {
			s = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

			//q = "LOAD DATA LOCAL INFILE '" + file + "' INTO TABLE (TABLE NAME) (PARAMETERS);";
			
			//for importing CSV files.
			q = "LOAD DATA LOCAL INFILE '" + file + "' INTO TABLE trialtable character set latin1 FIELDS TERMINATED BY ','";
			
			s.executeUpdate(q);
			
			q = "SELECT * FROM trialtable";
			
			ResultSet result = s.executeQuery(q);
			
			while(result.next()){
				System.out.println("Country:" + result.getString("Country") + "\nCommodity:" + result.getString("Commodity") + "\nItem:" + result.getString("Item") + "\nUnit:" + result.getString("Unit") + "\nAmount:" + result.getString("Amount"));
		    } 
			
		}catch(Exception e) {
			
			e.printStackTrace();
			s = null;
		}
		/*
		//read from MySQL to Java.
		try {
			Statement t = c.createStatement();
			//while(there are columns) 
			t.executeQuery("SELECT (column) FROM (table)");
			ResultSet rs = t.getResultSet();
			int counter = 0;
			
			while(rs.next()) {
				result[counter] = rs.getDouble("(COLUMN NAME)");
				counter++;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//t = null;
		}
		
		sumStat(result);
	
	*/
	}
	
	/*
	//compute summary statistics for the data at hand.
	public void sumStat(double [] result) {
		
		double total = 0;
		double mean = 0;
		double median = 0;
		double max = 0;
		double range = 0;
		double iqr = 0;
		double inner_fence = 0;
		double outer_fence = 0;
		
		//calculate mean.
		for(int i=0; i<result.length; i++) {
			total += result[i];
		}
		
		mean = total/result.length;
		
		//calcuate median.
		for(int i=0; i<result.length; i++) {
			if(result.length % 2 == 0) {
				median = result[result.length/2 + 1];
			}
			
			else {
				median = result[result.length/2];
			}
		}
		
		int count = 1;
		int temp = count;
		double value = 0;
		
		//calculate mode.
		for(int i=0; i<result.length-1; i++) {
			value = result[i];
			temp = 0;
			
			for(int j=1; j<result.length; j++) {
				if(value == result[j]) {
					temp++;
				}
			}
			
			if(temp > count) {
				max = value;
				count = temp;
			}
		}
		
		double minimum = result[0];
		double maximum = result[0];
		
		//calculate range.
		for(int i=1; i<result.length; i++) {
			if(result[i] < minimum) {
				minimum = result[i];
			}
			
			else if(result[i] > maximum) {
				maximum = result[i];
			}
		}
		
		range = maximum - minimum;
		
		double lower;
		double upper;
		
		//calculate iqr.
		if(result.length % 4 != 0) {
			lower = result[result.length/4 + 1];
			upper = result[(result.length/4 * 3) + 1];
		}
		
		else {
			lower = result[result.length/4];
			upper = result[result.length/4 * 3];
		}
		
		iqr = upper - lower;
		inner_fence = 1.5 * iqr;
		outer_fence = 3 * iqr;
	}
	*/
	
	/* 
	 //import file locally from computer, then read from database.
	public static void importData(Connection c, String file, String [][] list1, char [] type1) {
				 
				Statement s;
				//Statement s2;
				String q;
				//String q2;
				int min = 0; 
				//int min2 = 0;
				int max = 0;
				//int max2 = 0;
				
				//import .csv, and other text domains to MySQL.
				try {
					s = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
					//s2 = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

					//q = "LOAD DATA LOCAL INFILE '" + file + "' INTO TABLE (TABLE NAME) (PARAMETERS);";
					
					String entity = list1[0][0];
					//String entity2 = list2[0][0];
					
					if(type1[0] == 's') {
						q = "CREATE TABLE table1(" + entity + " VARCHAR(255))";
						s.executeUpdate(q);
			
					}
					
					else {
						q = "CREATE TABLE table1(" + entity + " DOUBLE)";
						s.executeUpdate(q);
					}
					
					/*
					if(type2[0] == 's') {
						q2 = "CREATE TABLE table2(" + entity2 + " VARCHAR(255))";
						s2.executeUpdate(q2);
			
					}
					
					else {
						q2 = "CREATE TABLE table2(" + entity2 + " DOUBLE)";
						s2.executeUpdate(q2);
					}
					*/
					
					//System.out.print(list1[0].length);
					 
					//for(int i=1; i<list1.length; i++) {
				//		entity = list1[i][0];
						
					//	if(type1[i] == 's') {
						//	q = "ALTER TABLE table1 ADD " + entity + " VARCHAR(255)";
						//	s.executeUpdate(q);
						//}
						
					//	else {
						//	q = "ALTER TABLE table1 ADD " + entity + " DOUBLE";
							//s.executeUpdate(q);
					//	}
					//}
					
					/*
					for(int i=1; i<list2.length; i++) {
						entity = list2[i][0];
						
						if(type2[i] == 's') {
							q2 = "ALTER TABLE table2 ADD " + entity2 + " VARCHAR(255)";
							s2.executeUpdate(q2);
						}
						
						else {
							q2 = "ALTER TABLE table2 ADD " + entity2 + " DOUBLE";
							s2.executeUpdate(q2);
						}
					}
					*/
					
					//for importing CSV files.
				//	q = "LOAD DATA LOCAL INFILE '" + file + "' INTO TABLE table1 character set latin1 FIELDS TERMINATED BY ',' IGNORE 1 LINES";
					//q2 = "LOAD DATA LOCAL INFILE '" + file2 + "' INTO TABLE table2 character set latin1 FIELDS TERMINATED BY ',' IGNORE 1 LINES";
					
				//	s.executeUpdate(q);
					//s2.executeUpdate(q2);
					
			//		System.out.println(list1.length);
					
				//	for(int i=0; i<list1.length; i++) {
						
					//	if(type1[i] == 'd') {
						//	entity = list1[i][0];
			//				q = "SELECT " + entity + " FROM table1";
				//			ResultSet result = s.executeQuery(q);
							
					//		q = "SELECT AVG (" + entity + ") FROM table1";
						//	result = s.executeQuery(q);
							
	//						if(result.next()) {
		//						System.out.println("The average of the column " + entity + " is: " + result.getInt(1));
			//				}
							 
		//					q = "SELECT MAX(" + entity + ") FROM table1";
			//				result = s.executeQuery(q);
							
			//				if(result.next()) {
				//				System.out.println("The max value of the column " + entity + " is: " + result.getInt(1));
					//			max = result.getInt(1);
						//	}
							
			//				q = "SELECT MIN(" + entity + ") FROM table1";
		//					result = s.executeQuery(q);
							
				//			if(result.next()) {
					//			System.out.println("The min value of the column " + entity + " is: " + result.getInt(1));
						//		min = result.getInt(1);
							//}
							
							//int range = max - min;
							//System.out.println("The range of the column " + entity + " is: " + range);
							
							//System.out.println(""); 
					//	}
				//	}
					
					/*
					
					for(int i=0; i<list2.length; i++) {
						
						if(type2[i] == 'd') {
							entity2 = list2[i][0];
							q2 = "SELECT " + entity2 + " FROM table2";
							ResultSet result2 = s2.executeQuery(q2);
							
							q2 = "SELECT AVG (" + entity2 + ") FROM table2";
							result2 = s2.executeQuery(q2);
							
							if(result2.next()) {
								System.out.println("The average of the column " + entity2 + " is: " + result2.getInt(1));
							}
							 
							q2 = "SELECT MAX(" + entity2 + ") FROM table2";
							result2 = s2.executeQuery(q2);
							
							if(result2.next()) {
								System.out.println("The max value of the column " + entity2 + " is: " + result2.getInt(1));
								max2 = result2.getInt(1);
							}
							
							q2 = "SELECT MIN(" + entity2 + ") FROM table2";
							result2 = s2.executeQuery(q2);
							
							if(result2.next()) {
								System.out.println("The min value of the column " + entity2 + " is: " + result2.getInt(1));
								min2 = result2.getInt(1);
							}
							
							int range = max2 - min2;
							System.out.println("The range of the column " + entity2 + " is: " + range);
						}
					} 
					
					*/
					
					//while(result.next()){
				//		System.out.println("Country:" + result.getString("Country") + "\nCommodity:" + result.getString("Commodity") + "\nItem:" + result.getString("Item") + "\nUnit:" + result.getString("Unit") + "\nAmount:" + result.getString("Amount"));
				 //   } 
					
			//	}catch(Exception e) {
					
				//	e.printStackTrace();
					//s = null;
				//}
				/*
				//read from MySQL to Java.
				try {
					Statement t = c.createStatement();
					//while(there are columns) 
					t.executeQuery("SELECT (column) FROM (table)");
					ResultSet rs = t.getResultSet();
					int counter = 0;
					
					while(rs.next()) {
						result[counter] = rs.getDouble("(COLUMN NAME)");
						counter++;
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//t = null;
				}
				
				/*
        		//String response = choicegroup.getSelection().getActionCommand(); 
        		//System.out.println("You selected " + response); 
        		Component[] components = statPanel.getComponents(); 
        		
        		for(int a=0; a<components.length; a++) {
        			System.out.println("Component " + a + "is: " + components[a]);
        		}
        		
        		for(int i=0; i<components.length; i++) { 
        			JCheckBox cb = (JCheckBox) components[i]; 
        			
        			if(cb.isSelected()) {
        				System.out.println("Also selected: " + cb.getText()); 
        				
        				if(cb.getText().equals("Mean")) {
        					System.out.println("I may never make it to this point.");
            				meanchoice = true; 
            			}
            			
            			if(cb.getText().equals("Median")) {
            				medianchoice = true;
            			}
            			
            			if(cb.getText().equals("Mode")) {
            				modechoice = true;
            			}
            			
            			if(cb.getText().equals("Range")) {
            				rangechoice = true;
            			}
            			
            			if(cb.getText().equals("Min")) {
            				minchoice = true;
            			}
            			
            			if(cb.getText().equals("Max")) {
            				maxchoice = true;
            			}
            			
            			if(cb.getText().equals("Interquartile Range")) {
            				iqrchoice = true;
            			}
            			
            			if(cb.getText().equals("Outliers")) {
            				outlierchoice = true;
            			}
            			
            			if(cb.getText().equals("Correlation")) {
            				correlationchoice = true;
            			}
            			
            			if(cb.getText().equals("Standard Deviation")) {
            				sdchoice = true;
            			}
            			
            			if(cb.getText().equals("Variance")) {
            				variancechoice = true;
            			}
        			}
        		} 
        		
        		Data_base d = new Data_base();
    	Connection c = d.connect("jdbc:mysql://localhost:3306/sakila", "root", "Goombie1!");
    	//importData(c, csvFile, list1, type1);
        		*/
			
	//int csv1 = countLines(csvFile);
	//int csv2 = countLines(csvFile2);
	//int csv3 = countLines(csvFile3);
	//int csv4 = countLines(csvFile4);
	//int csv5 = countLines(csvFile5);
	
	//String[][] list1 = csvReader(csvFile, max);
	//String[][] list2 = csvReader(csvFile2, max);
	//String[][] list3 = csvReader(csvFile3, 19);
	
	
	} 
	 
	 

