package View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import Contact.ProjectFinals;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;

public class ViewJFX implements IView, ProjectFinals {
	
	private static final String REQUIRED_FIELDS_ERROR = "Please fill all required fields.";
	
	private GridPane upperPane;
	private GridPane middlePane;
	private GridPane lowerPane;
	private GridPane bottomPane;
	private GridPane container;
	private Scene primaryScene;
	private Stage primaryStage;
	
	private TextField[] dataTextFieldArr = new TextField[NUM_OF_DATA_PARMATERS];
	private Button createButton = new Button("create");
	private Button updateButton = new Button("update");
	
	private Text[] dataTextArr = new Text[NUM_OF_DATA_PARMATERS];
	private Button previousButton = new Button(" < ");
	private Button nextButton = new Button(" > ");
	private Button firstButton = new Button("first");
	private Button lastButton = new Button("last");
	private Button editButton = new Button("EDIT");
	
	private ComboBox<String> formatsComboBox = new ComboBox<String>(FXCollections.observableArrayList(FORMATS));
	private Button exportButton = new Button("export");
	private TextField filePathField = new TextField();
	private Button loadFileButton = new Button("load");
	
	private ComboBox<String> sortOp = new ComboBox<String>(FXCollections.observableArrayList(SORT_OP));
	private ComboBox<String> sortField = new ComboBox<String>(FXCollections.observableArrayList(SORT_FIELD));
	private ComboBox<String> sortOrder = new ComboBox<String>(FXCollections.observableArrayList(SORT_ORDER));
	private Button sortButton = new Button("sort");
	private ComboBox<String> showOrder = new ComboBox<String>(FXCollections.observableArrayList(SORT_ORDER));
	private Button showButton = new Button("show");
	private Timer timer = null;
	
	
	private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();
	
