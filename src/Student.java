import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Student {

	private List<Pair> al; // pairs are classname, votes
	private int votesLeft;

	public Student(List<Pair> al) {
		this.al = al;
	}

	// computes priority of student (aka difficulty level) based on this
	// algorithm:
	// priority = (sum over all classes (|log(votes for/avg votes)| total votes
	// to class/class size)) * votes left
	public Double getPriority(Map<String, Double> profMap) {
		double priority = 0;
		for (int i = 0; i < al.size(); i++) {
			votesLeft = votesLeft - (Integer) al.get(i).getSecond();
			priority += Math.abs(Math.log((Double) al.get(i).getSecond()
					/ this.avgVotes()))
					* profMap.get(al.get(i).getFirst());
		}
		priority = priority * votesLeft;
		return priority;
	}

	//returns average votes that the student has left relative to the number of classes 
	//FIX
	private Double avgVotes() {
		int total = 0;
		int numClasses = 0;
		for (int i = 0; i < al.size(); i++) {
			total += (Integer) al.get(i).getSecond();
			if ((Integer) al.get(i).getSecond() != 0){
				numClasses++;
			}
		}
		return (double) (total / numClasses);
	}

	//returns list of classes and priorities
	public List<Pair> getAl() {
		return al;
	}

	//returns the votes the student has remaining
	public int getVotesLeft() {
		return votesLeft;
	}

	//sets the votes the student has remaining, edited by algMethod
	public void setVotesLeft(int n) {
		votesLeft = n;
	}

	// Gets the votes for the given class name
	// votes is either 0 or the votes associated with
	// the first occurrence of this class name in our list
	// of pairs, since we assume that we can't have duplicate
	// preferences.
	public double getVotesFor(String cName) {
		for (Pair p : al) {
			if (((String) p.getFirst()).equals(cName)) {
				return ((Double) p.getSecond()).doubleValue();
			}
		}
		return 0;
	}

}
