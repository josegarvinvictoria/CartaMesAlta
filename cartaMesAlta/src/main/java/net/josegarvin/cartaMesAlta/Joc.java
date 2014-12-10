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
  int jugadorsAfegir;
  int contRonda;
  boolean fiJoc = false;

  Joc(Baralla barallaC) {
    this.barallaCartes = barallaC;

    String nFitxer = "serial.ser";
    File fitxer = new File(nFitxer);
    jugadorsPartida = new ArrayList<Jugador>();

  }

  void començaJoc() {
    System.out.println(barallaCartes.getCartes().size());
    indicarJugInicials();
    while (!fiJoc) {
      començaRonda();
      donarCartes();
      cercarGuanyadorRonda();
      if (jugadorsAfegir > 0) {
        afegirJugadors(jugadorsAfegir);
      }

    }
  }

  void continuaJoc() {
    while (!fiJoc) {
      començaRonda();
      donarCartes();
      cercarGuanyadorRonda();
      if (jugadorsAfegir > 0) {
        afegirJugadors(jugadorsAfegir);
      }
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
    jugadorsAfegir = 0;
    System.out.println("-------- Ronda " + contRonda + " --------");
    String opcioUsuari;
    // Recorreguem els jugadors per realitzar apostes.
    for (int i = 0; i < jugadorsPartida.size(); i++) {
      System.out.println("Nom del jugador: " + jugadorsPartida.get(i).getNom());
      System.out.println("Monedes disponibles: "
          + jugadorsPartida.get(i).getMonedes());
      System.out.println();
      
      if(jugadorsPartida.get(i).getAposta() == 0){
        opcioUsuari = demanarOpcio();
        tractarOpcio(opcioUsuari, jugadorsPartida.get(i));
      }else{
        System.out.println("El jug " + jugadorsPartida.get(i).getNom() +" ja ha apostat!");
        System.out.println();
        System.out.println();
      }

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

    for (int i = 0; i < jugadorsPartida.size(); i++) {
      Carta cartaAlta;
      ArrayList<Carta> cartesInutils = jugadorsPartida.get(i)
          .getCartesInutils();
      afegirCartesBaralla(cartesInutils);

      System.out.println("La carta mès alta del jug "
          + jugadorsPartida.get(i).getNom() + " és: "
          + jugadorsPartida.get(i).cartesToString());

    }
    
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
   
    System.out.println("-------->El campio es " + campioRonda.getNom().toUpperCase() + "<--------");
    System.out.println("Les seves monedes ABANS:" + campioRonda.getMonedes());
    campioRonda.setMonedes(campioRonda.getMonedes() + getTotalDinersApostats());
    System.out.println("Les seves monedes ARA:" + campioRonda.getMonedes());

    // Un cop trobat el guanyador retornem les cartes amb les que s'ha jugat.
    for (int i = 0; i < jugadorsPartida.size(); i++) {
      barallaCartes.getCartes().add(jugadorsPartida.get(i).getCartes().get(0));
    }
    
    //Al trobar un guanyador, reinicialitzem les apostes de tots el jugadors per a la pròxima ronda.
    reinicialitzarApostes();
  }

  String demanarOpcio() {
    String opcio = "";
    boolean opcioOk = false;
    System.out
        .println("Que vols fer? [A]postar, [N]ou Jugador, [P]ausar partida:");

    while (!opcioOk) {
      opcio = lector.nextLine();
      if (opcio.equalsIgnoreCase("A") || opcio.equalsIgnoreCase("N")
          || opcio.equalsIgnoreCase("P")) {
        opcioOk = true;
        break;
      }
      System.out.println("Opció invàlida! Torna-hi:");         
    }
    return opcio;
  }

  void tractarOpcio(String opcioUsuari, Jugador jugador) {
    
    
    if(opcioUsuari.equalsIgnoreCase("A")){
      apostar(jugador);
    }
    
    if(opcioUsuari.equalsIgnoreCase("N")){
      jugadorsAfegir++;
      System.out.println("A la proxima ronda afegirem el nou jugador!");
      tractarOpcio(demanarOpcio(), jugador);     
    }
    
    if(opcioUsuari.equalsIgnoreCase("P")){
      serialitzaObjecte();
      System.exit(0);
    }
    
  }
  
  boolean apostaCorrecte(int aposta, Jugador jugador){
    if(aposta>=0 && aposta<=jugador.getMonedes()){
      return true;
    }
      return false;
    
  }

  void apostar(Jugador jugador){
    
    int apostaJug = -1;
    System.out.println("Quantes monedes vols apostar?");
    
    //Comprovem que el jugador introdueix un enter.
    while(!lector.hasNextInt()){
    	lector.next();
    	System.out.println("Nomès números enters! Torna-hi:");
    }          
    apostaJug = lector.nextInt();
    
    //Comprovem si l'aposta es correcte
    while(!apostaCorrecte(apostaJug, jugador)){
      
      System.out.println("Aposta incorrecte! Torna-hi:");
      apostaJug = lector.nextInt();
    }
    jugador.setMonedes(jugador.getMonedes() - apostaJug);
    jugador.setAposta(apostaJug);
    System.out.println("El jug " + jugador.getNom() + " aposta " + jugador.getAposta() + ".");
    
  }
  
  void reinicialitzarApostes(){
    for(int i = 0; i<jugadorsPartida.size();i++){
      jugadorsPartida.get(i).setAposta(0);
    }
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
