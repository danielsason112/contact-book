package Contact;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Serializable;

public class Contact implements IContact, Serializable, ProjectFinals{
	
	private static final long 	serialVersionUID = 1L;
	
	private static int 			contactCount = 0;
	
	private int 	id;
	private String 	firstName;
	private String 	lastName;
	private String 	phoneNumber;	
	
	

	public Contact(int id, String firstName, String lastName, String phoneNumber) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		contactCount++;
	}

	@Override
	public void writeObject(RandomAccessFile randomAccessFile) throws IOException {
		String[] uiData = getUiData();
		
		for (int i = 0; i < NUM_OF_FIELDS; i++) {
			FixedLengthStringIO.writeFixedLengthString(uiData[i], FIELD_SIZE, randomAccessFile);
		}
	}

	@Override
	public void export(String format, File file) throws IOException {
		
		switch (format) {
			case FORMAT_TXT:
				PrintWriter pw = new PrintWriter(file);
				pw.print(id + " " + firstName + " " + lastName + " " + phoneNumber);
				pw.close();
				break;
			case FORMAT_OBJ:
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
				oos.writeObject(this);
				oos.close();
				break;
			case FORMAT_BYTE:
				DataOutputStream dos = new DataOutputStream(new FileOutputStream(file));
				dos.writeUTF(String.valueOf(id));
				dos.writeUTF(firstName);
				dos.writeUTF(lastName);
				dos.writeUTF(phoneNumber);
				dos.close();
				break;

			default:
				break;
			}
	}

	@Override
	public String[] getUiData() {
		String[] uiData = {String.valueOf(id), firstName, lastName, phoneNumber};
		return uiData;
	}

	@Override
	public int getObjectSize() {
		return NUM_OF_FIELDS * FIELD_SIZE * NUM_OF_BYTES;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public int getId() {
		return id;
	}

	public static int getContactCount() {
		return contactCount;
	}

	@Override
	public String toString() {
		return "Contact [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", phoneNumber="
				+ phoneNumber + "]";
	}



}
