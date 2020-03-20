package com.generation.entities;

import com.generation.base.Entity;

public class Libro extends Entity 
{

private String titolo, autore, genere;
private int numeropagine, annopubblicazione;

// constructor

public Libro (int id, String titolo, String autore, String genere,
int numeropagine, int annopubblicazione) 
{
super(id);
setTitolo(titolo);
setAutore(autore);
setGenere(genere);
setNumeropagine(numeropagine);
setAnnopubblicazione(annopubblicazione);
}

// getters

public String getTitolo () {
return titolo;
}
public String getAutore () {
return autore;
}
public String getGenere () {
return genere;
}
public int getNumeropagine () {
return numeropagine;
}
public int getAnnopubblicazione () {
return annopubblicazione;
}

// setters

public void setTitolo (String titolo) {
this.titolo = titolo;
}
public void setAutore (String autore) {
this.autore = autore;
}
public void setGenere (String genere) {
this.genere = genere;
}
public void setNumeropagine (int numeropagine) {
this.numeropagine = numeropagine;
}
public void setAnnopubblicazione (int annopubblicazione) {
this.annopubblicazione = annopubblicazione;
}
}