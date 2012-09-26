package ex1;

import java.security.Security;

/**
 * Uma classe para confirmar se um provedir de criptografia
 * esta instalado... 
 * Ex: BC, SunJCE - Sun, ...
 */
public class SimpleProviderTest
{
    public static void main(String[] args)
    {
        String providerName = "BC";
        
        if (Security.getProvider(providerName) == null)
        {
            System.out.println(providerName + " provider not installed");
        }
        else
        {
            System.out.println(providerName + " is installed.");
        }
    }
}
