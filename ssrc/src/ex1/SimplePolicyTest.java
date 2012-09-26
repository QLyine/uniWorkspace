package ex1;

import javax.crypto.*;
import javax.crypto.spec.*;

/**
 * Um teste para testar se se instalou uma politica nao restritiva (policy).
 */
public class SimplePolicyTest
{
    public static void main(
        String[] args)
    throws Exception
    {
        byte[] data = { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07 };

        // create a 64 bit secret key from raw bytes

        SecretKey key64 = new SecretKeySpec(new byte[] { 0x00, 0x01, 0x02,
                0x03, 0x04, 0x05, 0x06, 0x07 }, "Blowfish");

        // create a cipher and attempt to encrypt the data block with our key

        Cipher c = Cipher.getInstance("Blowfish/ECB/NoPadding");

        c.init(Cipher.ENCRYPT_MODE, key64);
        c.doFinal(data);
        System.out.println(" 64 bit : OK");
       
       
        // create a 128 bit secret key from raw bytes

        SecretKey key128 = new SecretKeySpec(new byte[] { 0x00, 0x01, 0x02,
                0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C,
	        0x0D, 0x0E , 0x0F}, "Blowfish");


        // try encrypting with a 128 key

        c.init(Cipher.ENCRYPT_MODE, key128);
        c.doFinal(data);
        System.out.println("128 bit : OK");       
       
       

        // create a 192 bit secret key from raw bytes

        SecretKey key192 = new SecretKeySpec(new byte[] { 0x00, 0x01, 0x02,
                0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c,
                0x0d, 0x0e, 0x0f, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16,
                0x17 }, "Blowfish");

        // now try encrypting with the larger key

        c.init(Cipher.ENCRYPT_MODE, key192);
        c.doFinal(data);
        System.out.println("192 bit : OK");
       
       
       
        // create a 256 bit secret key from raw bytes

        SecretKey key256 = new SecretKeySpec(new byte[] { 0x00, 0x01, 0x02,
                0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c,
                0x0d, 0x0e, 0x0f, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16,
                0x17, 0x18, 0x19, 0x20, 0x21, 0x22, 0x23, 0x24, 0x25 }, "Blowfish");

        // now try encrypting with the larger key

        c.init(Cipher.ENCRYPT_MODE, key256);
        c.doFinal(data);
        System.out.println("256 bit : OK");       
       
       
       
        // create a 448 bit secret key from raw bytes

        SecretKey key448 = new SecretKeySpec(new byte[] { 0x00, 0x01, 0x02,
                0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c,
                0x0d, 0x0e, 0x0f, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16,
                0x17, 0x18, 0x19, 0x20, 0x21, 0x22, 0x23, 0x24, 0x25, 0x26,
                0x27, 0x28, 0x29, 0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36,
                0x37, 0x38, 0x39, 0x40, 0x41, 0x42, 0x43, 0x44, 0x45, 0x46,
	        0x47, 0x48, 0x49 }, "Blowfish");

        // now try encrypting with the larger key

        c.init(Cipher.ENCRYPT_MODE, key448);
        c.doFinal(data);
        System.out.println("448 bit : OK");       
       

        System.out.println("Passou todos os testes: Unrestricted Policy ");
    }
}

