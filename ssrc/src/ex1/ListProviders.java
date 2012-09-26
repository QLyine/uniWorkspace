package ex1;

import java.security.Provider;
import java.security.Security;

/**
 * Mostrar os provedores que se encontram instalados no JRE
 */

public class ListProviders
{
    public static void main(
        String[]	args)
    {
        Provider[]	providers = Security.getProviders();
        
        for (int i = 0; i != providers.length; i++)
        {
            System.out.println("Name: " + providers[i].getName() + Utils.makeBlankString(15 - providers[i].getName().length())+ " Version: " + providers[i].getVersion());
        }
    }
}
