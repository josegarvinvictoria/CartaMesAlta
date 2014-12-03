package net.josegarvin.cartaMesAlta;

import java.util.Collections;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Baralla barallaEspanyola = new Baralla();
//        
//        barallaEspanyola.barallaToString();
      
      Joc jocCartes = new Joc(barallaEspanyola);
      jocCartes.començaJoc();
    }
}
