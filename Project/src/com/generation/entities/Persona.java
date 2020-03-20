package com.generation.entities;

import com.generation.base.Entity;

public class Persona extends Entity 
{

  private String nome;
  private String cognome;
  private String sesso;
  private int eta;
  private String pw;

// constructor

  public Persona (int id, String nome, String cognome, String sesso,
                  int eta, String pw) 
  {
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
public void setPw (String pw) {
  this.pw = pw;
}

}//chisura class
