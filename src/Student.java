import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Random;

// text file syntax for a student file
// <unique text identifier>
// <className votes \n>*
public class Student {

	private List<Pair<String,Double>> votesList; // pairs are classname, votes
	private double votesLeft;
	private Set<String> classesBought;
	private String id;
	
	private static Random rGen = new Random();
	
	public Student(List<Pair<String,Double>> vList, String id) {
		this.votesList = vList;
		Collections.sort(votesList, new PairComparator());
		classesBought = new HashSet<String>();
		this.id = id;
		this.votesLeft = 0;
		for(Pair<String,Double> p : votesList){
			this.votesLeft += p.getSecond();
		}
	}

	// whether this student would like to take another class
	public boolean wantsAClass(){
		return !votesList.isEmpty();
	}
	
	// the class this person would most like to take
	// returns null if this person does not want another class
	public String targetClass(){
		if(!this.wantsAClass()){
			return null;
		}else{
			return votesList.get(0).getFirst();
		}
	}
	
	// the highest priority class is not available
	// remove it from the list, but rescale all other votes
	// so we do not penalize this person for class unavailability
	// return true on success
	public boolean loseBestClass(){
		if(votesList.isEmpty()){ // can't lose from an empty list
			return false;
		}
		double lostVotes = (Double) votesList.get(0).getSecond();
		votesList.remove(0);
		if(lostVotes == votesLeft){ // no more classes
			return true;
		}
		double scaleFactor = votesLeft / (votesLeft - lostVotes);
		for(Pair<String,Double> p : votesList){
			p.setSecond(p.getSecond()*scaleFactor);
		}
		return true;
	}
	
	
	// the highest priority class is available
	// so spend votes to "buy" it
	// return true on success
	public boolean buyBestClass(){
		if(votesList.isEmpty()){ // can't buy from an empty list
			return false;
		}else{
			classesBought.add(votesList.get(0).getFirst());
			votesLeft -= votesList.get(0).getSecond();
			votesList.remove(0);
			return true;
		}
	}
	
	// computes priority of student (aka difficulty level) based on this
	// algorithm:
	// priority = (sum over all classes (|log(votes for/avg votes)| total votes
	// to class/class size)) * votes left
	// priority is votesLeft * sum of abs(log(a/b)*c
	// a = votes for this class
	// b = avg votes
	
	public Double getPriority(Map<String, Double> votesPerSeatMap) {
		double priority = 0;
		for (int i = 0; i < votesList.size(); i++) {
			priority += Math.abs(Math.log(votesList.get(i).getSecond()
					/ this.avgVotes()))
					* votesPerSeatMap.get(votesList.get(i).getFirst());
		}
		priority += 2; // smoothing 
		priority = priority * votesLeft;
		priority += rGen.nextDouble() / 100; // break ties
		return priority;
	}

	// returns average votes that the student has left relative to 
	// the number of classes they have given positive votes to
	private double avgVotes() {
		int total = 0;
		int numClasses = 0;
		for (int i = 0; i < votesList.size(); i++) {
			total += (Double) votesList.get(i).getSecond();
			if (votesList.get(i).getSecond() != 0){
				numClasses++;
			}
		}
		return (double) (total / numClasses);
	}

	//returns list of classes and priorities
	public List<Pair<String,Double>> getAl() {
		return votesList;
	}

	//returns the votes the student has remaining
	public double getVotesLeft() {
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
		for (Pair<String,Double> p : votesList) {
			if (((String) p.getFirst()).equals(cName)) {
				return ((Double) p.getSecond()).doubleValue();
			}
		}
		return 0;
	}
	
	public void writeResult(PrintWriter out){
		out.println(id + ": " + classesBought);
	}

}
