package net.josegarvin.cartaMesAlta;

import java.io.Serializable;

/**
 * Classe per crear objectes de tipus "Carta".
 * @author Jose Garvin Victoria.
 *
 */
public class Carta implements Serializable {

  /**
   * UID necessari per a la serialització del objectes de tipus "Carta".
   */
  private static final long serialVersionUID = -191781805571611534L;

  /**
   * Numero de la carta.
   */
  private int numero;
  
  /**
   * Pal de la carta.
   */
  private String pal;

  /**
   * Constructor d'objectes de tipus "Carta".
   * @param numC --> Numero de la carta.
   * @param palC --> Pal de la carta.
   */
  Carta(final int numC, final String palC) {
    this.numero = numC;
    this.pal = palC;
  }

  /**
   * Mètode per obtenir el numero d'una carta.
   * 
   * @return --> Retorna un enter corresponent al numero de la carta.
   */
  public final int getNumero() {
    return numero;
  }

  /**
   * Mètode per assignar un numero a una carta.
   * 
   * @param numeroC
   *          --> Numero a assignar.
   */
  public final void setNumero(final int numeroC) {
    this.numero = numeroC;
  }

  /**
   * Mètode per obtenir el pal d'una carta.
   * 
   * @return --> Retorna un String corresponent al pal de la carta.
   */
  public final String getPal() {
    return pal;
  }

  /**
   * Mètode per assignar un pal a una carta.
   * 
   * @param palC
   *          --> Pal a assignar.
   */
  public final void setPal(final String palC) {
    this.pal = palC;
  }

  /**
   * Mètode per obtenir un String amb el numero i el pal d'una carta.
   * 
   * @return --> un String amb el numero i el pal d'una carta.
   */
  public final String cartaToString() {
    return this.numero + this.pal;
  }

}
