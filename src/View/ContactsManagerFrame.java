package View;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import Contact.ProjectFinals;

public class ContactsManagerFrame extends JFrame implements ProjectFinals, IView {

	private final int JTEXT_FIELDS_SIZE = 21;
	private final String REQUIRED_FIELDS_ERROR = "Please fill all required fields";

	private static final long serialVersionUID = 1L;

	private JPanel upperPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;

	private JTextField[] dataTextFields = new JTextField[NUM_OF_DATA_PARMATERS];
	private CMFJButton createButton = new CMFJButton("Create", CREATE_NEW_CONTACT_EVENT);
	private CMFJButton updateButton = new CMFJButton("Update", UPDATE_CONTACT_EVENT);
	private CMFJButton previousButton = new CMFJButton("   <   ", PREVIOUS_CONTACT_EVENT);
	private CMFJButton nextButton = new CMFJButton("   >   ", NEXT_CONTACT_EVENT);
	private CMFJButton firstButton = new CMFJButton("First", FIRST_CONTACT_EVENT);
	private CMFJButton lastButton = new CMFJButton("Last", LAST_CONTACT_EVENT);
	private CMFJButton editButton = new CMFJButton("Edit contact", EDIT_CONTACT_EVENT);

	private JLabel[] dataLabelArr = new JLabel[NUM_OF_DATA_PARMATERS];
	private JComboBox<String> formatsComboBox = new JComboBox<String>(FORMATS);
	private CMFJButton exportButton = new CMFJButton("Export", EXPORT_CONTACT_EVENT);
	private JTextField filePathField = new JTextField(JTEXT_FIELDS_SIZE);
	private CMFJButton loadFileButton = new CMFJButton("Load file", LOAD_CONTACT_EVENT);

	private JComboBox<String> sortOp = new JComboBox<String>(SORT_OP);
	private JComboBox<String> sortField = new JComboBox<String>(SORT_FIELD);
	private JComboBox<String> sortOrder = new JComboBox<String>(SORT_ORDER);
	private CMFJButton sortButton = new CMFJButton("sort", SORT_EVENT);
	private JComboBox<String> showOrder = new JComboBox<String>(SORT_ORDER);
	private CMFJButton showButton = new CMFJButton("show", SHOW_EVENT);
	private Timer timer = null;

	private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();

	public ContactsManagerFrame() {
		super();

	}

	public void init() {

		// Set the upper JPanel
		upperPanel = new JPanel();
		upperPanel.setLayout(new GridLayout(4, 2, 25, 30));
		for (int i = 0; i < dataTextFields.length; i++) {
			dataTextFields[i] = new JTextField(JTEXT_FIELDS_SIZE);
		}
		upperPanel.add(new JLabel("First name:"));
		upperPanel.add(dataTextFields[0]);
		upperPanel.add(new JLabel("Last name:"));
		upperPanel.add(dataTextFields[1]);
		upperPanel.add(new JLabel("Phone number:"));
		upperPanel.add(dataTextFields[2]);
		upperPanel.add(createButton);
		updateButton.setEnabled(false);
		upperPanel.add(updateButton);

		for (int i = 0; i < dataLabelArr.length; i++) {
			dataLabelArr[i] = new JLabel();
		}

		// Set the middle JPanel
		middlePanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.ipady = 30;
		gbc.insets = new Insets(5, 0, 5, 0);
		setGbc(gbc, 0, 0, 1, 2);
		middlePanel.add(previousButton, gbc);
		setGbc(gbc, 5, 0, 1, 2);
		middlePanel.add(nextButton, gbc);
		setGbc(gbc, 0, 2, 1, 2);
		middlePanel.add(firstButton, gbc);
		setGbc(gbc, 5, 2, 1, 2);
		middlePanel.add(lastButton, gbc);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 120;
		setGbc(gbc, 1, 0, 2, 1);
		middlePanel.add(new JLabel("First name:"), gbc);
		setGbc(gbc, 3, 0, 2, 1);
		middlePanel.add(dataLabelArr[0], gbc);
		setGbc(gbc, 1, 1, 2, 1);
		middlePanel.add(new JLabel("Last name:"), gbc);
		setGbc(gbc, 3, 1, 2, 1);
		middlePanel.add(dataLabelArr[1], gbc);
		setGbc(gbc, 1, 2, 2, 1);
		middlePanel.add(new JLabel("Phone number"), gbc);
		setGbc(gbc, 3, 2, 2, 1);
		gbc.ipadx = 0;
		gbc.ipady = 50;
		middlePanel.add(dataLabelArr[2], gbc);
		gbc.anchor = GridBagConstraints.SOUTH;
		setGbc(gbc, 1, 3, 2, 1);
		gbc.ipady = 0;
		middlePanel.add(editButton, gbc);

		// Set the lower JPanel
		lowerPanel = new JPanel(new GridBagLayout());
		gbc.weightx = 0.5;
		gbc.fill = GridBagConstraints.NONE;
		setGbc(gbc, 0, 1, 1, 1);
		lowerPanel.add(formatsComboBox, gbc);
		setGbc(gbc, 2, 0, 2, 1);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		lowerPanel.add(new JLabel("file path:"), gbc);
		setGbc(gbc, 2, 1, 2, 1);
		lowerPanel.add(filePathField, gbc);
		setGbc(gbc, 2, 2, 2, 1);
		lowerPanel.add(loadFileButton, gbc);
		setGbc(gbc, 1, 0, 1, 3);
		gbc.fill = GridBagConstraints.VERTICAL;
		lowerPanel.add(exportButton, gbc);
		gbc.fill = GridBagConstraints.NONE;
		setGbc(gbc, 0, 3, 1, 1);
		lowerPanel.add(sortOp, gbc);
		setGbc(gbc, 1, 3, 1, 1);
		lowerPanel.add(sortField, gbc);
		setGbc(gbc, 2, 3, 1, 1);
		lowerPanel.add(sortOrder, gbc);
		setGbc(gbc, 3, 3, 1, 1);
		lowerPanel.add(sortButton, gbc);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		setGbc(gbc, 0, 4, 2, 1);
		lowerPanel.add(showOrder, gbc);
		setGbc(gbc, 2, 4, 2, 1);
		lowerPanel.add(showButton, gbc);

		this.add(upperPanel, BorderLayout.NORTH);
		this.add(middlePanel, BorderLayout.CENTER);
		this.add(lowerPanel, BorderLayout.SOUTH);
		this.pack();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		this.setAlwaysOnTop(true);
		this.setTitle("Contact Manager");

	}

