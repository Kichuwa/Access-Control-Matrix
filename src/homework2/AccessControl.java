package homework2;

import java.io.FileReader; 
import java.util.Arrays;
import java.util.Scanner;

/*
 * Load an access control matrix from a file
 * First line: subjects 
 * Second line: objects
 * Subsequent lines: access control matrix (comma separated)
 * e.g 
 * 
 *    alice, bob, carol, eve
 *    top-secret, secret, public
 *    rw,                
 *    r,          rw,    rw
 *    ,           r,     rw
 *    ,           ,      r   
 */
public class AccessControl {
	// Define any fields or methods as necessary.
	String[] subjects, objects; 
	static String[][] matrix; // 'r', 'w', or 'rw'
	

	// load access control matrix from a file by the name of "fileName"
	void load(String fileName) {
		try {
			Scanner in = new Scanner(new FileReader(fileName));
			
			subjects = in.nextLine().split(",\\s");
			objects = in.nextLine().split(",\\s");
			matrix = new String[subjects.length][objects.length];
			
			int i = 0;
			while(i < subjects.length && in.hasNextLine()) {
				matrix[i] = in.nextLine().split(",\\s");
				i += 1;
				
			}
			in.close();
			
		}
		catch(Exception e){
			System.out.println("Cannot open file: '" +fileName +"'");
		}
	}

	
	public String toString() {
	       //format matrix
	       String lineSeparator = System.lineSeparator(); //return matrix string in separate lines
	       StringBuilder matrixLayout = new StringBuilder();
	       matrixLayout.append("\t");
	       
	       for (int i = 0; i < objects.length; i++) {
	           String object = objects[i]; 
	          
	           matrixLayout.append(object + " "); //add objects to matrix
	       }
	      
	       matrixLayout.append("\n");
	      
	       for (int i = 0; i < subjects.length; i++) {
	           String subj = subjects[i]; 
	           
	          
	           matrixLayout.append(subj +"\t"); //add subjects to matrix 
	           //Test Zone
	           
	           for(int j = 0; j < matrix.length; j++) {
	        	   for(int k = 0; k < matrix[j].length; k++) {
	        		   String matr = matrix[j][k];
	        		   matrixLayout.append(matr);
		           }
	           }
	           matrixLayout.append("\n");
	           
	       }
	       for (String[] row : matrix) { //break matrix array into rows  
	       matrixLayout.append(Arrays.toString(row).indent(8)).append(lineSeparator);
	       }
	   return matrixLayout.toString();
	   }
	
	
	boolean check(String subj, String op, String obj) {
		// TODO
		boolean subjCheck = false, opCheck = false, objCheck = false;
		int subjFound, objectFound;
		
		for(int i = 0; i < subjects.length; i++) {
			if(subjects[i] == subj) {
				subjCheck = true;
				
			}
		}
		
		for(int i = 0; i < objects.length; i++) {
			if(objects[i] == obj) {
				objCheck = true;
			}
		}
		
		for(int row = 0; row < matrix.length; row++) {
			for(int col = 0; col < matrix[row].length; col++) {
				if(matrix[row][col] == op) {
					opCheck = true;
				}
			}
		}
		
		if(subjCheck == true|| opCheck == true == objCheck == true) {
			return true;
		}
		
		return false;
	}

	// Driver method
	public static void main(String[] args) {
		AccessControl ac = new AccessControl();
		ac.load("ac_matrix");
		System.out.println(ac);

		Scanner in = new Scanner(System.in);

		System.out.println("Enter your command (e.g. alice r secret):");
		String cmd = in.nextLine().trim(); 

		while(!cmd.equals("")) {
			String[] triple = cmd.split(" ");
			if(triple.length != 3) {
				System.out.println("Illegal command. Try again");
			}
			else {
				String subj = triple[0], op = triple[1], obj = triple[2];
				try {
					if(ac.check(subj, op, obj)) {
						System.out.printf("%s is allowed to perform %s operation on %s\n", subj, op, obj);
					}
					else {
						System.out.printf("%s is NOT allowed to perform %s operation on %s\n", subj, op, obj);
					}
				} catch(Exception e) { System.out.println(e); }
			}
			System.out.println("\nEnter your command");
			cmd = in.nextLine().trim();
		}

		in.close();
	}
}
