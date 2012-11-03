import java.util.Comparator;

public class PairComparator implements Comparator<Pair> {

	@Override
	public int compare(Pair arg0, Pair arg1) {
		if ((Double) arg0.getSecond() < (Double) arg1.getSecond()) {
			return -1;
		} else if ((Double) arg0.getSecond() == (Double) arg1.getSecond()) {
			return 0;
		} else
			return 1;
	}

}
