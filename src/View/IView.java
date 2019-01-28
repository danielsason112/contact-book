package View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public interface IView {
	
	void init();

	void registerListener(ActionListener listener);
	
	void fireActionEvent(ActionEvent e);
	
	String[] getNewContactData();
	
	void showContact(String[] uiData);
	
	void editContact(String[] uiData);
	
	String[] getShowenContactData();
	
	String getFormat();
	
	String getFilePath();
	
	String getSortOp();
	
	String getSortField();
	
	String getSortOrder();
	
	String getShowOrder();
	
	void beginContactsDisplay(String order);
	
	void stopContactDisplay();

}
