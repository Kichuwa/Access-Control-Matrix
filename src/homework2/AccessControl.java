package homework2;

import java.io.FileReader; 
import java.util.Arrays;
import java.util.Scanner;

	/*
	 * Darren Soulier
	 * APC420
	 * 6/7/2021
	 */

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
	
	static int subject, object;
	static String operation;

	//for reviewing matrices
	public static void print2D(String[][] matrix2)
    {
        // Loop through all rows
        for (String[] row : matrix2)
 
            // converting each row as string
            // and then printing in a separate line
            System.out.println(Arrays.toString(row));
    }
	
	//For finding index of subject
	public int findSubject(String subject){
		int i = 0;
		while(subjects[i] != null && !subjects[i].equals(subject)) {
			i++;
		}
		if(subject == null) 
			return -1;
		else
			return i;
	}
	//For finding index of object
	public int findObject(String object){
		int i = 0;
		while(objects[i] != null && !objects[i].equals(object)) {
			i++;
		}
		if(object == null) 
			return -1;
		else
			return i;
	}
	

	// load access control matrix from a file by the name of "fileName"
	void load(String fileName) {
		try {
			Scanner in = new Scanner(new FileReader(fileName));
			
			subjects = in.nextLine().split(",\\s*");
			objects = in.nextLine().split(",\\s*");
			matrix = new String[subjects.length][objects.length];
			
			int i = 0;
			while(i < subjects.length && in.hasNextLine()) {
				matrix[i] = in.nextLine().split(",\\s*");
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
	       StringBuilder matrixLayout = new StringBuilder();
	       matrixLayout.append("\t");
	       
	       for (int i = 0; i < objects.length; i++) {
	           String object = objects[i]; 
	          
	           matrixLayout.append(object + "  "); //add objects to matrix
	       }
	      
	       matrixLayout.append("\n");
	      
	       for (int i = 0; i < subjects.length; i++) {
	           String subj = subjects[i]; 
	           
	           matrixLayout.append(subj +"\t"); //add subjects to matrix 
	           for(int k = 0; k < matrix[i].length; k++) {
        		   matrixLayout.append(matrix[i][k] + "\t    ");
	           }
	                      
           matrixLayout.append("\n");   
           
	       }
           
	       //Test Zone
		//print2D(matrix);
	       
	   return matrixLayout.toString();
	   }
	
	
	boolean check(String subj, String op, String obj) {
		try {
			int subject = findSubject(subj);
			int object = findObject(obj);
			AccessControl.operation = op;
		
			if(matrix[subject][object].contains("r") 
				&& AccessControl.operation.contains("r")) {
				return true;
			} else if	(matrix[subject][object].contains("w") 
						&& AccessControl.operation.contains("w")) {
				return true;
			}
		}
		catch(ArrayIndexOutOfBoundsException e) {
			return false;
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
