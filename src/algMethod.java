import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class algMethod {
	public ArrayList<Student> students;
	public ProfessorReader prof;

	public void doStuff() {
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
}
