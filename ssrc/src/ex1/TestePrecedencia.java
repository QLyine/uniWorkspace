package ex1;

import javax.crypto.Cipher;

/**
 * Teste de precedencia em relacao ao provedir utilizado
 */
public class TestePrecedencia
{
    public static void main(
        String[]    args)
        throws Exception
    {
        Cipher        cipher = Cipher.getInstance("Blowfish/ECB/NoPadding");
        
        System.out.println(cipher.getProvider());
        
        cipher = Cipher.getInstance("Blowfish/ECB/NoPadding", "BC");
        
        System.out.println(cipher.getProvider());
    }
}
