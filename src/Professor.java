import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// Simple container for a professor's preference
// A preference is a max # classes,
// and a map<String,Integer> from classes willing
// to teach to max # students in that class
//
// text file syntax for a professor:
// <unique string identifier> <max classes> <class spots>*
public class Professor {
	private final int maxTaught;
	private Set<String> classesTaught;
	Map<String,Integer> seatMap;
	private String id;
	
	public Professor(int maxTaught, String id){
		this.maxTaught = maxTaught;
		this.classesTaught = new HashSet<String>();
		this.seatMap = new HashMap<String,Integer>();
		this.id = id;
	}
	
	public int getMaxTaught(){
		return maxTaught;
	}
	
	public boolean addClass(String name, int max){
		if(!seatMap.containsKey(name)){
			seatMap.put(name, max);
			return true;
		}
		return false;
	}
	
	// returns true if we can teach this class
	// if we have room and we're not already teaching it
	// add it to the set of taught classes
	private boolean tryToTeach(String name){
		if(classesTaught.contains(name)){
			return true;
		}else{
			if(classesTaught.size() < maxTaught){
				classesTaught.add(name);
				return true;
			}
		}
		return false;
	}
	
	public boolean reduceAvailable(String name){
		if(!tryToTeach(name)){
			return false;
		}
		if(seatMap.containsKey(name)){
			if(seatMap.get(name) > 0){
				seatMap.put(name,seatMap.get(name)-1);
				return true;
			}
		}
		return false;
	}
	
	public Map<String,Double> getSeatMap(){
		Map<String,Double> sMapValid = new HashMap<String,Double>();
		if(classesTaught.size() >= maxTaught){
			for(String cName : classesTaught){
				sMapValid.put(cName, new Double(seatMap.get(cName)));
			}
		}else{
			for(String cName : seatMap.keySet()){
				sMapValid.put(cName, new Double(seatMap.get(cName)));
			}
		}
		return sMapValid;
	}
	
	public String toString(){
		String s = "Prof with max classes " + maxTaught + " and currently teaching ";
		s += classesTaught.toString();
		s += " and with seatMap ";
		s += seatMap.toString();
		s += "\n";
		return s;
	}
	
	public void writeResult(PrintWriter p){
		p.println(id + ": " + classesTaught);
	}
}
