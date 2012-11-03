import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


public class StudentReader {

	private List<Student> students;
	private Set<String> idSeen;
	
	
	public StudentReader(String dirName) throws FileNotFoundException{
		this.students = new ArrayList<Student>();
		this.idSeen = new HashSet<String>();
		File studentDir = new File(dirName);
		if(!studentDir.isDirectory()){
			AlgControl.error("Illegal student file directory argument");
		}
		File[] studentFiles = studentDir.listFiles();
		// for every file in the directory, parse and add a student
		for(File sf : studentFiles){
			Scanner scan = new Scanner(sf);
			students.add(parseFile(scan));
			scan.close();
		}
	}
	
	public List<Student> getStudentList(){
		return students;
	}
	
	// get the total number of votes (across all students)
	// for the given class
	public double getTotalVotes(String cName){
		double total = 0;
		for(Student s : students){
			total += s.getVotesFor(cName);
		}
		return total;
	}
	
	private Student parseFile(Scanner scan){
		String id = scan.next();
		if(idSeen.contains(id)){
			AlgControl.error("Duplicate student id: " + id);
		}else{
			idSeen.add(id);
		}
		List<Pair<String,Double>> votesList = new ArrayList<Pair<String,Double>>();
		Set<String> cNameSet = new HashSet<String>();
		while(scan.hasNextLine()){
			String cName = scan.next();
			if(cNameSet.contains(cName)){
				AlgControl.error("Duplicate class name in student " + id);
			}else{
				cNameSet.add(cName);
			}
			double votes = scan.nextDouble();
			votesList.add(new Pair<String,Double>(cName,votes));
		}
		return new Student(votesList,id);
	}
	
}
