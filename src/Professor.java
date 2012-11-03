import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// Simple container for a professor's preference
// A preference is a max # classes,
// and a map<String,Integer> from classes willing
// to teach to max # students in that class
public class Professor {
	private final int maxTaught;
	private Set<String> classesTaught;
	Map<String,Integer> possClasses;
	
	public Professor(int maxTaught){
		this.maxTaught = maxTaught;
		this.classesTaught = new HashSet<String>();
		this.possClasses = new HashMap<String,Integer>();
	}
	
	public int getMaxTaught(){
		return maxTaught;
	}
	
	public boolean addClass(String name, int max){
		if(!possClasses.containsKey(name)){
			possClasses.put(name, max);
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
	
	public boolean reduceAvailalbe(String name){
		if(!tryToTeach(name)){
			return false;
		}
		if(possClasses.containsKey(name)){
			if(possClasses.get(name) > 0){
				possClasses.put(name,possClasses.get(name)-1);
				return true;
			}
		}
		return false;
	}
}
