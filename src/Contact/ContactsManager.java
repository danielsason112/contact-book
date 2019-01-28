package Contact;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import CollectionAndSort.FieldsSortComparator;
import CollectionAndSort.FileListIterator;
import CollectionAndSort.MapSortByValueComparator;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

public class ContactsManager implements ProjectFinals {

	
	public final int 		RECORD_SIZE = Contact.NUM_OF_FIELDS * Contact.FIELD_SIZE * Contact.NUM_OF_BYTES;
	private final String 	FILE_ERROR = "Error! Check if file exists.";
	private final String	FORMAT_NOT_SUPPORTED = " format is not supported.";
	private static int 		idGen = 1;
	
	private RandomAccessFile 			raf;
	private FileListIterator<IContact> 	fli;
	private String[] currentContactData = null;
	private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();
	private DataColor dataColor = DataColor.black;

	public ContactsManager(String fileName) throws IOException {
		this.raf = new RandomAccessFile(fileName, "rw");
		idGen += (int)raf.length() / RECORD_SIZE;
		this.fli = new FileListIterator<IContact>(raf);
	}
	
	/**
	 * Creates a new contact and writes it in the end of the file.
	 */
	public void createContact(String[] data) {
		if (data != null)
			fli.add(new Contact(idGen++, data[0], data[1], data[2]));
	}
	
	public void previous() {
		try {
			fli.previous();
			fli.previous();
			currentContactData =  fli.next().getUiData();
			this.setDataColor(DataColor.red);
		} catch (NoSuchElementException e) {
			if (fli.hasNext())
				currentContactData = fli.next().getUiData();
			else
				currentContactData = null;	//list is empty
			proccessEvent(NO_SUCH_ELEMENT_EVENT);
		} finally {
			proccessEvent(MODEL_LIST_UPDATED_EVENT);
		}
	}
	
	public void next() {
		try {
			currentContactData = fli.next().getUiData();
			this.setDataColor(DataColor.green);
		} catch (NoSuchElementException e) {
			proccessEvent(NO_SUCH_ELEMENT_EVENT);
		} finally {
			proccessEvent(MODEL_LIST_UPDATED_EVENT);
		}
	}
	
	public void first() {
		try {
			while (fli.hasPrevious())
				fli.previous();
			currentContactData =  fli.next().getUiData();
			this.setDataColor(DataColor.red);
		} catch (NoSuchElementException e) {
			currentContactData = null;
		} finally {
			proccessEvent(MODEL_LIST_UPDATED_EVENT);
		}
	}
	
	public void last() {
		if (this.fli.hasPrevious())
			this.previous();
		while(fli.hasNext())
			currentContactData = fli.next().getUiData();
		this.setDataColor(DataColor.green);
		proccessEvent(MODEL_LIST_UPDATED_EVENT);
	}
	
	public void updated() {
		try {
			if (fli.hasPrevious())
				currentContactData = fli.previous().getUiData();
			currentContactData = fli.next().getUiData();
			this.setDataColor(DataColor.blue);
		} catch (NoSuchElementException e) {
			
		} finally {
			proccessEvent(MODEL_LIST_UPDATED_EVENT);
		}
	}
	
	/**
	 * Returns boolean value indicating whether list is empty. 
	 */
	public boolean isEmpty() {
		try {
			return this.raf.length() == 0;
		} catch (IOException e) {
			System.out.println(FILE_ERROR);
			return true;
		}
	}

	/**
	 * Gets a new data for a contact an writes the updated content to this.raf file by the
	 * contact's id.
	 */
	public void updateContact(String[] uiData) {
		fli.set(new Contact(this.getContactId(), uiData[0], uiData[1], uiData[2]));
	}

	/**
	 * Creates a file in the format selected with the current displayed contact data.
	 */
	public boolean exportContact(String format) {
		if (this.isEmpty())
			return false;
		fli.previous();
		try {
			fli.next().export(format, new File(String.valueOf(this.getContactId()) + "." + format));
			return true;
		} catch (IOException e) {
			System.out.println(FILE_ERROR);
			return false;
		}
	}

