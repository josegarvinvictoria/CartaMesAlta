package net.josegarvin.cartaMesAlta;

import java.util.ArrayList;

/**
 * Classe per crear objectes de tipus "Baralla".
 * 
 * @author b4tm4n
 *
 */
public class Baralla {

  /**
   * ArrayList per emmagatzemar les cartes que formen la baralla.
   */
  private ArrayList<Carta> cartes;

  /**
   * Pals possibles per a les cartes.
   */
  private String[] pals = { "-B", "-O", "-E", "-C" };

  /**
   * Números possibles per a les cartes.
   */
  private int[] numeros = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };

  /**
   * Constructor per crear objectes de tipus "Baralla".
   */
  Baralla() {
    this.cartes = generarBaralla();
  }

  /**
   * Mètode per generar una baralla espanyola de cartes.
   * 
   * @return --> Retorna un ArrayList amb les cartes que formen la baralla.
   */
  public final ArrayList<Carta> generarBaralla() {
    ArrayList<Carta> baralla = new ArrayList<Carta>();

    for (int i = 0; i < pals.length; i++) {
      for (int j = 0; j < numeros.length; j++) {
        baralla.add(new Carta(numeros[j], pals[i]));
      }
    }
    return baralla;
  }

  /**
   * Mètode per passar les cartes a String.
   */
  public final void barallaToString() {
    for (int i = 0; i < cartes.size(); i++) {
      System.out.println(cartes.get(i).getNumero() + cartes.get(i).getPal());
    }
  }

  /**
   * Mètode per obtenir les cartes de la baralla.
   * @return --> Retorna un ArrayList de cartes.
   */
  public final ArrayList<Carta> getCartes() {
    return cartes;
  }

  /**
   * Mètode per assignar cartes a la baralla.
   * @param cartesN --> Cartes a assignar.
   */
  public final void setCartes(final ArrayList<Carta> cartesN) {
    this.cartes = cartesN;
  }

}
