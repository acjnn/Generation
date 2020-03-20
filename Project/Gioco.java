package com.generation.entities;

import com.generation.base.Entity;

public class Gioco extends Entity
{
	private String titolo, genere, distributore;
	private int numeromin, numeromax;
	
	// constructor
	
	public Gioco(int id, String titolo, String genere, String distributore, int numeromin, int numeromax) {
		super(id);
		setTitolo(titolo);
		setGenere(genere);
		setDistributore(distributore);
		setNumeromin(numeromin);
		setNumeromax(numeromax);
	}

	// getters
	
	public String getTitolo() {
		return titolo;
	}

	public String getGenere() {
		return genere;
	}

	public String getDistributore() {
		return distributore;
	}

	public int getNumeromin() {
		return numeromin;
	}

	public int getNumeromax() {
		return numeromax;
	}

	// setters
	
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}
	
	public void setDistributore(String distributore) {
		this.distributore = distributore;
	}
	
	public void setNumeromin(int numeromin) {
		this.numeromin = numeromin;
	}
	
	public void setNumeromax(int numeromax) {
		this.numeromax = numeromax;
	}
	
}
