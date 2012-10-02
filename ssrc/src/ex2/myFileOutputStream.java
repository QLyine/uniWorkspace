package ex2;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class myFileOutputStream extends FileOutputStream {

	public static final int BLOCK_SIZE = myFileInputStream.BOCK_SIZE;

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
		int n = 0;
		int bytes_to_write = (len > BLOCK_SIZE) ? BLOCK_SIZE : len;
		
		System.out.println("##################### OUT : " + bytes_to_write + " | " + off + " | " + len + " | " + b.length);    	
		
		if (bytes_to_write > 0) {
			byte[] cipher = new byte[BLOCK_SIZE];
			byte[] plainTXT;
			
			for (int i = off, j = 0; i < Math.min(off+bytes_to_write, b.length) && j < cipher.length; i++, j++) 
				cipher[j] = b[i];
			
			
			plainTXT = SimpleSymmetricBC.deCipherBC(cipher, BLOCK_SIZE);

			super.write(plainTXT, 0, bytes_to_write);

			System.out.println(">>>>>>>> ");
			System.out.println(Utils.toHex(b));
			System.out.println(Utils.toHex(cipher, bytes_to_write));
			System.out.println(new String(plainTXT));
		}
	}

	public void write(byte b[]) throws IOException {
		this.write(b, 0, b.length);
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String filename = args[0];
		
		myFileInputStream myfi = new myFileInputStream(filename);
		myFileOutputStream myfo = new myFileOutputStream(filename + ".clean");
		FileInputStream fi = new FileInputStream(filename + ".ori");
		FileOutputStream fo = new FileOutputStream(filename + ".ori");
		FileInputStream ofi = new FileInputStream("../files_to_serve/rfile.txt");
		
		byte b[] = new byte[516];
		
		myfi.read(b);
		fo.write(b);
		// 
		fi.read(b);
		
		myfo.write(b);
		
		ofi.read(b);
		
		System.out.println(Utils.toHex(b));
	}

}
