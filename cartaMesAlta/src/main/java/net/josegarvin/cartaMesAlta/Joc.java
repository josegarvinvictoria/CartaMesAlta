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

/**
 * Classe per crear objectes de tipus "Joc".
 * 
 * @author Jose Garvín Victoria
 *
 */
public class Joc implements Serializable {

  /**
   * Variable per controlar el numero d'apostants en una ronda.
   */
  private int numapostants = 0;
  /**
   * UID de la classe.
   */
  private static final long serialVersionUID = 981634420561001462L;

  /**
   * Fitxer de serialitzacio.
   */
  private File fitxerSerial = new File("serial.ser");
  /**
   * Instancia de Scanner per poder llegir des del teclat.
   */
  private Scanner lector = new Scanner(System.in);

  /**
   * Baralla de cartes del joc.
   */
  private Baralla barallaCartes;

  /**
   * ArrayList amb els jugadors que formen el joc.
   */
  private ArrayList<Jugador> jugadorsPartida;

  /**
   * Variable utilitzada per contar el jugadors que s'han d'afegir al final de
   * la ronda.
   */
  private int jugadorsAfegir;

  /**
   * Variable utilitzada per contabilitzar les rondes del joc.
   */
  private int contRonda;

  /**
   * Constructor d'objectes de tipus "Joc".
   * 
   * @param barallaC
   *          --> Baralla de cartes.
   */
  Joc(final Baralla barallaC) {
    this.barallaCartes = barallaC;
    jugadorsPartida = new ArrayList<Jugador>();

  }

  /**
   * Mètode que s'encarrega d'iniciar el joc.
   */
  final void comencaJoc() {
    System.out.println(barallaCartes.getCartes().size());
    if (!fitxerSerial.exists()) {
      indicarJugInicials();
    } else {
      desSerialitzarObjecte();
    }

    while (jugadorsPartida.size() >= 2) {
      comencaRonda();
      donarCartes();
      cercarGuanyadorRonda();
      eliminarJugPobres();
      if (jugadorsAfegir > 0) {
        afegirJugadors(jugadorsAfegir);
      }

    }
    // desSerialitzarObjecte();
    if (jugadorsPartida.size() < 2 && jugadorsAfegir == 0) {

      // Preguntar Xaviii!
      System.out.println("Jugadors insuficients! La partida ha acabat!");
    }
  }

  /**
   * Mètode que s'encarrega de continuar el joc.
   */
  final void continuaJoc() {
    while (jugadorsPartida.size() >= 2) {
      comencaRonda();
      donarCartes();
      cercarGuanyadorRonda();
      eliminarJugPobres();
      if (jugadorsAfegir > 0) {
        afegirJugadors(jugadorsAfegir);
      }
    }

    if (jugadorsPartida.size() < 2 && jugadorsAfegir == 0) {
      System.out.println("Jugadors insuficients! La partida ha acabat!");
    }

  }

  /**
   * Mètode per demanar el numero de jugadors inicials.
   */
  final void indicarJugInicials() {
    int numJugadors = 0;

    while (numJugadors < 2) {
      System.out.println("Amb quants jugadors iniciem la partida? ");
      numJugadors = lector.nextInt();
    }

    afegirJugadors(numJugadors);

  }

