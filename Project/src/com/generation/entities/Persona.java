package com.generation.entities;

public class Utente extends Entity {

  private String nome;
  private String cognome;
  private Sting sesso;
  private Int eta;
  private String pw;

// constructor

  public Persona (int id, Strig nome, String cognome, String sesso
                  int eta, String pw) {
    super(id);
    setNome(nome);
    setCognome(cognome);
    setSesso(sesso);
    setEta(eta);
    setPw(pw);
    }

// getters

public String getNome () {
  return nome;
}
public String getCognome () {
  return cognome;
}
public String getSesso () {
  return sesso;
}
public int getEta () {
  return eta;
}
public String getPw () {
  return pw;
}

// setters

public void setNome (String nome) {
  this.nome = nome;
}
public void setCognome (String cognome) {
  this.cognome = cognome;
}
public void setSesso (String sesso) {
  this.sesso = sesso;
}
public void setEta (int eta) {
  this.eta = eta;
}
public void setPw (int pw) {
  this.pw = pw;
}

}
