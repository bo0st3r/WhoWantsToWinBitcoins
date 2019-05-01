package utilities;

import java.util.Comparator;

public class IntegerComparator implements Comparator<Integer> {

	@Override
	public int compare(Integer arg0, Integer arg1) {
		return arg0 - arg1;
	}
}
