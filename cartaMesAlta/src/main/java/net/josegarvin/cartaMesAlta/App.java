package net.josegarvin.cartaMesAlta;

/**
 * Classe principal del programa "CartaMèsAlta".
 * 
 * @author Jose Garvin Victoria.
 *
 */
public final class App {

  /**
   * Constructor per defecte de la classe.
   */
  private App() {

  }

  /**
   * Mètode principal del programa "CartaMèsAlta".
   * 
   * @param args
   *          --> .
   */
  public static void main(final String[] args) {
    Baralla barallaEspanyola = new Baralla();

    // barallaEspanyola.barallaToString();

    Joc jocCartes = new Joc(barallaEspanyola);

    jocCartes.comencaJoc();

  }

}