	/** Creates a new contact from a file in the format selected. */
	public String[] loadContactFromFile(String format, String filePath) {
		if (filePath == null)
			return null;
		File file = new File(filePath);
		String[] uiData = null;
		try {			
			switch (format.toLowerCase()) {
			case "txt":
				uiData = loadTxt(file);
				break;
			case "obj.dat":
				uiData = loadObj(file);
				break;
			case "byte.dat":
				uiData = loadByte(file);
				break;
	
			default:
				System.out.println(format + FORMAT_NOT_SUPPORTED);
				break;
			}
			return uiData;
		} catch (Exception e) {
			System.out.println(FILE_ERROR);
			return null;
		}
	}
	
	private String[] loadTxt(File file) throws FileNotFoundException {
		String[] uiData = new String[4];
		Scanner s = new Scanner(file);
		for (int i = 0; i < uiData.length; i++) {
			uiData[i] = s.next();
		}
		s.close();
		return uiData;
	}
	
	private String[] loadObj(File file) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
		String[] uiData = ((IContact)ois.readObject()).getUiData();
		ois.close();
		return uiData;
	}
	
	private String[] loadByte(File file) throws IOException {
		String[] uiData = new String[4];
		DataInputStream dis = new DataInputStream(new FileInputStream(file));
		for (int i = 0; i < uiData.length; i++) {
			uiData[i] = dis.readUTF();
		}
		dis.close();
		return uiData;
	}
	
	private void setItrToBeginning() {
		this.first();
		fli.previous();
	}
	
	private void createCollection(Collection<IContact> coll) throws NoSuchElementException {
		setItrToBeginning();
		while (fli.hasNext())
			coll.add(fli.next());
	}
	
	public void sortByField(String field, String order) {
		try {
			Set<IContact> set = new TreeSet<IContact>(new FieldsSortComparator(field));
			createCollection(set);
			
			this.raf.setLength(0L);
			this.fli = new FileListIterator<>(raf);
			for (IContact contact : set)
				fli.add(contact);
			if (!order.equals(SORT_ORDER_ASC))
				this.reverseList();
			
		} catch (IOException e) {
			System.out.println(FILE_ERROR);
		} catch (NoSuchElementException e) {
			return;	//do nothing
		}
	}
	
	private void createMap(Map<IContact,Integer> map) {
		while (this.fli.hasNext()) {
			IContact c = fli.next();
			if (map.containsKey(c))
				map.replace(c, map.get(c) + 1);
			else
				map.put(c, 1);
		}
	}
	
	public void sortByCount(String field, String order) {
		try {
			Map<IContact,Integer> map = new TreeMap<IContact,Integer>(new FieldsSortComparator(field));
			setItrToBeginning();
			createMap(map);
		
			Set<Entry<IContact, Integer>> set = new TreeSet<Entry<IContact, Integer>>(new MapSortByValueComparator());
			set.addAll(map.entrySet());
		
			this.raf.setLength(0L);
			this.fli = new FileListIterator<>(raf);
			for (Entry<IContact, Integer> entry : set) {
				fli.add(entry.getKey());
			}
			if (!order.equals(SORT_ORDER_ASC))
				this.reverseList();
		} catch (IOException e) {
			System.out.println(FILE_ERROR);
		} catch (NoSuchElementException e) {
			return;	//do nothing
		}
	}
	
	public void reverseList() {
		try {
			Stack<IContact> stack = new Stack<>();
			createCollection(stack);
		
			this.raf.setLength(0L);
			this.fli = new FileListIterator<IContact>(raf);
			while (!stack.isEmpty())
				this.fli.add(stack.pop());
		} catch (IOException e) {
			System.out.println(FILE_ERROR);
		} catch (NoSuchElementException e) {
			return;	//do nothing
		}
	}
	
	private int getContactId() {
		int id;
		if (!fli.hasPrevious()) {
			id = Integer.parseInt(fli.next().getUiData()[0]);
			fli.previous();
			return id;
		}
		fli.previous();
		return Integer.parseInt(fli.next().getUiData()[0]);
		
	}
	
	public String[] getCurrentContactdata() {
		return currentContactData;
	}
	
	public void registerListener(ActionListener listener) {
		this.listeners.add(listener);
	}
	
	private void proccessEvent(String eventCommand) {
		for (ActionListener listener : listeners) {
			listener.actionPerformed(new ActionEvent(this, -1, eventCommand));
		}
	}
	
	public void setDataColor(DataColor dataColor) {
		if (this.dataColor != dataColor) {
			this.dataColor = dataColor;
			proccessEvent(COLOR_CHANGED_EVENT);
		}
	}
	
	public DataColor getDataColor() {
		return dataColor;
	}

}
