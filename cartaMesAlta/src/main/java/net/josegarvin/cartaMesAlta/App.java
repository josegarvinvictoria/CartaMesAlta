package net.josegarvin.cartaMesAlta;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.logging.Level;

/**
 * Hello world!
 *
 */
public class App {
  public static void main(String[] args) {
    Baralla barallaEspanyola = new Baralla();

    //barallaEspanyola.barallaToString();
   
    
    Joc jocCartes = new Joc(barallaEspanyola);
  
      
    String nFitxer = "serial.ser";
    File fitxer = new File(nFitxer);
    
    if(fitxer.exists()){
      System.out.println("S'ha trobat un arxiu de serialització!");
      jocCartes.desSerialitzarObjecte();
      jocCartes.continuaJoc();
    }else{
      System.out.println("NO s'ha trobat cap arxiu de serialització!");
      jocCartes.començaJoc();
    }
    
      

   

    

    
  }

}
