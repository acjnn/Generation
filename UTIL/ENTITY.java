package com.generation.base;

import java.math.BigInteger;

public abstract class Entity implements Mapper, Validator
{
	public static final int IDMIN = 1;
	
	//LE proprietà dell'oggetto ci sono solo nelle classi
	//astratte NON nelle interfacce, per cui la differenza
	//è che le classi astratte hanno LO STATO DELL'OGGETTO
	protected BigInteger id;
	public Entity() {}
	public Entity(int id) 
	{
		this.id = BigInteger.valueOf(id);
	}

	public BigInteger getId()
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = BigInteger.valueOf(id);
	}
	
	// public abstract String questoDovraEssereImplementatoDaiFigliConcreti();
}
