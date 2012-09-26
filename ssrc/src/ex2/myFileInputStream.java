package ex2;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class myFileInputStream extends FileInputStream {

	public myFileInputStream(String name) throws FileNotFoundException {
		super(name);
	}

	public myFileInputStream(File file) throws FileNotFoundException {
		super(file);
	}
	
	public myFileInputStream(FileDescriptor fd){
		super( fd);
	}
	
    public int read(byte b[], int off, int len) throws IOException {
    	int n = 0;
    	Pair p;
    	byte[] plainBytes = new byte[len];
    	byte[] cipher = new byte[b.length];
        n = super.read(plainBytes, off, len);
        
        for(int i = 0; i < n; i ++)
        	cipher[i] = plainBytes[i];
        
        cipher = SimpleSymmetricBC.cipherBC(plainBytes); 
        
        for(int i = 0; i < b.length; i ++ )
        	b[i] = cipher[i];
        
        return n;
    }
    
    public int read(byte b[]) throws IOException {
        return this.read(b, 0, b.length);
    }
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		int nread = 0;
		String fileName = args[0];
		
		myFileInputStream myfi = new myFileInputStream(fileName);
		FileInputStream fi = new FileInputStream(fileName);
		
		byte[] bc = new byte[1024];		
		byte[] b =  new byte[1024];
		
		myfi.read(bc);
		nread = fi.read(b);
		
		System.out.println( Utils.toHex(bc, nread)  );
		System.out.println( Utils.toHex(b , nread)  );
		System.out.println( Utils.toHex(SimpleSymmetricBC.deCipherBC(bc), nread ));
		
	}

}
