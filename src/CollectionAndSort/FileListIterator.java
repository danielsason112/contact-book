package CollectionAndSort;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import Contact.Contact;
import Contact.FixedLengthStringIO;
import Contact.IContact;

public class FileListIterator<T extends IContact> implements ListIterator<T> {

	private RandomAccessFile raf;
	
	public FileListIterator(RandomAccessFile raf) {
		super();
		this.raf = raf;
	}

	// generic using - reflection (not in syllabus)
	// @SuppressWarnings("unchecked")
	// public T initContactWorkaround() {
	// Class<T> mClass = null; // this should be received in the constructor
	// try {
	// mClass = (Class<T>) Class.forName(t.getClass().getName());
	// } catch (ClassNotFoundException e2) {
	// // TODO Auto-generated catch block
	// e2.printStackTrace();
	// return null;
	// }
	// ;
	// try {
	// Class<?> stringClass = Class.forName("String");
	//
	// try {
	// return mClass.getDeclaredConstructor(Integer.TYPE, stringClass,
	// stringClass, stringClass).newInstance(10);
	// } catch (Exception e) {
	// e.printStackTrace();
	// return null;
	// }
	//
	// } catch (ClassNotFoundException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	// return null;
	//
	// }

	// simple approach

	@SuppressWarnings("unchecked")
	private T initContactWorkaround(int id, String firstName, String lastName,
			String phoneNumber) {
		return (T) new Contact(id, firstName, lastName, phoneNumber);
	}
	
	private String[] readCurrentAddress() throws IOException {
		String[] uiData = new String[T.NUM_OF_FIELDS];
		
		for (int i = 0; i < uiData.length; i++) {
			uiData[i] = FixedLengthStringIO.readFixedLengthString(Contact.FIELD_SIZE, raf).trim();
		}
		
		return uiData;
	}

	@Override
	public void add(T e) {
		try {
			this.raf.seek(this.raf.length());
			e.writeObject(raf);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public boolean hasNext() {
		try {
			return this.raf.getFilePointer() != this.raf.length();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean hasPrevious() {
		try {
			return this.raf.getFilePointer() >=  T.RECORD_SIZE;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public T next() {
		try {
			if (!this.hasNext())
				throw new NoSuchElementException();
			String[] uiData = readCurrentAddress();
			
			return initContactWorkaround(Integer.parseInt(uiData[0]), uiData[1], uiData[2], uiData[3]);
		
		} catch (IOException e) {
			return null;
		}
		
	}

	@Override
	public int nextIndex() {
		try {
			return (int) (this.raf.getFilePointer() / T.RECORD_SIZE);
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public T previous() {
		try {
			if (!this.hasPrevious())
				throw new NoSuchElementException();
			this.raf.seek(this.raf.getFilePointer() - T.RECORD_SIZE);
			String[] uiData = readCurrentAddress();
			this.raf.seek(this.raf.getFilePointer() - T.RECORD_SIZE);
			
			return initContactWorkaround(Integer.parseInt(uiData[0]), uiData[1], uiData[2], uiData[3]);
		
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public int previousIndex() {
		try {
			if (raf.getFilePointer() == 0L)
				return -1;
			return (int) ((this.raf.getFilePointer() / T.RECORD_SIZE) - 1);
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public void remove() {
		try {
			long orginalPos = raf.getFilePointer();
			while(this.raf.length() >= orginalPos + T.RECORD_SIZE)
				this.set(this.next());
			this.raf.seek(orginalPos);
			this.raf.setLength(this.raf.length() - T.RECORD_SIZE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void set(T e) {
		try {
			this.raf.seek(this.raf.getFilePointer() - T.RECORD_SIZE);
			e.writeObject(raf);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
