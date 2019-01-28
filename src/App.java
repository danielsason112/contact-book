import java.io.FileNotFoundException;
import java.io.IOException;

import Contact.ContactsManager;
import Controller.Controller;
import View.ContactsManagerFrame;
import View.ViewJFX;
import javafx.application.*;
import javafx.stage.Stage;

public class App extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ContactsManager cm;
		try {
			cm = new ContactsManager("contacts.dat");
			ContactsManagerFrame cmf = new ContactsManagerFrame();
			Controller controller = new Controller(cm);
			controller.addView(cmf);
			ViewJFX view = new ViewJFX(primaryStage);
			controller.addView(view);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	
	
	
}
