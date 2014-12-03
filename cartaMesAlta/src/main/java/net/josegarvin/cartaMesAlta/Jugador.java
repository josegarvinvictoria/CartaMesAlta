package net.josegarvin.cartaMesAlta;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Jugador {

  String nom;
  ArrayList<Carta> cartes;
  int monedes;
  int aposta;

  public Jugador(String nomJ) {
    // TODO Auto-generated constructor stub
    this.nom = nomJ;
    this.cartes = new ArrayList<Carta>();
    this.monedes = 3;
    this.aposta = 0;
  }

  public int generarMonedes() {

    Random rnd = new Random();
    return rnd.nextInt(5) + 1;
  }

  public String jugadorToString() {
    return this.nom + " " + this.cartes.toString() + " " + this.monedes;
  }
  
  public boolean teMonedesPerApostar(int aposta){
    if(aposta<= this.monedes && aposta >= 0 ){
      return true;
    }
    return false;
  }
  
  public void rebreCarta(Carta carta){
    cartes.add(carta);
  }
  
  public ArrayList<Carta> getCartesInutils(){
    ArrayList<Carta> cartesRetornar = new ArrayList<Carta>();
    ordenarCartes();
    //System.out.println(cartes.get(0).getNumero() + " " +cartes.get(1).getNumero());
    for(int i = cartes.size()-1; i>=1;i--){
      cartesRetornar.add(cartes.get(i));
      cartes.remove(i);
    }
    
    return cartesRetornar;
  }
  
  
 
  public void ordenarCartes(){
    Collections.sort(cartes, new Comparator<Carta>() {

public int compare(Carta o1, Carta o2) {
        // TODO Auto-generated method stub
        return new Integer(o2.getNumero()).compareTo(new Integer(o1.getNumero()));
      }
});
  }

public String getNom() {
  return nom;
}

public void setNom(String nom) {
  this.nom = nom;
}

public ArrayList<Carta> getCartes() {
  return cartes;
}

public void setCartes(ArrayList<Carta> cartes) {
  this.cartes = cartes;
}

public int getMonedes() {
  return monedes;
}

public void setMonedes(int monedes) {
  this.monedes = monedes;
}

public int getAposta() {
  return aposta;
}

public void setAposta(int aposta) {
  this.aposta = aposta;
}

public String cartesToString() {
  String resultat = "";
  for(int i = 0; i<cartes.size();i++){
    resultat += cartes.get(i).getNumero() + cartes.get(i).getPal() + " --- ";
  }
  return resultat;
}
  
}
