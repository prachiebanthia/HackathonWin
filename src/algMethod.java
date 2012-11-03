import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class algMethod {
	public ArrayList<Student> students;
	public ProfessorReader prof;

	public void assignClasses() {
		// compute vpc table?
		Map<String, Integer> profMap = null; // get real map
		ArrayList<Pair> studprior = new ArrayList<Pair>();
		boolean isChanged = true;
		while (isChanged) {
			isChanged = false;
			for (Student s : students) {
				studprior.add(new Pair(s, s.getPriority(profMap)));
			}

			Collections.sort(studprior, new PairComparator());

			for (Pair s : studprior) {
				Student person = (Student) s.getFirst();
				String className = (String) person.getAl().get(0).getFirst();
				boolean isAdded = prof.addStudentToClass(className);
				
				while (!isAdded && person.getAl().get(0).getFirst() != null) {
					className = (String) person.getAl().get(0).getFirst();
					isAdded = prof.addStudentToClass(className);
					person.setVotesLeft(person.getVotesLeft()
							- (Integer) person.getAl().get(0).getSecond());
					person.getAl().removeFirst();
				}

				if (isAdded) {
					isChanged = true;
				}
			}

			// recompute vpc

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
		
		private Double getTotalVotes(String cName){
			double total = 0;
			for(Student s : students){
				total += s.getVotesFor(cName);
			}
			return total;
		}
}