  /**
   * Mètode per iniciar una ronda al joc.
   */
  final void comencaRonda() {

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

      if (jugadorsPartida.get(i).getAposta() == 0) {
        opcioUsuari = demanarOpcio();
        tractarOpcio(opcioUsuari, jugadorsPartida.get(i));
      } else {
        System.out.println("El jug " + jugadorsPartida.get(i).getNom()
            + " ja havia apostat!");
        System.out.println();
        System.out.println();
      }

    }

  }

  /**
   * Mètode per afegir els jugadors a l'array jugadorsPartida.
   * 
   * @param numJugadors
   *          --> Número de jugadors a afegir.
   */
  final void afegirJugadors(final int numJugadors) {
    String nouJugador;

    for (int i = 0; i < numJugadors; i++) {
      System.out.println("Nom del jugador: ");
      nouJugador = lector.next();
      jugadorsPartida.add(new Jugador(nouJugador));
    }

    serialitzaObjecte();
  }

  /**
   * Mètode per donar cartes als jugadors desprès de que realitzin l'aposta.
   */
  final void donarCartes() {
    Random rand = new Random();

    // Recorreguem el jugadors.
    for (int i = jugadorsPartida.size() - 1; i >= 0; i--) {

      // Li assignem una nova carta per cada moneda apostada.

      for (int j = 0; j < jugadorsPartida.get(i).getAposta(); j++) {
        int rannum = rand.nextInt(barallaCartes.getCartes().size());
        jugadorsPartida.get(i)
            .rebreCarta(barallaCartes.getCartes().get(rannum));
        barallaCartes.getCartes().remove(rannum);
      }
    }

    for (int i = 0; i < jugadorsPartida.size(); i++) {

      ArrayList<Carta> cartesInutils = jugadorsPartida.get(i)
          .getCartesInutils();
      afegirCartesBaralla(cartesInutils);

      System.out.println("La carta mès alta del jug "
          + jugadorsPartida.get(i).getNom() + " és: "
          + jugadorsPartida.get(i).cartesToString());

    }

    serialitzaObjecte();
  }

  /**
   * Mètode que s'encarrega de retornar les cartes que ens serveixen.
   * 
   * @param cartesARetornar
   *          --> Arraylist de cartes amb les cartes a retornar a la baralla.
   */
  final void afegirCartesBaralla(final ArrayList<Carta> cartesARetornar) {
    for (int i = 0; i < cartesARetornar.size(); i++) {
      barallaCartes.getCartes().add(cartesARetornar.get(i));
    }
  }

  /**
   * Mètode per obtenir els diners que s'han apostat en una ronda.
   * 
   * @return --> Retorna un enter corresponent a la suma de totes les apostes
   *         dels jugadors.
   */
  final int getTotalDinersApostats() {
    int dinersRonda = 0;
    for (int i = 0; i < jugadorsPartida.size(); i++) {
      dinersRonda += jugadorsPartida.get(i).getAposta();
    }
    return dinersRonda;
  }

  /**
   * Mètode per cercar el guanyador de la ronda.
   */
  final void cercarGuanyadorRonda() {
    if (numapostants == 0) {
      System.out.println("NO HI HA APOSTES!");
    } else {
      Jugador campioRonda = jugadorsPartida.get(0);
      for (int i = 1; i < jugadorsPartida.size(); i++) {
        if (campioRonda.getCartes().get(0).getNumero() < jugadorsPartida.get(i)
            .getCartes().get(0).getNumero()) {
          campioRonda = jugadorsPartida.get(i);
        }

      }
      System.out.println("-------->El campio es "
          + campioRonda.getNom().toUpperCase() + "<--------");
      System.out.println("Les seves monedes ABANS:" + campioRonda.getMonedes());

      campioRonda.setMonedes(campioRonda.getMonedes()
          + getTotalDinersApostats());

      System.out.println("Les seves monedes ARA:" + campioRonda.getMonedes());
      System.out.println();

      // Un cop trobat el guanyador retornem les cartes amb les que s'ha jugat.
      for (int i = 0; i < jugadorsPartida.size(); i++) {
        barallaCartes.getCartes()
            .add(jugadorsPartida.get(i).getCartes().get(0));
      }

      // Al trobar un guanyador, reinicialitzem les apostes de tots el jugadors
      // per a la pròxima ronda.
      reinicialitzarApostes();
    }

  }

  /**
   * Mètode per demanar una opció a l'usuari.
   * 
   * @return --> Retorna un String corresponent a la opcio escollida per
   *         l'usuari.
   */
  final String demanarOpcio() {
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

    }
    return opcio;
  }

  /**
   * Mètode per tractar l'opció escollida per l'usuari.
   * 
   * @param opcioUsuari
   *          --> Opcio escollida.
   * @param jugador
   *          --> Jugador que ha escollit l'opció.
   */
  final void tractarOpcio(final String opcioUsuari, final Jugador jugador) {

    if (opcioUsuari.equalsIgnoreCase("A")) {
      apostar(jugador);
    }

    if (opcioUsuari.equalsIgnoreCase("N")) {
      jugadorsAfegir++;
      System.out.println("A la proxima ronda afegirem el nou jugador!");
      tractarOpcio(demanarOpcio(), jugador);
    }

    if (opcioUsuari.equalsIgnoreCase("P")) {
      serialitzaObjecte();
      System.exit(0);
    }

  }

  /**
   * Mètode per determinar si una aposta es correcte.
   * 
   * @param aposta
   *          --> Monedes apostades.
   * @param jugador
   *          --> Jugador que realitza l'aposta.
   * @return --> Retorna True si l'aposta es correcte o False si no ho es.
   */
  final boolean apostaCorrecte(final int aposta, final Jugador jugador) {
    if (aposta >= 0 && aposta <= jugador.getMonedes()) {
      return true;
    }
    return false;

  }

  /**
   * Mètode per apostar.
   * 
   * @param jugador
   *          --> Jugador que realitza l'aposta.
   */
  final void apostar(final Jugador jugador) {

    int apostaJug = -1;
    System.out.println("Quantes monedes vols apostar?");

    // Comprovem que el jugador introdueix un enter.
    while (!lector.hasNextInt()) {
      lector.next();
      System.out.println("Nomès números enters! Torna-hi:");
    }
    apostaJug = lector.nextInt();

    // Comprovem si l'aposta es correcte
    while (!apostaCorrecte(apostaJug, jugador)) {

      System.out.println("Aposta incorrecte! Torna-hi:");
      apostaJug = lector.nextInt();
    }
    jugador.setMonedes(jugador.getMonedes() - apostaJug);
    jugador.setAposta(apostaJug);
    if (apostaJug != 0) {
      numapostants++;
    }
    serialitzaObjecte();
    System.out.println("El jug " + jugador.getNom() + " aposta "
        + jugador.getAposta() + ".");

  }

  /**
   * Mètode que s'encarrega de tornar a posar a 0 les apostes de tots el
   * jugadors, despres de trobar un guanyador. A mès reinicialitza la variable
   * que controla el numero d'apostants per ronda.
   */
  final void reinicialitzarApostes() {
    for (int i = 0; i < jugadorsPartida.size(); i++) {
      jugadorsPartida.get(i).setAposta(0);
    }
    numapostants = 0;
  }

  /**
   * Mètode que s'encarrega d'expulsar als jugadors que es queden sense monedes.
   */
  final void eliminarJugPobres() {
    for (int i = jugadorsPartida.size() - 1; i >= 0; i--) {
      if (jugadorsPartida.get(i).getMonedes() == 0) {
        System.out.println("Jugador " + jugadorsPartida.get(i).getNom()
            + " expulsat! No te diners.");
        jugadorsPartida.remove(i);
      }
    }
  }

  /**
   * Mètode que s'encarrega de serialitzar l'objecte "jugadorPartida".
   */
  final void serialitzaObjecte() {
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

  /**
   * Mètode que s'encarrega de des-serialitzar l'objecte "jugadorPartida".
   */
  final void desSerialitzarObjecte() {

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
