import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

public class algMethod {
	public ArrayList<Student> students;
	public ProfessorReader prof;

	public void doStuff(){
		// compute vpc table?
		Map<String, Integer> profMap = null; //get real map
		ArrayList<Pair> studprior = new ArrayList<Pair>();
		for (Student s: students){
			studprior.add(new Pair(s, s.getPriority(profMap)));
		}

		Collections.sort(studprior, new PairComparator());

		for (Pair s : studprior){
			Student person = (Student) s.getFirst();
			person.getAl().get(0);

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
