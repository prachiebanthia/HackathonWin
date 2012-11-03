import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

// reads a list of professors from the given file
// then maintains a map of classes to seats available
public class ProfessorReader {
	
	List<Professor> profs;
	Set<String> cNameSet;
	
	public ProfessorReader(String inFileName) throws FileNotFoundException{
		this.profs = new ArrayList<Professor>();
		this.cNameSet = new HashSet<String>();
		Scanner scan = new Scanner(new File(inFileName));
		while(scan.hasNextLine()){
			Professor nextProf = readProf(scan.nextLine());
			profs.add(nextProf);
		}
		scan.close();
	}
	
	
	// intentionally crash with a given error message
	public static void error(String msg){
		System.err.println(msg);
		System.exit(0);
	}
	
	// private method reads a string of input
	// and returns the professor corresponding to that string
	private Professor readProf(String info){
		Scanner scan = new Scanner(info);
		int maxTaught = scan.nextInt();
		Professor newProf = new Professor(maxTaught);
		while(scan.hasNext()){
			String cName = scan.next();
			if(cNameSet.contains(cName)){
				error("Duplicate class name: " + cName);
			}else{
				cNameSet.add(cName);
			}
			int cMax = scan.nextInt();
			newProf.addClass(cName, cMax);
		}
		scan.close();
		return newProf;
	}
}
