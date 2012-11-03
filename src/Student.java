import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Student {

	private LinkedList<Pair> al;
	private int votesLeft;
	
	public Student(LinkedList<Pair> al){
		this.al = al;
	}

	public Double getPriority(Map<String, Integer> profMap) {
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

	private Double avgVotes() {
		int total = 0;
		for (int i = 0; i < al.size(); i++) {
			total += (Integer) al.get(i).getSecond();
		}
		return (double) (total / al.size());
	}

	public LinkedList<Pair> getAl(){
		return al;
	}
	
	public int getVotesLeft(){
		return votesLeft;
	}
	
	public void setVotesLeft(int n){
		votesLeft = n;
	}
}
