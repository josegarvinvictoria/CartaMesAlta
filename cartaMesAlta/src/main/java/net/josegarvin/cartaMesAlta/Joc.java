package net.josegarvin.cartaMesAlta;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Joc implements Serializable {

  /**
 * 
 */
  private static final long serialVersionUID = 981634420561001462L;
  /**
 * 
 */
  
  Scanner lector = new Scanner(System.in);
  Baralla barallaCartes;
  ArrayList<Jugador> jugadorsPartida;
  int contRonda;
  boolean fiJoc = false;

  Joc(Baralla barallaC) {
    this.barallaCartes = barallaC;

    String nFitxer = "serial.ser";
    File fitxer = new File(nFitxer);
    jugadorsPartida = new ArrayList<Jugador>();
    
  }

  void començaJoc() {
    indicarJugInicials();
    while(!fiJoc){
    començaRonda();
    donarCartes();
    cercarGuanyadorRonda();
    }
  }
  
  void continuaJoc(){
    while(!fiJoc){
      començaRonda();
      donarCartes();
      cercarGuanyadorRonda();
      }
    
  }

  /**
   * Mètode per demanar el numero de jugadors inicials.
   */
  void indicarJugInicials() {
    int numJugadors = 0;

    while (numJugadors < 2) {
      System.out.println("Amb quants jugadors iniciem la partida? ");
      numJugadors = lector.nextInt();
    }

    afegirJugadors(numJugadors);

  }

  void començaRonda() {

    contRonda++;
    System.out.println("-------- Ronda " + contRonda + " --------");
    String opcioUsuari;
    // Recorreguem els jugadors per realitzar apostes.
    for (int i = 0; i < jugadorsPartida.size(); i++) {
      System.out.println("Nom del jugador: " + jugadorsPartida.get(i).getNom());
      System.out.println("Monedes disponibles: "
          + jugadorsPartida.get(i).getMonedes());
      System.out.println();
      System.out
          .println("Nº de monedes a apostar o tecla I per afegir un nou jugador:");
      opcioUsuari = lector.next();

      // (RETIRAR?)!!!
      // *************
      tractarOpcio(opcioUsuari, jugadorsPartida.get(i));

    }

  }

  /**
   * Mètode per afegir els jugadors a l'array jugadorsPartida.
   * 
   * @param numJugadors
   */
  void afegirJugadors(int numJugadors) {
    String nouJugador;

    for (int i = 0; i < numJugadors; i++) {
      System.out.println("Nom del jugador: ");
      nouJugador = lector.next();
      jugadorsPartida.add(new Jugador(nouJugador));
    }

    serialitzaObjecte();
  }

  void donarCartes() {
    Random rand = new Random();

    // Recorreguem el jugadors.
    for (int i = jugadorsPartida.size() - 1; i >= 0; i--) {

      // Li assignem una nova carta per cada moneda apostada.
      int apostaJugador = jugadorsPartida.get(i).getAposta();
      for (int j = 0; j < jugadorsPartida.get(i).getAposta(); j++) {
        int rannum = rand.nextInt(barallaCartes.getCartes().size());
        jugadorsPartida.get(i)
            .rebreCarta(barallaCartes.getCartes().get(rannum));
        barallaCartes.getCartes().remove(rannum);
      }
    }

    System.out.println("Tamany BARALLA!!!__>"
        + barallaCartes.getCartes().size());

    for (int i = 0; i < jugadorsPartida.size(); i++) {
      Carta cartaAlta;
      System.out.println("Les cartes del " + jugadorsPartida.get(i).getNom()
          + " son: " + jugadorsPartida.get(i).cartesToString());
      ArrayList<Carta> cartesInutils = jugadorsPartida.get(i)
          .getCartesInutils();
      afegirCartesBaralla(cartesInutils);

    }
    System.out.println("Tamany BARALLA!!!__>"
        + barallaCartes.getCartes().size());
    serialitzaObjecte();
  }

  void afegirCartesBaralla(ArrayList<Carta> cartesARetornar) {
    for (int i = 0; i < cartesARetornar.size(); i++) {
      barallaCartes.getCartes().add(cartesARetornar.get(i));
    }
  }

  int getTotalDinersApostats() {
    int dinersRonda = 0;
    for (int i = 0; i < jugadorsPartida.size(); i++) {
      dinersRonda += jugadorsPartida.get(i).getAposta();
    }
    return dinersRonda;
  }

  void cercarGuanyadorRonda() {
    Jugador campioRonda = jugadorsPartida.get(0);
    for (int i = 1; i < jugadorsPartida.size(); i++) {
      if (campioRonda.getCartes().get(0).getNumero() < jugadorsPartida.get(i)
          .getCartes().get(0).getNumero()) {
        campioRonda = jugadorsPartida.get(i);
      }

    }

    System.out.println("El campio es " + campioRonda.getNom().toUpperCase());
    System.out.println("les seves monedes ABANS:" + campioRonda.getMonedes());
    campioRonda.setMonedes(campioRonda.getMonedes() + getTotalDinersApostats());
    System.out.println("les seves monedes ARA:" + campioRonda.getMonedes());
  }

  void tractarOpcio(String opcioUsuari, Jugador jugador) {
    boolean opcioOk = false;
    int apostaUsuari = 0;

    while (!opcioOk) {
      if (opcioUsuari.equals("I")) {

        opcioOk = true;
        afegirJugadors(1);
        System.out.println("Nou jugador afegit!");

      } else {
        // Es una aposta!

        try {

          apostaUsuari = Integer.parseInt(opcioUsuari);

          while (!jugador.teMonedesPerApostar(apostaUsuari)) {
            System.out.println("Monedes insuficients! Torna-hi:");
            apostaUsuari = lector.nextInt();
          }
          jugador.setAposta(apostaUsuari);
          jugador.setMonedes(jugador.getMonedes() - apostaUsuari);
          opcioOk = true;
          serialitzaObjecte();
        } catch (java.lang.NumberFormatException e) {
          System.out.println("Opcio incorrecte! Torna-hi:");
          opcioUsuari = lector.next();
        }

      }
    }
    System.out.println("APOSTA!:" + apostaUsuari);
  }

  void serialitzaObjecte() {
    try {
      FileOutputStream fs = new FileOutputStream("serial.ser");
      ObjectOutputStream os = new ObjectOutputStream(fs);

      os.writeObject(jugadorsPartida);
      os.close();
      fs.close();
      System.out.println("Serialització ok!:");

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  void desSerialitzarObjecte() {
    
    try {
      FileInputStream fis = new FileInputStream("serial.ser");
      ObjectInputStream ois = new ObjectInputStream(fis);
      jugadorsPartida = (ArrayList<Jugador>) ois.readObject();
      ois.close();
      fis.close();
      System.out.println("Des-serialització Ok!");
    } catch (IOException ioe) {
      ioe.printStackTrace();
      return;
    } catch (ClassNotFoundException c) {
      System.out.println("Class not found");
      c.printStackTrace();
      return;
    }
  }

}
