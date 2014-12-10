package net.josegarvin.cartaMesAlta;

import java.io.Serializable;

public class Carta implements Serializable {


  private static final long serialVersionUID = -191781805571611534L;

  int numero;
  String pal;

  Carta(int numC, String palC) {
    this.numero = numC;
    this.pal = palC;
  }

  public int getNumero() {
    return numero;
  }

  public void setNumero(int numero) {
    this.numero = numero;
  }

  public String getPal() {
    return pal;
  }

  public void setPal(String pal) {
    this.pal = pal;
  }

  public String cartaToString() {
    return this.numero + this.pal;
  }

}
