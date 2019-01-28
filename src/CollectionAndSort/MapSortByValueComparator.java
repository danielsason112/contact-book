package CollectionAndSort;
import java.util.Comparator;
import java.util.Map.Entry;

import Contact.IContact;

public class MapSortByValueComparator implements Comparator<Entry<IContact, Integer>> {

	@Override
	public int compare(Entry<IContact, Integer> o1, Entry<IContact, Integer> o2) {
		int val =  o1.getValue().compareTo(o2.getValue());
		return val == 0 ? 1 : val;
	}

}
