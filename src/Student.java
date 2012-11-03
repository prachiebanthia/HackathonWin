import java.util.ArrayList;
import java.util.Map;

public class Student {

	private ArrayList<Pair> al;
	
	public Student(ArrayList<Pair> al){
		this.al = al;
	}

	public Double getPriority(Map<String, Integer> profMap) {
		double priority = 0;
		int votes = 50;
		for (int i = 0; i < al.size(); i++) {
			votes = votes - (Integer) al.get(i).getSecond();
			priority += Math.abs(Math.log((Double) al.get(i).getSecond()
					/ this.avgVotes()))
					* profMap.get(al.get(i).getFirst());
		}
		priority = priority * votes;

		return priority;

	}

	private Double avgVotes() {
		int total = 0;
		for (int i = 0; i < al.size(); i++) {
			total += (Integer) al.get(i).getSecond();
		}
		return (double) (total / al.size());
	}

	public ArrayList<Pair> getAl(){
		return al;
	}
}
