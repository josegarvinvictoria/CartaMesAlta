package net.josegarvin.cartaMesAlta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * Classe per crear objectes de tipus "Jugador".
 * 
 * @author Jose Garvin Victoria
 *
 */
public class Jugador implements Serializable {

  /**
   * Valor de referencia per generar monedes aleatories.
   */
  private static final int REF_VALOR_ALEATORI = 6;

  /**
   * UID de la classe.
   */
  private static final long serialVersionUID = 3456735106916880740L;

  /**
   * Nom del jugador.
   */
  private String nom;

  /**
   * Cartes del jugador.
   */
  private ArrayList<Carta> cartes;

  /**
   * Monedes del jugador.
   */
  private int monedes;

  /**
   * Aposta realitzada per el jugador.
   */
  private int aposta;

  /**
   * Constructor per crear objectes de tipus "Jugador".
   * 
   * @param nomJ
   *          --> Nom del jugador.
   */
  public Jugador(final String nomJ) {
    // TODO Auto-generated constructor stub
    this.nom = nomJ;
    this.cartes = new ArrayList<Carta>();
    this.monedes = generarMonedes();
    this.aposta = 0;
  }

  /**
   * Mètode per generar les monedes del jugador.
   * 
   * @return --> Retorna un numero aleatori de monedes de 1 a 6.
   */
  final int generarMonedes() {

    Random rnd = new Random();
    return rnd.nextInt(REF_VALOR_ALEATORI) + 1;
  }

  /**
   * Mètode per comprovar si un usuari té prous diners per apostar.
   * 
   * @param apostaN
   *          --> Aposta a realitzar.
   * @return --> Retorna True en el cas de que el jugador tingui prous monedes o
   *         False si no en té.
   */
  public final boolean teMonedesPerApostar(final int apostaN) {
    if (apostaN <= this.monedes && apostaN >= 0) {
      return true;
    }
    return false;
  }

  /**
   * Mètode per rebre una carta.
   * 
   * @param carta
   *          --> Carta rebuda.
   */
  public final void rebreCarta(final Carta carta) {
    cartes.add(carta);
  }

  /**
   * Mètode per obtenir les cartes inutils. Cartes amb numeros petits.
   * 
   * @return --> Retorna un ArrayList de cartes amb les cartes a retornar.
   */
  public final ArrayList<Carta> getCartesInutils() {
    ArrayList<Carta> cartesRetornar = new ArrayList<Carta>();
    ordenarCartes();
    // System.out.println(cartes.get(0).getNumero() + " "
    // +cartes.get(1).getNumero());
    for (int i = cartes.size() - 1; i >= 1; i--) {
      cartesRetornar.add(cartes.get(i));
      cartes.remove(i);
    }

    return cartesRetornar;
  }

  /**
   * Mètode per ordenar les cartes d'un jugador de numero més grana més petit.
   */
  public final void ordenarCartes() {
    Collections.sort(cartes, new Comparator<Carta>() {

      public int compare(final Carta o1, final Carta o2) {
        // TODO Auto-generated method stub
        return new Integer(o2.getNumero()).compareTo(
            new Integer(o1.getNumero()));
      }
    });
  }

  /**
   * Mètode per obtenir el nom d'un jugador.
   * 
   * @return --> Retorna el nom del jugador.
   */
  public final String getNom() {
    return nom;
  }

  /**
   * Mètode per assignar un nom a un jugador.
   * 
   * @param nomN
   *          --> Nom del jugador.
   */
  public final void setNom(final String nomN) {
    this.nom = nomN;
  }

  /**
   * Mètode per obtenir les cartes d'un jugador.
   * 
   * @return --> Retorna un ArrayList amb les cartes del jugador.
   */
  public final ArrayList<Carta> getCartes() {
    return cartes;
  }

  /**
   * Mètode per assignar unes cartes a un jugador.
   * 
   * @param cartesN
   *          --> ArrayList amb les cartes a assignar.
   */
  public final void setCartes(final ArrayList<Carta> cartesN) {
    this.cartes = cartesN;
  }

  /**
   * Mètode per obtenir les monedes d'un jugador.
   * 
   * @return --> Retorna un valor enter corresponent a l numero de monedes d'un
   *         jugador.
   */
  public final int getMonedes() {
    return monedes;
  }

  /**
   * Mètode per assignar un numero determinat de monedes a un jugador.
   * 
   * @param monedesN
   *          --> Numero de monedes a assignar.
   */
  public final void setMonedes(final int monedesN) {
    this.monedes = monedesN;
  }

  /**
   * Mètode per obtenir l'aposta realitzada per un jugador en una ronda.
   * 
   * @return --> Retorna un valor enter corresponent a l'aposta del jugador.
   */
  public final int getAposta() {
    return aposta;
  }

  /**
   * Mètode per assignar una aposta a un jugador.
   * 
   * @param apostaN
   *          --> Aposta realitzada.
   */
  public final void setAposta(final int apostaN) {
    this.aposta = apostaN;
  }

  /**
   * Mètode per passar les cartes a String.
   * 
   * @return --> Retorna un String amb el pal i valor de totes les cartes d'un
   *         jugador.
   */
  public final String cartesToString() {
    String resultat = "";
    for (int i = 0; i < cartes.size(); i++) {
      resultat += cartes.get(i).getNumero() + cartes.get(i).getPal() + " ";
    }
    return resultat;
  }

}
