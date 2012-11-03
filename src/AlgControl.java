import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AlgControl {
	public ArrayList<Student> students;
	public ProfessorReader prof;

	//assigns each student to as many classes as we can
	//using the student's priority and fitting them into
	//classes as possible
	public void assignClasses() {
		Map<String, Double> profMap = this.computeVPSMap();
		ArrayList<Pair> studprior = new ArrayList<Pair>(); //array of students with their difficulty level
		boolean isChanged = true; //check if we've changed anything
		while (isChanged) {
			isChanged = false;
			for (Student s : students) {
				studprior.add(new Pair(s, s.getPriority(profMap)));
			} //compute priorities for each student

			Collections.sort(studprior, new PairComparator());
			//now studprior has the sequence of dealing with students

			for (Pair s : studprior) {
				Student person = (Student) s.getFirst();
				String className = (String) person.getAl().get(0).getFirst();
				boolean isAdded = prof.addStudentToClass(className);

				while (!isAdded && person.getAl().get(0).getFirst() != null) {
					className = (String) person.getAl().get(0).getFirst();
					isAdded = prof.addStudentToClass(className);
					person.setVotesLeft(person.getVotesLeft()
							- (Integer) person.getAl().get(0).getSecond());
					person.getAl().remove(0);
				} //find a class we can finally add for the student, and add it if we can

				if (isAdded) {
					isChanged = true;
				}
			}
			
			// recompute vpc
			profMap = this.computeVPSMap();
		}
	}

	// returns a map from classes to (votes per available seat)
	private Map<String,Double> computeVPSMap(){
		Map<String,Double> voteMap = new HashMap<String,Double>();
		Set<String> cNames = prof.getNameSet();
		// make a map from classes to total votes
		for(String cName : cNames){
			voteMap.put(cName, getTotalVotes(cName));
		}
		// divide each entry by seats available
		Map<String,Double> seatMap = prof.getSeatMap();
		for(String cName : cNames){
			voteMap.put(cName, voteMap.get(cName)/seatMap.get(cName));
		}
		return voteMap;
	}

	// get the total number of votes (across all students)
	// for the given class
	private Double getTotalVotes(String cName){
		double total = 0;
		for(Student s : students){
			total += s.getVotesFor(cName);
		}
		return total;
	}
}
