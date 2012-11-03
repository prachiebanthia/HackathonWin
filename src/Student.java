import java.util.ArrayList;
import java.util.Map;

public class Student {

	public ArrayList<Pair> al;
	
	public Student(ArrayList<Pair> al){
		this.al = al;
	}

	public Double getPriority(Map<String, Double> map) {
		double priority = 0;
		int votes = 50;
		for (int i = 0; i < al.size(); i++) {
			votes = votes - al.get(i).getSecond();
			priority += Math.abs(Math.log((double) al.get(i).getSecond()
					/ this.avgVotes()))
					* map.get(al.get(i).getFirst());
		}
		priority = priority * votes;

		return priority;

	}

	private Double avgVotes() {
		int total = 0;
		for (int i = 0; i < al.size(); i++) {
			total += al.get(i).getSecond();
		}
		return (double) (total / al.size());
	}

}
