import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AlgControl {
	public StudentReader students;
	public ProfessorReader profs;

	// create an algorithm control structure
	public AlgControl(StudentReader students, ProfessorReader prof){
		this.profs = prof;
		this.students = students;
	}
	
	
	//assigns each student to as many classes as we can
	//using the student's priority and fitting them into
	//classes as possible
	public void assignClasses() {
		Map<String, Double> profMap; // map of class to votes per seat
		List<Pair<Student,Double>> studentPriorities;  //array of students with their priority level
		boolean isChanged = true; //check if we've changed anything
		while (isChanged) {
			profMap = this.computeVPSMap();
			isChanged = false;
			//compute priorities for each student
			studentPriorities = new ArrayList<Pair<Student,Double>>();
			for (Student s : students.getStudentList()) {
				studentPriorities.add(new Pair<Student,Double>(s, s.getPriority(profMap)));
			} 
			Collections.sort(studentPriorities, new PairComparator());
			//now studentPriorities is ordered by priority

			for (Pair<Student,Double> s : studentPriorities) {
				Student person = s.getFirst();
				if(!person.wantsAClass()){
					continue;
				}
				boolean isAdded = false;
				//find a class we can finally add for the student, and add it if we can
				while (!isAdded && person.wantsAClass()) {
					String className = person.targetClass();
					boolean gotClass = profs.addStudentToClass(className);
					if(gotClass){
						person.buyBestClass();
						isAdded = true;
					}else{
						person.loseBestClass();
					}
				} 
				if (isAdded) { // if we added a class, the list was changed on this run through
					isChanged = true;
				}
			}
		}
	}

	// returns a map from classes to (votes per available seat)
	private Map<String,Double> computeVPSMap(){
		Map<String,Double> voteMap = new HashMap<String,Double>();
		Set<String> cNames = profs.getNameSet();
		// make a map from classes to total votes
		for(String cName : cNames){
			voteMap.put(cName, students.getTotalVotes(cName));
		}
		// divide each entry by seats available
		Map<String,Double> seatMap = profs.getSeatMap();
		for(String cName : cNames){
			voteMap.put(cName, voteMap.get(cName)/seatMap.get(cName));
		}
		return voteMap;
	}

	
	// intentionally crash with a given error message
	public static void error(String msg){
		System.err.println(msg);
		System.exit(0);
	}
	
	public static void main(String[] args){
		if(args.length != 4){
			error("Usage: java AlgControl <teacherFile> <studentDirectory> <teacherOutFile> <studentOutFile>");
		}
		try{
			ProfessorReader prof = new ProfessorReader(args[0]);
			StudentReader students = new StudentReader(args[1]);
			AlgControl ac = new AlgControl(students, prof);
			ac.assignClasses();
			prof.writeResult(args[2]);
			students.writeResult(args[3]);
		}catch(IOException e){
			error("IO Exception; exiting.");
		}
	}
	
}
