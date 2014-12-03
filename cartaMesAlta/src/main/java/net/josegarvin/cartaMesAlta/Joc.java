package net.josegarvin.cartaMesAlta;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Joc {

  Scanner lector = new Scanner(System.in);
  Baralla barallaCartes;
  ArrayList<Jugador> jugadorsPartida;
  int contRonda;

  Joc(Baralla barallaC) {
    this.barallaCartes = barallaC;
    this.jugadorsPartida = new ArrayList<Jugador>();

  }

  void començaJoc() {
    indicarJugInicials();
    començaRonda();
    donarCartes();
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
  }

  void donarCartes(){
    Random rand = new Random();
    
    
    //Recorreguem el jugadors.
    for(int i = 0; i<jugadorsPartida.size();i++){
      
      //Li assignem una nova carta per cada moneda apostada.
      int apostaJugador = jugadorsPartida.get(i).getAposta();
      for(int j = 0; j<jugadorsPartida.get(i).getAposta(); j++){
        int rannum = rand.nextInt(barallaCartes.getCartes().size());
        jugadorsPartida.get(i).rebreCarta(barallaCartes.getCartes().get(rannum));
      }
    }
    
    for(int i = 0; i<jugadorsPartida.size();i++){
      System.out.println("Les cartes del " + jugadorsPartida.get(i).getNom() + " son: " + jugadorsPartida.get(i).getCartes());
    }
    
    
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
          opcioOk = true;

        } catch (java.lang.NumberFormatException e) {
          System.out.println("Opcio incorrecte! Torna-hi:");
          opcioUsuari = lector.next();
        }

      }
    }
    System.out.println("APOSTA!:" + apostaUsuari);
  }
  

}
