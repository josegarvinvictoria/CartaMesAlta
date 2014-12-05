package net.josegarvin.cartaMesAlta;


import java.util.ArrayList;

public class Baralla{

  /**
 * 
 */
  
private ArrayList<Carta> cartes;
  String[] pals = { "-B", "-O", "-E", "-C" };
  int[] numeros = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

  Baralla(){
    this.cartes = GenerarBaralla();
  }
  
  
  /**
   * Mètode per generar una baralla espanyola de cartes.
   * 
   * @return
   */
  public ArrayList<Carta> GenerarBaralla() {
    ArrayList<Carta> baralla = new ArrayList<Carta>();

    for (int i = 0; i < pals.length; i++) {
      for (int j = 0; j < numeros.length; j++) {
        baralla.add(new Carta(numeros[j], pals[i]));
      }
    }
    return baralla;
  }
  
  public void barallaToString(){
    for(int i = 0; i<cartes.size();i++){
      System.out.println(cartes.get(i).getNumero() + cartes.get(i).getPal());
    }
  }


public ArrayList<Carta> getCartes() {
  return cartes;
}


public void setCartes(ArrayList<Carta> cartes) {
  this.cartes = cartes;
}
  
  
}
