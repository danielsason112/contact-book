package Contact;
import javafx.scene.paint.Color;

public interface ProjectFinals {
	
	public static final String FORMAT_TXT = "txt";
	public static final String FORMAT_OBJ = "obj.dat";
	public static final String FORMAT_BYTE = "byte.dat";
	public static final String SORT_OP_FIELD = "sort-field";
	public static final String SORT_OP_COUNT = "sort-count";
	public static final String SORT_OP_REVERSE = "reverse";
	public static final String FIRST_NAME_FIELD = "FIRST_NAME_FIELD";
	public static final String LAST_NAME_FIELD = "LAST_NAME_FIELD";
	public static final String PHONE_NUMBER_FIELD = "PHONE_NUMBER_FIELD";
	public static final String SORT_ORDER_ASC = "asc";
	public static final String SORT_ORDER_DESC = "desc";
	public static final String[] FORMATS = { FORMAT_TXT, FORMAT_OBJ, FORMAT_BYTE };
	public static final String[] SORT_OP = { "sort-field", SORT_OP_COUNT, SORT_OP_REVERSE };
	public static final String[] SORT_FIELD = { FIRST_NAME_FIELD, LAST_NAME_FIELD, PHONE_NUMBER_FIELD };
	public static final String[] SORT_ORDER = { SORT_ORDER_ASC, SORT_ORDER_DESC };
	
	public static final String CREATE_NEW_CONTACT_EVENT = "CREATE_NEW_CONTACT_EVENT";
	public static final String MODEL_LIST_UPDATED_EVENT = "MODEL_LIST_UPDATED_EVENT";
	public static final String FIRST_CONTACT_EVENT = "FIRST_CONTACT_EVENT";
	public static final String LAST_CONTACT_EVENT = "LAST_CONTACT_EVENT";
	public static final String NEXT_CONTACT_EVENT = "NEXT_CONTACT_EVENT";
	public static final String PREVIOUS_CONTACT_EVENT = "PREVIOUSE_CONTACT_EVENT";
	public static final String EDIT_CONTACT_EVENT = "EDIT_CONTACT_EVENT";
	public static final String UPDATE_CONTACT_EVENT = "UPDATE_CONTACT_EVENT";
	public static final String EXPORT_CONTACT_EVENT = "EXPORT_CONTACT_EVENT";
	public static final String LOAD_CONTACT_EVENT = "LOAD_CONTACT_EVENT";
	public static final String SORT_EVENT = "SORT_EVENT";
	public static final String SHOW_EVENT = "SHOW_EVENT";
	public static final String NO_SUCH_ELEMENT_EVENT = "NO_SUCH_ELEMENT_EVENT";
	public static final String COLOR_CHANGED_EVENT = "COLOR_CHANGED";
	
	public static final String EXPORT_FAILED_ERROR = "Contact export failed.";
	public final int NUM_OF_DATA_PARMATERS = 3;
	
	public enum DataColor {
		black (Color.BLACK), red (Color.RED), green (Color.GREEN), blue (Color.BLUE);
		
		private final Color color;
		
		DataColor(Color color) {
			this.color = color;
		}
		
		public Color getColorValue() {
			return this.color;
		}
	}

}
