package ex2;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class myFileOutputStream extends FileOutputStream{

	public myFileOutputStream(File file) throws FileNotFoundException {
		super(file);
	}

	public myFileOutputStream(File file, boolean append)
			throws FileNotFoundException {
		
		super(file, append);
	}

	public myFileOutputStream(FileDescriptor fdObj) {
		super(fdObj);
	}

	public myFileOutputStream(String name, boolean append)
			throws FileNotFoundException {
		
		super(name, append);
	}

	public myFileOutputStream(String name) throws FileNotFoundException {
		super(name);
	}

    public void write(byte b[], int off, int len) throws IOException {
        super.write(b);
    }
	
	public void write(byte b[]) throws IOException {
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