	@Override
	public void showContact(String[] uiData) {
		if (uiData != null)
			for (int i = 0; i < dataLabelArr.length; i++)
				dataLabelArr[i].setText(uiData[i + 1]);
	}

	private void setGbc(GridBagConstraints gbc, int gridx, int gridy, int width, int height) {
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.gridwidth = width;
		gbc.gridheight = height;

	}

	/**
	 * Returns a boolean variable indicating whether one or more of the required
	 * JTextFields is empty, in order to generate a new Contact.
	 */
	private boolean missingData() {
		for (int i = 0; i < dataTextFields.length; i++) {
			if (dataTextFields[i].getText().isEmpty())
				return true;
		}

		return false;
	}

	private void showMassage(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}

	@Override
	public void registerListener(ActionListener listener) {
		this.listeners.add(listener);

	}

	@Override
	public void fireActionEvent(ActionEvent e) {
		for (ActionListener listener : listeners) {
			listener.actionPerformed(new ActionEvent(this, -1, e.getActionCommand()));
		}
	}

	@Override
	public String[] getNewContactData() {
		String[] data = null;
		if (missingData())
			showMassage(REQUIRED_FIELDS_ERROR);
		else {
			data = new String[dataTextFields.length];
			for (int i = 0; i < data.length; i++)
				data[i] = dataTextFields[i].getText();
		}
		updateButton.setEnabled(false);
		return data;
	}

	@Override
	public void editContact(String[] uiData) {
		if (uiData == null)
			return;
		for (int i = 0; i < dataTextFields.length; i++)
			dataTextFields[i].setText(uiData[i+1]);
	}

	private class CMFJButton extends JButton {

		private static final long serialVersionUID = 1L;

		private CMFJButton(String name, String actionCommand) {
			super(name);
			this.setActionCommand(actionCommand);
			this.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					fireActionEvent(e);
				}
			});
		}

	}

	@Override
	public String getFormat() {
		return (String) formatsComboBox.getSelectedItem();
	}

	@Override
	public String getFilePath() {
		if (!filePathField.getText().isEmpty())
			return this.filePathField.getText();
		showMassage(REQUIRED_FIELDS_ERROR);
		return null;
	}

	@Override
	public String[] getShowenContactData() {
		if (dataLabelArr[0].getText().isEmpty())
			return null;
		String[] data = new String[4];
		for (int i = 1; i < data.length; i++)
			data[i] = dataLabelArr[i-1].getText();
		updateButton.setEnabled(true);
		return data;
	}

	@Override
	public String getSortOp() {
		return sortOp.getSelectedItem().toString();
	}

	@Override
	public String getSortField() {
		return sortField.getSelectedItem().toString();
	}

	@Override
	public String getSortOrder() {
		return sortOrder.getSelectedItem().toString();
	}

	@Override
	public String getShowOrder() {
		return showOrder.getSelectedItem().toString();
	}

	@Override
	public void beginContactsDisplay(String order) {
		timer = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (showOrder.getSelectedItem().toString().equals(SORT_ORDER_ASC))
					fireActionEvent(new ActionEvent(this, -1, NEXT_CONTACT_EVENT));
				else
					fireActionEvent(new ActionEvent(this, -1, PREVIOUS_CONTACT_EVENT));
				}
			});
			
		if (showOrder.getSelectedItem().toString().equals(SORT_ORDER_ASC))
			fireActionEvent(new ActionEvent(this, -1, FIRST_CONTACT_EVENT));
		else
			fireActionEvent(new ActionEvent(this, -1, LAST_CONTACT_EVENT));
		timer.start();
	}

	@Override
	public void stopContactDisplay() {
		if (this.timer != null)
			if (this.timer.isRunning())
				this.timer.stop();
	}

}
