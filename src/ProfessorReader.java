import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

// reads a list of professors from the given file
// then maintains a map of classes to seats available
public class ProfessorReader {
	
	List<Professor> profs;
	Set<String> cNameSet;
	Map<String,Professor> teachMap;
	
	public ProfessorReader(String inFileName) throws FileNotFoundException{
		this.profs = new ArrayList<Professor>();
		this.cNameSet = new HashSet<String>();
		this.teachMap = new HashMap<String,Professor>();
		Scanner scan = new Scanner(new File(inFileName));
		while(scan.hasNextLine()){
			String nextLine = scan.nextLine();
			if(nextLine.charAt(0) == '#'){
				
			}
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
				teachMap.put(cName, newProf);
			}
			int cMax = scan.nextInt();
			newProf.addClass(cName, cMax);
		}
		scan.close();
		return newProf;
	}
	
	// returns a map from class names to remaining seats
	// might be slow; call sparingly
	public Map<String,Double> getSeatMap(){
		Map<String,Double> seatMap = new HashMap<String,Double>();
		for(Professor p : profs){
			seatMap.putAll(p.getSeatMap());
		}
		return seatMap;
	}
	
	public boolean addStudentToClass(String cName){
		return teachMap.get(cName).reduceAvailalbe(cName);
	}
}
