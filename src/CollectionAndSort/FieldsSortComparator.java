package CollectionAndSort;
import java.util.Comparator;


import Contact.IContact;
import Contact.ProjectFinals;

public class FieldsSortComparator implements ProjectFinals, Comparator<IContact> {	
	
	private String fieldType;

	public FieldsSortComparator(String fieldType) {
		super();
		this.fieldType = fieldType;
	}

	@Override
	public int compare(IContact o1, IContact o2) {
		if (this.fieldType.equals(FIRST_NAME_FIELD))
			return o1.getUiData()[1].compareTo(o2.getUiData()[1]);
		if (this.fieldType.equals(LAST_NAME_FIELD))
			return o1.getUiData()[2].compareTo(o2.getUiData()[2]);
		return o1.getUiData()[3].compareTo(o2.getUiData()[3]); //PHONE_NUMBER
	}

}
