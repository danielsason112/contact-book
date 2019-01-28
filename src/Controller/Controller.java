package Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import Contact.ContactsManager;
import Contact.ProjectFinals;
import View.IView;
import View.ViewJFX;

public class Controller implements ActionListener, ProjectFinals {

	private ContactsManager cm;
	private ArrayList<IView> views;

	public Controller(ContactsManager cm) {
		this.cm = cm;
		this.cm.registerListener(this);
		this.views = new ArrayList<IView>();
	}

	public void addView(IView view) {
		this.views.add(view);
		view.registerListener(this);
		view.init();
		actionPerformed(new ActionEvent(this, -1, FIRST_CONTACT_EVENT));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		IView view = null;
		if (e.getSource() instanceof IView)
			view = (IView)e.getSource();
		
		switch (e.getActionCommand()) {
		case CREATE_NEW_CONTACT_EVENT:
			cm.createContact(view.getNewContactData());
			cm.last();
			cm.setDataColor(DataColor.blue);
			break;
		case MODEL_LIST_UPDATED_EVENT:
			for (IView v : views)
				v.showContact(cm.getCurrentContactdata());
			break;
		case FIRST_CONTACT_EVENT:
			cm.first();
			break;
		case LAST_CONTACT_EVENT:
			cm.last();
			break;
		case NEXT_CONTACT_EVENT:
			cm.next();
			break;
		case PREVIOUS_CONTACT_EVENT:
			cm.previous();
			break;
		case EDIT_CONTACT_EVENT:
			for (IView v : views)
				v.editContact(view.getShowenContactData());
			break;
		case UPDATE_CONTACT_EVENT:
			cm.updateContact(view.getNewContactData());
			cm.updated();
			break;
		case EXPORT_CONTACT_EVENT:
			if (!cm.exportContact(view.getFormat()))
				System.out.println(EXPORT_FAILED_ERROR);
			break;
		case LOAD_CONTACT_EVENT:
			for (IView v : views)
				v.editContact(cm.loadContactFromFile(view.getFormat(), view.getFilePath()));
			break;
		case SORT_EVENT:
			sortHandler(view.getSortOp(),view.getSortField(), view.getSortOrder());
			cm.first();
		break;
		case SHOW_EVENT:
			if(!cm.isEmpty())
				view.beginContactsDisplay(view.getShowOrder());
		break;
		case NO_SUCH_ELEMENT_EVENT:
			for (IView v : views)
				v.stopContactDisplay();
			break;
		case COLOR_CHANGED_EVENT:
			for (IView v : views)
				if (v instanceof ViewJFX)
					((ViewJFX)v).setDataColor(cm.getDataColor());

		default:
			break;
		}
		

	}

	private void sortHandler(String sortOp, String sortField, String sortOrder) {
		switch (sortOp) {
		case SORT_OP_FIELD:
			cm.sortByField(sortField, sortOrder);
			break;
		case SORT_OP_COUNT:
			cm.sortByCount(sortField, sortOrder);
			break;
		case SORT_OP_REVERSE:
			cm.reverseList();
			break;

		default:
			System.out.println("sort op not supported");
			break;
		}
	}

}
