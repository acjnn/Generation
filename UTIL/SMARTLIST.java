package com.generation.base;

import java.math.BigInteger;
import java.util.ArrayList;

//SmartList				// X è una classe generica, che dovrà essere specificata quando 
//tipo parametrizzato	//creeremo un oggetto di tipo SmartList
public class SmartList<X extends Entity> extends ArrayList<X>
{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SmartList() {}
	
	// Spesso ci siamo ritrovati a dover spostare gli oggetti di un vettore
	//in una lista, quindi facciamo un costruttore che ha il compito di 
	//aggiungere tutti gli elementi di un vettore nella lista
	public SmartList(X[] vett)
	{
		for(X elemento : vett)
			this.add(elemento);
	}

	@Override
	public boolean add(X elemento)
	{
		if(elemento instanceof Entity && ((Entity)elemento).getId() != BigInteger.valueOf(0))
			return super.add(elemento);
		else
			return false;
	}
	
	
	
	
}