	public ViewJFX(Stage primaryStage) {
		
		this.primaryStage = primaryStage;
		
		this.upperPane = new GridPane();
		this.upperPane.setAlignment(Pos.CENTER);
		this.upperPane.setHgap(10.0);
		this.upperPane.setVgap(10.0);
		for (int i = 0; i < 3; i++) {
			dataTextFieldArr[i] = new TextField();
			dataTextArr[i] = new Text(" ");
			dataTextArr[i].setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize()));
		}
		this.upperPane.add(new Label("first name: "), 0, 0);
		this.upperPane.add(dataTextFieldArr[0], 1, 0);
		this.upperPane.add(new Label("last name: "), 0, 1);
		this.upperPane.add(dataTextFieldArr[1], 1, 1);
		this.upperPane.add(new Label("phone number: "), 0, 2);
		this.upperPane.add(dataTextFieldArr[2], 1, 2);
		this.upperPane.add(createButton, 0, 3);
		this.upperPane.add(updateButton, 1, 3);
		this.updateButton.setDisable(true);
		this.updateButton.setAlignment(Pos.CENTER_RIGHT);
		
		this.middlePane = new GridPane();
		this.middlePane.setAlignment(Pos.CENTER);
		this.middlePane.setHgap(60.0);
		this.middlePane.add(previousButton, 0, 0);
		this.middlePane.add(nextButton, 3, 0);
		this.middlePane.add(firstButton, 0, 1);
		this.middlePane.add(lastButton, 3, 1);
		this.middlePane.add(new Label("first name: "), 1, 0);
		this.middlePane.add(new Label("last name: "), 1, 1);
		this.middlePane.add(new Label("phone number: "), 1, 2);
		for (int i = 0; i < dataTextArr.length; i++) {
			this.middlePane.add(dataTextArr[i], 2, i);
		}
		this.middlePane.add(editButton, 1, 3);
		
		this.lowerPane = new GridPane();
		this.lowerPane.setAlignment(Pos.CENTER);
		this.lowerPane.setHgap(20.0);
		this.lowerPane.add(formatsComboBox, 0, 1);
		formatsComboBox.setValue(FORMAT_TXT);
		this.lowerPane.add(exportButton, 1, 1);
		this.lowerPane.setHgap(150.0);
		this.lowerPane.add(new Label("file path:"), 2, 0);
		this.lowerPane.setHgap(20.0);
		this.lowerPane.add(filePathField, 2, 1);
		this.lowerPane.add(loadFileButton, 2, 2);
		
		this.bottomPane = new GridPane();
		this.bottomPane.setAlignment(Pos.CENTER);
		this.bottomPane.setHgap(20.0);
		this.bottomPane.add(sortOp, 0, 0);
		this.sortOp.setValue(SORT_OP_FIELD);
		this.bottomPane.add(sortField, 1, 0);
		this.sortField.setValue(FIRST_NAME_FIELD);
		this.bottomPane.add(sortOrder, 2, 0);
		this.sortOrder.setValue(SORT_ORDER_ASC);
		this.bottomPane.add(sortButton, 3, 0);
		this.bottomPane.setVgap(40.0);
		this.bottomPane.add(showOrder, 0, 1);
		this.showOrder.setValue(SORT_ORDER_ASC);
		this.bottomPane.add(showButton, 1, 1);
		
		this.container = new GridPane();
		this.container.setAlignment(Pos.BOTTOM_CENTER);
		this.container.setVgap(40.0);
		this.container.add(upperPane, 0, 0);
		this.container.add(middlePane, 0, 1);
		this.container.add(lowerPane, 0, 2);
		this.container.add(bottomPane, 0, 3);
		
		this.createButton.setOnAction(e -> fireActionEvent(new ActionEvent(this, -1, CREATE_NEW_CONTACT_EVENT)));
		this.updateButton.setOnAction(e -> fireActionEvent(new ActionEvent(this, -1, UPDATE_CONTACT_EVENT)));
		this.previousButton.setOnAction(e -> fireActionEvent(new ActionEvent(this, -1, PREVIOUS_CONTACT_EVENT)));
		this.nextButton.setOnAction(e -> fireActionEvent(new ActionEvent(this, -1, NEXT_CONTACT_EVENT)));
		this.firstButton.setOnAction(e -> fireActionEvent(new ActionEvent(this, -1, FIRST_CONTACT_EVENT)));
		this.lastButton.setOnAction(e -> fireActionEvent(new ActionEvent(this, -1, LAST_CONTACT_EVENT)));
		this.editButton.setOnAction(e -> fireActionEvent(new ActionEvent(this, -1, EDIT_CONTACT_EVENT)));
		this.exportButton.setOnAction(e -> fireActionEvent(new ActionEvent(this, -1, EXPORT_CONTACT_EVENT)));
		this.loadFileButton.setOnAction(e -> fireActionEvent(new ActionEvent(this, -1, LOAD_CONTACT_EVENT)));
		this.sortButton.setOnAction(e -> fireActionEvent(new ActionEvent(this, -1, SORT_EVENT)));
		this.showButton.setOnAction(e -> fireActionEvent(new ActionEvent(this, -1, SHOW_EVENT)));
		
		primaryScene = new Scene(container);
		
		setDataColor(DataColor.black);
				
	}
	
	@Override
	public void init() {
		primaryStage.setHeight(550);
		primaryStage.setWidth(500);
		primaryStage.setScene(primaryScene);
		primaryStage.show();
		primaryStage.setTitle("Contact Manager");
	}

	@Override
	public void registerListener(ActionListener listener) {
		this.listeners.add(listener);
	}

	@Override
	public void fireActionEvent(ActionEvent e) {
		for (ActionListener listener : listeners)
			listener.actionPerformed(e);
	}

	@Override
	public String[] getNewContactData() {
		String[] data = null;
		if (missingData())
			showMassage(REQUIRED_FIELDS_ERROR);
		else {
			data = new String[dataTextFieldArr.length];
			for (int i = 0; i < data.length; i++)
				data[i] = dataTextFieldArr[i].getText();
		}
		updateButton.setDisable(true);
		return data;
	}

	private boolean missingData() {
		for (int i = 0; i < dataTextFieldArr.length; i++)
			if (dataTextFieldArr[i].getText().isEmpty())
				return true;
		return false;
	}

	private void showMassage(String msg) {
		BorderPane pane = new BorderPane();
		Stage stage = new Stage();
		Button OKbutton = new Button("OK");
		OKbutton.setOnAction(e -> stage.close());
		pane.setTop(new Label(msg));
		pane.setBottom(OKbutton);
		stage.setScene(new Scene(pane));
		stage.setHeight(100);
		stage.setWidth(200);
		stage.show();
	}

	@Override
	public void showContact(String[] uiData) {
		if (uiData != null)
			for (int i = 0; i < dataTextArr.length; i++)
				dataTextArr[i].setText(uiData[i+1]);
		
	}

	@Override
	public void editContact(String[] uiData) {
		if (uiData == null)
			return;
		for (int i = 0; i < dataTextFieldArr.length; i++)
			dataTextFieldArr[i].setText(uiData[i+1]);
	}

	@Override
	public String[] getShowenContactData() {
		if (dataTextArr[0].getText().isEmpty())
			return null;
		String[] data = new String[4];
		for (int i = 1; i < data.length; i++)
			data[i] = dataTextArr[i-1].getText();
		updateButton.setDisable(false);
		return data;
	}

	@Override
	public String getFormat() {
		return formatsComboBox.getValue();
	}

	@Override
	public String getFilePath() {
		if (!filePathField.getText().isEmpty())
			return this.filePathField.getText();
		showMassage(REQUIRED_FIELDS_ERROR);
		return null;
	}

	@Override
	public String getSortOp() {
		return sortOp.getValue();
	}

	@Override
	public String getSortField() {
		return sortField.getValue();
	}

	@Override
	public String getSortOrder() {
		return sortOrder.getValue();
	}

	@Override
	public String getShowOrder() {
		return showOrder.getValue();
	}

	@Override
	public void beginContactsDisplay(String order) {
timer = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (showOrder.getValue().equals(SORT_ORDER_ASC))
					fireActionEvent(new ActionEvent(this, -1, NEXT_CONTACT_EVENT));
				else
					fireActionEvent(new ActionEvent(this, -1, PREVIOUS_CONTACT_EVENT));
				}
			});
			
		if (showOrder.getValue().equals(SORT_ORDER_ASC))
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
	
	public void setDataColor(DataColor dataColor) {
		for (int i = 0; i < dataTextArr.length; i++) {
			dataTextArr[i].setFill(dataColor.getColorValue());
		}
	}


}
