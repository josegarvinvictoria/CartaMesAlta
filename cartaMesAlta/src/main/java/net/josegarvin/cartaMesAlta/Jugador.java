package net.josegarvin.cartaMesAlta;

import java.util.ArrayList;
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
    this.monedes = generarMonedes();
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
  
  public void retornarCartes(){
    ArrayList<Carta> cartesRetornar;
   //Carta cartaMesAlta = this.cartes[0];
    //for(int i = 0; i<this.cartes.size() -1;i++){
      	
      
    //}
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

  
}
