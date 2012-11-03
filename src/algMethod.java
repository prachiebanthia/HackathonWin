import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;


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
		person.getAl();
	}
	}
	
	
	
}
