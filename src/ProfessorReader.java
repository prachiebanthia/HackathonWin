import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
	Set<String> profIdSet;
	
	public ProfessorReader(String inFileName) throws FileNotFoundException{
		this.profs = new ArrayList<Professor>();
		this.cNameSet = new HashSet<String>();
		this.teachMap = new HashMap<String,Professor>();
		this.profIdSet = new HashSet<String>();
		Scanner scan = new Scanner(new File(inFileName));
		while(scan.hasNextLine()){
			String nextLine = scan.nextLine();
			if(nextLine.charAt(0) == '#'){
				continue;
			}
			Professor nextProf = readProf(nextLine);
			profs.add(nextProf);
		}
		scan.close();
	}
	
	// private method reads a string of input
	// and returns the professor corresponding to that string
	private Professor readProf(String info){
		Scanner scan = new Scanner(info);
		String id = scan.next();
		if(profIdSet.contains(id)){
			AlgControl.error("Duplicate professor id: " + id);
		}else{
			profIdSet.add(id);
		}
		int maxTaught = scan.nextInt();
		Professor newProf = new Professor(maxTaught, id);
		while(scan.hasNext()){
			String cName = scan.next();
			if(cNameSet.contains(cName)){
				AlgControl.error("Duplicate class name: " + cName);
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
		if(!teachMap.containsKey(cName)){
			return false;
		}
		return teachMap.get(cName).reduceAvailable(cName);
	}
	
	public Set<String> getNameSet(){
		return cNameSet;
	}
	
	public void writeResult(String outFileName) throws IOException{
		File outFile = new File(outFileName);
		PrintWriter out = new PrintWriter(new FileWriter(outFile));
		out.println("Professor taught classes:");
		for(Professor p : profs){
			p.writeResult(out);
		}
		out.close();
	}
	
	// only used for testing. should go away later
	/*
	public static void main(String[] args){
		try {
			ProfessorReader pr = new ProfessorReader("ProfTest.txt");
			System.out.println(pr.getSeatMap());
		} catch (FileNotFoundException e) {
			System.err.println("FNF exception :(");
		}
	}
	*/
}
